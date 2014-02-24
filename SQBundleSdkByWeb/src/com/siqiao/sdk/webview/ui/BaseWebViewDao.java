package com.siqiao.sdk.webview.ui;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.siqiao.sdk.common.other.util.AnimationManager;
import com.siqiao.sdk.dialog.R;
import com.siqiao.sdk.webview.util.SDKSettings;

/**
 * 实现使用自定义的webview
 * @author Administrator
 *
 */
public abstract class BaseWebViewDao implements
		OnTouchListener {
	protected Activity activity;
	
	// passs
	protected static final String SH_SAVE_NAME_VALUE = "shsavenameandvalue";

	// 检查域名成功后取服务器时间的回调码
	protected static final int checkHostDomainFinishCode = 0xaaa3;
	// 进入WEB界面的头一个参数
	protected String startLoadUrl;

	protected ViewFlipper mViewFlipper;
	protected CustomWebView mCurrentWebView;
	protected List<CustomWebView> mWebViews;
	protected LayoutInflater mInflater;
	protected Window mWindow;
	protected WindowManager.LayoutParams mLayoutParams;
	// 配置
	// private SettingBean settingBean;
	// private SdkSettingInterface settingManager;

	public static final String S_H_WINDOW_WIDTH_FLAG = "s_h_window_width_flag";
	public static final String S_H_WINDOW_HEIGHT_FLAG = "s_h_window_height_flag";
	// action类型
	public static final String S_H_ACTION_FLAG = "s_h_action_flag";
	// 要加载的URL传递参数标识
	public static final String S_H_LOADURL_PARAMS_FLAG = "s_h_loadurl_params_flag";

	protected View mCustomView;
	private FrameLayout mFullscreenContainer;
	protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);
	private WebChromeClient.CustomViewCallback mCustomViewCallback;
	private GestureDetector mGestureDetector;
	// true键盘正在显示
	private boolean showInputMethodView = true;

	protected MyWebHandler handler;

	protected String urlWeb = "";// url ? 之后的部分

	static class MyWebHandler extends Handler {
		private final WeakReference<BaseWebViewDao> weakActivity;

		public MyWebHandler(BaseWebViewDao activity) {
			this.weakActivity = new WeakReference<BaseWebViewDao>(activity);
		};

		@Override
		public void handleMessage(Message msg) {
			BaseWebViewDao activity = weakActivity.get();
			if (null != activity) {
				activity.handleMessage(msg);
			}
		}
	}
	
	public BaseWebViewDao(Activity activity){
		this.activity = activity;
		mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGestureDetector = new GestureDetector(activity, new GestureListener());
		handler = new MyWebHandler(this);
	}

	/**
	 * 依据传入的meg 开始相应的功能
	 * @param msg
	 */
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 0: // webview网页缩放
			// mCurrentWebView.zoomIn();
			break;
		case 1: // 设置webview窗口大小
			windowSetSize();
		case 2:

			break;
		case 3:  // 加载指定的URL
			String url = (String) msg.obj;
			mCurrentWebView.loadUrl(url);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置web的大小
	 */
	protected abstract void windowSetSize();

	protected abstract void generageCompletionUrlParams(String string);

	/**
	 * mCurrentWebView.setWebChromeClient(new WebViewDialog(this, mProgressBar,
	 * mProgressBarBackView)); if (BaseWebViewActivity.this instanceof
	 * WebViewNewsReader) { mCurrentWebView.setWebViewClient(new
	 * CustomWebViewClientImpl(this, this, handler)); } else if
	 * (BaseWebViewActivity.this instanceof PayGameActivity) {
	 * mCurrentWebView.setWebViewClient(new CustomWebViewClientImplByPay(this,
	 * this, handler)); } mCurrentWebView.setOnTouchListener(this); if
	 * (SDKSettings.DEBUG) { Log.w(SDKSettings.LOG_TAG,
	 * " initializeCurrentWebView is success call end  "); } // 菜单实现
	 * http://blog.csdn.net/think_soft/article/details/7350177 //
	 * mCurrentWebView.startActionMode();
	 * 
	 * @param mProgressBar
	 * @param mProgressBarBackView
	 *            \
	 * 
	 *            初始化web所有的功能
	 * 
	 */
	protected abstract void initializeCurrentWebView(ProgressBar mProgressBar,
			View mProgressBarBackView);

	/**
	 * 内置网页弹框 主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
	 * 
	 * @author boge
	 * @time 2013-3-26
	 * 
	 */
	public class WebViewDialog extends WebChromeClient {

		private Animation animal;
		private ProgressBar mProgressBar;
		private View mProgressBarBackView;

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress < 100) {
				if (mProgressBarBackView.getVisibility() == View.GONE) {
					mProgressBarBackView.setVisibility(View.VISIBLE);
				}
				if (newProgress <= 50) {
					((CustomWebView) view)
							.setProgress((int) (newProgress * 1.5));
				} else {
					((CustomWebView) view)
							.setProgress(75 + (int) (newProgress * 0.25));
				}
				mProgressBar.setProgress(mCurrentWebView.getProgress());
			} else {
				mProgressBar.setProgress(mCurrentWebView.getProgress());
				// 运行动画
				mProgressBarBackView.startAnimation(animal);
			}
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onProgressChanged newProgress = "
						+ newProgress);
			}
			// super.onProgressChanged(view, newProgress);
		}

		public WebViewDialog(Activity context, ProgressBar pb,
				final View mProgressBarBackView) {
			this.mProgressBarBackView = mProgressBarBackView;
			this.mProgressBar = pb;
			animal = AnimationUtils.loadAnimation(context,
					R.anim.sh_anim_progress);
			animal.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// 将 spinner 的可见性设置为不可见状态
					mProgressBarBackView.setVisibility(View.GONE);
				}
			});
		}

		@Override
		public void onCloseWindow(WebView window) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onCloseWindow 调用");
			}
			super.onCloseWindow(window);
		}

		@Override
		public boolean onCreateWindow(WebView view, boolean dialog,
				boolean userGesture, Message resultMsg) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onCreateWindow 调用");
			}
			WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
			addTab(false, mViewFlipper.getDisplayedChild());
			transport.setWebView(mCurrentWebView);
			resultMsg.sendToTarget();
			return true;
			// return super.onCreateWindow(view, dialog, userGesture,
			// resultMsg);
		}

		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG,
						"onConsoleMessage " + consoleMessage.message() + " "
								+ consoleMessage.sourceId());
			}
			return super.onConsoleMessage(consoleMessage);
		}

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			if (SDKSettings.DEBUG) {
				Log.w(SDKSettings.LOG_TAG, "onShowCustomView 调用");
			}
			showCustomView(view, callback);
		}

		@Override
		public void onHideCustomView() {
			hideCustomView();
		}

		/**
		 * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
		 */
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onJsAlert  is callback");
			}
			new AlertDialog.Builder(view.getContext())
					.setTitle("消息")
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							}).setCancelable(false).create().show();

			return true;
		}

		@Override
		public boolean onJsBeforeUnload(WebView view, String url,
				String message, JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onJsBeforeUnload  is callback");
			}
			return super.onJsBeforeUnload(view, url, message, result);
		}

		/**
		 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
		 */
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				final JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onJsConfirm  is callback");
			}
			new AlertDialog.Builder(view.getContext())
					.setTitle("消息")
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.confirm();
								}
							})
					.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									result.cancel();
								}
							}).create().show();

			return true;
		}

		/**
		 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
		 * window.prompt('请输入您的域名地址', '618119.com');
		 */
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, final JsPromptResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onJsPrompt  is callback");
			}
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					view.getContext());
			builder.setTitle("消息").setMessage(message);
			final EditText et = new EditText(view.getContext());
			et.setSingleLine();
			et.setText(defaultValue);
			builder.setView(et);
			builder.setPositiveButton(android.R.string.ok,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.confirm(et.getText().toString());
						}
					}).setNeutralButton(android.R.string.cancel,
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();
						}
					});
			// 禁止响应按back键的事件
			AlertDialog dialog = builder.create();
			dialog.show();
			return true;
		}

		@Override
		public void onExceededDatabaseQuota(String url,
				String databaseIdentifier, long quota,
				long estimatedDatabaseSize, long totalQuota,
				QuotaUpdater quotaUpdater) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onExceededDatabaseQuota 调用");
			}
			quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
		}

		@Override
		public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
				QuotaUpdater quotaUpdater) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onReachedMaxAppCacheSize 调用");
			}
			// 扩充缓存的容量
			quotaUpdater.updateQuota(requiredStorage * 2);
		}

		@Override
		public void onReceivedIcon(WebView view, Bitmap icon) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onReceivedIcon 调用");
			}
			super.onReceivedIcon(view, icon);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onReceivedTitle 调用");
			}
			super.onReceivedTitle(view, title);
		}

		@Override
		public void onRequestFocus(WebView view) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onRequestFocus 调用");
			}
			super.onRequestFocus(view);
		}
	}

	protected void initData() {
		mViewFlipper.removeAllViews();

	}

	protected void initView() {
		mWebViews = new ArrayList<CustomWebView>();
	}

	/**
	 * 
	 * 2014年1月2日下午2:13:30
	 * 
	 * @param url
	 *            域名之后的部分
	 */
	public void navigateToUrl() {
		if ((urlWeb != null) && (urlWeb.length() > 0)) {
			Message msg = handler.obtainMessage();
			msg.what = 3;
			msg.obj = urlWeb;
			handler.sendMessage(msg);
		}
	}

	/**
	 * Add a new tab.
	 * 
	 * @param navigateToHome
	 *            If True, will load the user home page.
	 */
	public void addTab(boolean navigateToHome) {
		addTab(navigateToHome, -1);
	}

	/**
	 * Add a new tab.
	 * 
	 * @param navigateToHome
	 *            If True, will load the user home page.
	 * @param parentIndex
	 *            The index of the new tab.
	 */
	protected void addTab(final boolean navigateToHome, final int parentIndex) {
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "addTab is callback");
		}
		// final LinearLayout view = (LinearLayout) mInflater.inflate(
		// RResouceByWebUtil.getIdFromR_LayoutClass(R.layout.sh_webview),
		// mViewFlipper, false);
		final View view = (View) mInflater.inflate(R.layout.sh_webview,
				mViewFlipper, false);
		mCurrentWebView = (CustomWebView) view.findViewById(R.id.sh_webview);

		final ProgressBar mProgressBar = (ProgressBar) view
				.findViewById(R.id.sh_pb_top);

		final View mProgressBarBackView = (View) view
				.findViewById(R.id.sh_pb_fl);

		mProgressBar.setMax(100);
		initializeCurrentWebView(mProgressBar, mProgressBarBackView);
		synchronized (mViewFlipper) {
			if (parentIndex != -1) {
				mWebViews.add(parentIndex + 1, mCurrentWebView);
				mViewFlipper.addView(view, parentIndex + 1);
			} else {
				mWebViews.add(mCurrentWebView);
				mViewFlipper.addView(view);
			}
			if (mViewFlipper.getChildCount() > 1) {
				mViewFlipper.setInAnimation(AnimationManager.getInstance()
						.getInFromRightAnimation());
				mViewFlipper.setOutAnimation(AnimationManager.getInstance()
						.getOutToLeftAnimation());
			}
			mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(view));
		}
	}

	/**
	 * 加载错误页面
	 */
	public void errorLoad() {// file:///android_asset/
	// String name = null;//
	// String htmlContent = null;
	// if (this instanceof WebViewNewsReader) {
	// htmlContent = Util.getErrorPage(BaseWebViewActivity.this,
	// "shsdkconf/shsdkerror.html", aaa, name);
	// mCurrentWebView.loadDataWithBaseURL(bbb, htmlContent, "text/html",
	// "UTF-8", null);
	// } else {
	// final String bbb = RResouceByWebUtil.assetResources
	// .getAssetUrl("shsdkerror_nobtn.html");
	// htmlContent = Util.getErrorPage(BaseWebViewActivity.this,
	// "shsdkconf/shsdkerror_nobtn.html", "", "");
	// mCurrentWebView.loadDataWithBaseURL(bbb, htmlContent, "text/html",
	// "UTF-8", null);
	// }

		// if (SDKSettings.DEBUG) {
		// Log.w(SDKSettings.LOG_TAG, htmlContent);
		// }
		mCurrentWebView.clearHistory();
	}

	protected void closeWebView() {
		WebIconDatabase.getInstance().close();
		if (null != mWebViews && mWebViews.size() > 0) {
			mWebViews.clear();
		}
	}
	

	protected void showCustomView(View view,
			WebChromeClient.CustomViewCallback callback) {
		// if a view already exists then immediately terminate the new one
		if (mCustomView != null) {
			callback.onCustomViewHidden();
			return;
		}
		// WebViewNewsReader.this.getWindow().getDecorView();
		FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
		mFullscreenContainer = new FullscreenHolder(activity);
		mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
		decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
		mCustomView = view;
		mCustomViewCallback = callback;
	}

	protected void hideCustomView() {
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "hideCustomView() 调用");
		}
		if (mCustomView == null)
			return;

		FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
		decor.removeView(mFullscreenContainer);
		mFullscreenContainer = null;
		mCustomView = null;
		mCustomViewCallback.onCustomViewHidden();
	}

	static class FullscreenHolder extends FrameLayout {

		public FullscreenHolder(Context ctx) {
			super(ctx);
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " FullscreenHolder  is callback");
			}
			setBackgroundColor(ctx.getResources().getColor(
					android.R.color.black));
		}

		@Override
		public boolean onTouchEvent(MotionEvent evt) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG,
						"FullscreenHolder onTouchEvent  is callback");
			}
			return true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	/**
	 * Gesture listener implementation.
	 */
	protected class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onDoubleTap  is callback");
			}
			handler.sendEmptyMessage(0);
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onFling  is callback");
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	public void onPageFinished(String url) {
		WebIconDatabase.getInstance().retainIconForPageUrl(
				mCurrentWebView.getUrl());
		if (SDKSettings.DEBUG) {
			Log.i(SDKSettings.LOG_TAG,
					"WebIconDatabase onPageFinished  is callback");
		}
	}

	public void onPageStarted(String url) {
	}

	public void onUrlLoading(String url) {
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		mCurrentWebView.saveState(outState);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "onRestoreInstanceState is not null");
		}
		mCurrentWebView.restoreState(savedInstanceState);
	}

}
