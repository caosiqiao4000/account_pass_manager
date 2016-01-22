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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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

import com.shenhai.allapp.common.ConstantFieldUtil;
import com.shenhai.allapp.common.SDKSettings;
import com.shenhai.allapp.common.SharedPreferencesUtil;
import com.shenhai.util.CheckHostAsyTask;
import com.shenhai.util.ConfimNoticeDialog;
import com.shenhai.util.RResouceByWebUtil;
import com.shenhai.web.R;
import com.shenhai.web.activity.SHSDKEntry;
import com.shenhai.web.activity.Util;
import com.shenhai.web.common.AnimationManager;
import com.shenhai.web.interf.IUICallBackInterface;

public abstract class BaseWebViewActivity implements OnTouchListener, IUICallBackInterface {
	// passs
	protected static final String SH_SAVE_NAME_VALUE = "shsavenameandvalue";

	// 检查第一个域名的KEYCODE
	protected static final int checkHostDomainFirstCode = 0xaaa1;
	// 检查第二个域名的KEYCODE
	protected static final int checkHostDomainSecondCode = 0xaaa2;
	protected String currentCheckDomain = null;
	// 检查域名成功后取服务器时间的回调码
	protected static final int checkHostDomainFinishCode = 0xaaa3;
	// 使用哪个域名
	private String httpHeadStr;
	// 进入WEB界面的头一个参数
	protected String startLoadUrl;

	protected ViewFlipper mViewFlipper;
	protected CustomWebView mCurrentWebView;
	protected List<CustomWebView> mWebViews;
	protected LayoutInflater mInflater;
	protected SharedPreferencesUtil shServiceDataUtil;

	protected CheckHostAsyTask checkHostAsyTask;

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
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	private WebChromeClient.CustomViewCallback mCustomViewCallback;
	private GestureDetector mGestureDetector;
	// true键盘正在显示
	private boolean showInputMethodView = true;
	protected ConfimNoticeDialog mDialog;

	protected MyWebHandler handler;

	protected String urlWeb = "";// url ? 之后的部分

	static class MyWebHandler extends Handler {
		private final WeakReference<BaseWebViewActivity> weakActivity;

		public MyWebHandler(BaseWebViewActivity activity) {
			this.weakActivity = new WeakReference<BaseWebViewActivity>(activity);
		};

		@Override
		public void handleMessage(Message msg) {
			BaseWebViewActivity activity = weakActivity.get();
			if (null != activity) {
				activity.handleMessage(msg);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGestureDetector = new GestureDetector(this, new GestureListener());
		handler = new MyWebHandler(this);
		shServiceDataUtil = new SharedPreferencesUtil(BaseWebViewActivity.this,
				SharedPreferencesUtil.S_H_SERVICEADDRS_FLAG);
	}

	/**
	 * 显示用户action的错误 2014年1月2日下午3:17:26
	 * 
	 * @param actionStr
	 */
	protected void showActionError(String actionStr) {
		Log.e(SHSDKEntry.LOG_TAG, "SHSDK不存在相应的action " + actionStr + " 没有对应功能");
		Util.showToast(BaseWebViewActivity.this, "SHSDK不存在相应的action " + actionStr + " 没有对应功能");
		BaseWebViewActivity.this.finish();
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 0:
			// mCurrentWebView.zoomIn();
			break;
		case 1:
			windowSetSize();
		case 2:

			break;
		case 3:
			String url = (String) msg.obj;
			startLoadUrl = getHttpHeadStr() + url;
			if (SDKSettings.DEBUG) {
//			System.out.println("%%%%%%%%%%%%%%%%%% " + startLoadUrl);
			}
			mCurrentWebView.loadUrl(startLoadUrl);
			break;
		default:
			break;
		}
	}

	/**
	 * 检查主机域名解析是否可用 2014年1月2日下午3:08:32
	 * 
	 * @param casekey
	 *            回调区分码
	 * @param hostAdd
	 * @param pdShow
	 * @param pdMsg
	 * @param serverInterfaceName
	 */
	protected void checkHostValid(int casekey, String hostAdd, boolean pdShow, String pdMsg, String serverInterfaceName) {
		this.currentCheckDomain = hostAdd;
		checkHostAsyTask = new CheckHostAsyTask(this, this, casekey, hostAdd, serverInterfaceName, pdShow, pdMsg);
		checkHostAsyTask.execute();
	}

	protected abstract void windowSetSize();

	protected abstract void generageCompletionUrlParams(String string);

	protected void initializeCurrentWebView(ProgressBar mProgressBar, View mProgressBarBackView) {
		// mCurrentWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		// mCurrentWebView.getSettings().setBlockNetworkImage(true);
		mCurrentWebView.setWebChromeClient(new WebViewDialog(this, mProgressBar, mProgressBarBackView));
		if (BaseWebViewActivity.this instanceof WebViewNewsReader) {
			mCurrentWebView.setWebViewClient(new CustomWebViewClientImpl(this, this, handler));
		} else if (BaseWebViewActivity.this instanceof PayGameActivity) {
			mCurrentWebView.setWebViewClient(new CustomWebViewClientImplByPay(this, this, handler));
		}
		mCurrentWebView.setOnTouchListener(this);
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, " initializeCurrentWebView is success call end  ");
			;
		}
		// mCurrentWebView.setWebChromeClient(new WebViewDialog(this,
		// mProgressBar, mCurrentWebView, mViewFlipper));

		// 菜单实现 http://blog.csdn.net/think_soft/article/details/7350177
		// mCurrentWebView.startActionMode();
	}

	/**
	 * 内置网页弹框 主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
	 * 
	 * @author boge
	 * @time 2013-3-26
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
					((CustomWebView) view).setProgress((int) (newProgress * 1.5));
				} else {
					((CustomWebView) view).setProgress(75 + (int) (newProgress * 0.25));
				}
				mProgressBar.setProgress(mCurrentWebView.getProgress());
			} else {
				mProgressBar.setProgress(mCurrentWebView.getProgress());
				// 运行动画
				mProgressBarBackView.startAnimation(animal);
			}
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onProgressChanged newProgress = " + newProgress);
			}
			// super.onProgressChanged(view, newProgress);
		}

		public WebViewDialog(Activity context, ProgressBar pb, final View mProgressBarBackView) {
			this.mProgressBarBackView = mProgressBarBackView;
			this.mProgressBar = pb;
			animal = AnimationUtils.loadAnimation(context,
					RResouceByWebUtil.getIdFromR_AnimClass(R.anim.sh_anim_progress));
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
		public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
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
						"onConsoleMessage " + consoleMessage.message() + " " + consoleMessage.sourceId());
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
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onJsAlert  is callback");
			}
			new AlertDialog.Builder(view.getContext()).setTitle("消息").setMessage(message)
					.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					}).setCancelable(false).create().show();

			return true;
		}

		@Override
		public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onJsBeforeUnload  is callback");
			}
			return super.onJsBeforeUnload(view, url, message, result);
		}

		/**
		 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
		 */
		@Override
		public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onJsConfirm  is callback");
			}
			new AlertDialog.Builder(view.getContext()).setTitle("消息").setMessage(message)
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();
						}
					}).create().show();

			return true;
		}

		/**
		 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////” window.prompt('请输入您的域名地址', '618119.com');
		 */
		@Override
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
				final JsPromptResult result) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onJsPrompt  is callback");
			}
			final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
			builder.setTitle("消息").setMessage(message);
			final EditText et = new EditText(view.getContext());
			et.setSingleLine();
			et.setText(defaultValue);
			builder.setView(et);
			builder.setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					result.confirm(et.getText().toString());
				}
			}).setNeutralButton(android.R.string.cancel, new android.content.DialogInterface.OnClickListener() {
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
		public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
				long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "onExceededDatabaseQuota 调用");
			}
			quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
		}

		@Override
		public void onReachedMaxAppCacheSize(long requiredStorage, long quota, QuotaUpdater quotaUpdater) {
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
	protected void addTab(boolean navigateToHome) {
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
		final View view = (View) mInflater.inflate(RResouceByWebUtil.getIdFromR_LayoutClass(R.layout.sh_webview),
				mViewFlipper, false);
		mCurrentWebView = (CustomWebView) view.findViewById(RResouceByWebUtil.getIdFromR_IdClass(R.id.sh_webview));

		final ProgressBar mProgressBar = (ProgressBar) view.findViewById(RResouceByWebUtil
				.getIdFromR_IdClass(R.id.sh_pb_top));

		final View mProgressBarBackView = (View) view.findViewById(RResouceByWebUtil.getIdFromR_IdClass(R.id.sh_pb_fl));

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
				mViewFlipper.setInAnimation(AnimationManager.getInstance().getInFromRightAnimation());
				mViewFlipper.setOutAnimation(AnimationManager.getInstance().getOutToLeftAnimation());
			}
			mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(view));
		}
	}

	protected String getHttpHeadStr() {
		return httpHeadStr;
	}

	/**
	 * 加载错误页面
	 */
	public void errorLoad() {// file:///android_asset/
		// String bbb = "file:///android_asset/shsdkconf/shsdkerror.html";
		// if (Build.VERSION.SDK_INT < 11) {
		// mCurrentWebView.loadUrl(bbb + "?pid=" + aaa);
		// } else {
		// mCurrentWebView.loadUrl("file:///android_asset/shsdkconf/shsdkerror.html");
		String name = null;//
		String htmlContent = null;
		if (this instanceof WebViewNewsReader) {
			final String aaa = Util.getAppMetaData(BaseWebViewActivity.this, ConstantFieldUtil.S_H_SRCID);
			final String bbb = RResouceByWebUtil.assetResources.getAssetUrl("shsdkerror.html");
			name = shServiceDataUtil.getString(SH_SAVE_NAME_VALUE, "");
			htmlContent = Util.getErrorPage(BaseWebViewActivity.this, "shsdkconf/shsdkerror.html", aaa, name);
			mCurrentWebView.loadDataWithBaseURL(bbb, htmlContent, "text/html", "UTF-8", null);
		} else {
			// name = urlWeb;
			final String bbb = RResouceByWebUtil.assetResources.getAssetUrl("shsdkerror_nobtn.html");
			htmlContent = Util.getErrorPage(BaseWebViewActivity.this, "shsdkconf/shsdkerror_nobtn.html", "", "");
			mCurrentWebView.loadDataWithBaseURL(bbb, htmlContent, "text/html", "UTF-8", null);
		}

		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, htmlContent);
		}
		mCurrentWebView.clearHistory();
	}

	public RequestBean getRequestBeanByActionStr(String aString) {
		SHSDKEntry sdk = SHSDKEntry.initSDK(this);

		return sdk.getRequestBeanByActionStr(aString);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		WebIconDatabase.getInstance().close();
		if (null != checkHostAsyTask && checkHostAsyTask.isPdShow()) {
			checkHostAsyTask.setPdShow(false);
			checkHostAsyTask.getPopupDialog().dismiss();
			checkHostAsyTask.cancel(true);
		}
		if (null != mWebViews && mWebViews.size() > 0) {
			mWebViews.clear();
		}
		super.onDestroy();
	}

	public void setHttpHeadStr(String httpHeadStr) {
		// WebStorage.getInstance().setQuotaForOrigin(this.httpHeadStr, 20);
		this.httpHeadStr = httpHeadStr;
		if (SDKSettings.DEBUG) {// httpHeadStr html = http://user.pay.921.com
			Log.w(SDKSettings.LOG_TAG, "httpHeadStr save domain html = " + httpHeadStr);
		}
		if (this instanceof WebViewNewsReader) {
			shServiceDataUtil.saveString(WebViewNewsReader.SAVE_HTML_ORIGIN, httpHeadStr);
		} else {
			shServiceDataUtil.saveString(PayGameActivity.SAVE_HTML_ORIGIN, httpHeadStr);
		}
	}

	/**
	 * 检查域名完成,开始使用域名 2014年1月2日下午4:03:24
	 */
	protected void postServerStart(String saveDomainStr) {
		Util.getServerTime(this, this, checkHostDomainFinishCode,
				shServiceDataUtil.getString(saveDomainStr, ConstantFieldUtil.S_H_SH_FIRST_NEW_SERVER_DOMAIN));
	}

	protected void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
		// if a view already exists then immediately terminate the new one
		if (mCustomView != null) {
			callback.onCustomViewHidden();
			return;
		}
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "showCustomView() 调用");
		}
		// WebViewNewsReader.this.getWindow().getDecorView();
		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
		mFullscreenContainer = new FullscreenHolder(BaseWebViewActivity.this);
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

		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
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
			setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
		}

		@Override
		public boolean onTouchEvent(MotionEvent evt) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, "FullscreenHolder onTouchEvent  is callback");
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
	protected class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onDoubleTap  is callback");
			}
			handler.sendEmptyMessage(0);
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (SDKSettings.DEBUG) {
				Log.i(SDKSettings.LOG_TAG, " onFling  is callback");
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	public void onPageFinished(String url) {
		WebIconDatabase.getInstance().retainIconForPageUrl(mCurrentWebView.getUrl());
		if (SDKSettings.DEBUG) {
			Log.i(SDKSettings.LOG_TAG, "WebIconDatabase onPageFinished  is callback");
		}
	}

	public void onPageStarted(String url) {
	}

	public void onUrlLoading(String url) {
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		if (SDKSettings.DEBUG) {
			Log.i(SDKSettings.LOG_TAG, "onKeyLongPress is callback");
		}
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.moveTaskToBack(true);
			return true;
		default:
			return super.onKeyLongPress(keyCode, event);
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (SDKSettings.DEBUG) {
			Log.i(SDKSettings.LOG_TAG, "onKeyUp is callback");
		}
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (!mCurrentWebView.isFocused()) {
				if (showInputMethodView) {
					showInputMethodView = false;
					return true;
				}
				handleWebviewBack();
			} else {
				showInputMethodView = true;
				if (BaseWebViewActivity.this instanceof PayGameActivity) {
					mDialog = generageConfirmDialog("操作尚未完成,确定放弃吗?", new OnClickListener() {
						@Override
						public void onClick(View v) {
							handleWebviewBack();
							((PayGameActivity) BaseWebViewActivity.this).setIsBtnBack(true);
							mDialog.closeDialog();
						}
					});
				} else {
					handleWebviewBack();
				}
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		default:
			return super.onKeyUp(keyCode, event);
		}
	}

	protected ConfimNoticeDialog generageConfirmDialog(String contentStr, OnClickListener onClickListener) {
		return Util.showConfirmDialog(BaseWebViewActivity.this, "921支付", contentStr, onClickListener, null, null);
	}

	private void handleWebviewBack() {
		if (mCustomView != null) {
			hideCustomView();
		} else {
			if (mCurrentWebView.canGoBack()) {
				mCurrentWebView.goBack();
			} else {
				showPreviousTab(false);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (SDKSettings.DEBUG) {
			Log.i(SDKSettings.LOG_TAG, "onKeyDown is callback");
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Show the previous tab, if any.
	 */
	private void showPreviousTab(boolean resetToolbarsRunnable) {
		if (mViewFlipper.getChildCount() > 1) {
			final int a = mViewFlipper.getDisplayedChild();
			mCurrentWebView.doOnPause();
			mViewFlipper.setInAnimation(AnimationManager.getInstance().getInFromLeftAnimation());
			// mViewFlipper.setInAnimation(AnimationManager.getInstance()
			// .getInFromRightAnimation());
			mViewFlipper.setOutAnimation(AnimationManager.getInstance().getOutToRightAnimation());
			mViewFlipper.showPrevious();
			mCurrentWebView = mWebViews.get(mViewFlipper.getDisplayedChild());
			mCurrentWebView.doOnResume();
			mViewFlipper.removeViewAt(a);
		} else {
			this.finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mCurrentWebView.saveState(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "onRestoreInstanceState is not null");
		}
		mCurrentWebView.restoreState(savedInstanceState);
		super.onSaveInstanceState(savedInstanceState);
	}

}
