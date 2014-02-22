package com.siqiao.sdk.webview.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.siqiao.sdk.webview.util.SDKSettings;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * A convenient extension of WebView.
 * 
 * 透明WEBVIEW 周边暗色 圆角
 * 
 */
public class CustomWebView extends WebView {
	private Context mContext;

	private int mProgress = 100;
	// the round corner for the highlight path
    private static final float TOUCH_HIGHLIGHT_ARC = 5.0f;

	private boolean mIsLoading = false;

	private String mLoadedUrl;// 当前加载的URL

	private static Method mOnPauseMethod = null;
	private static Method mOnResumeMethod = null;
	private static Method mSetFindIsUp = null;
	private static Method mNotifyFindDialogDismissed = null;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				WebSettings settings = CustomWebView.this.getSettings();
				// 开启javascript设置
				settings.setJavaScriptEnabled(true);
				settings.setJavaScriptCanOpenWindowsAutomatically(true);
				configuredOfflineMode(settings);
				// User settings
				// 设置可以自动加载图片
				settings.setLoadsImagesAutomatically(true);
				// 设置加载进来的页面自适应手机屏幕
				settings.setUseWideViewPort(false);
				settings.setLoadWithOverviewMode(true);
				// 保存表单数据
				settings.setSaveFormData(true);
				// 不保存密码
				settings.setSavePassword(false);

				// setHorizontalScrollBarEnabled(false);// 水平不显示
				// setVerticalScrollBarEnabled(false); // 垂直不显示
				// DisplayMetrics metrics = new DisplayMetrics();
				// ((Activity) mContext).getWindowManager().getDefaultDisplay()
				// .getMetrics(metrics);
				// int mDensity = metrics.densityDpi;
				// if (SDKSettings.DEBUG) {
				// Log.i(SDKSettings.LOG_TAG, "mDensity = " + mDensity);
				// }
				// if (mDensity == 240) {
				// settings.setDefaultZoom(ZoomDensity.FAR);
				// } else if (mDensity == 160) {
				// settings.setDefaultZoom(ZoomDensity.MEDIUM);
				// } else if (mDensity == 120) {
				// settings.setDefaultZoom(ZoomDensity.CLOSE);
				// } else if (mDensity == DisplayMetrics.DENSITY_LOW) {
				// settings.setDefaultZoom(ZoomDensity.CLOSE);
				// }

				// settings.setUserAgentString(Controller
				// .getInstance()
				// .getPreferences()
				// .getString(Constants.PREFERENCES_BROWSER_USER_AGENT,
				// Constants.USER_AGENT_DEFAULT));
				// 开启javascript设置
				// CookieSyncManager.createInstance(mContext);
				// CookieSyncManager.getInstance().startSync();
				// CookieManager.getInstance().setAcceptCookie(true);
				// final int a = Build.VERSION.SDK_INT;
				// if (a <= 7) {
				// settings.setPluginsEnabled(true);
				// } else {
				// settings.setPluginState(PluginState.ON_DEMAND);
				// }

				settings.setSupportZoom(false);
				settings.setBuiltInZoomControls(false);
				// if (Controller.getInstance().getPreferences()
				// .getBoolean(Constants.PREFERENCES_BROWSER_ENABLE_PROXY_SETTINGS,
				// false)) {
				// ProxySettings.setSystemProxy(mContext);
				// } else {
				// ProxySettings.resetSystemProxy(mContext);
				// }
				// Technical settings
				settings.setSupportMultipleWindows(true);
				// setLongClickable(true);
				setScrollbarFadingEnabled(true);
				// 滚动条在WebView外侧显示
				setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
				// setDrawingCacheEnabled(true);

				// }
				break;

			default:
				break;
			}
		};
	};

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The current context.
	 */
	public CustomWebView(Context context) {
		super(context);
		initializeOptions();
		mContext = context;
		// loadMethods();
	}

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The current context.
	 * @param attrs
	 *            The attribute set.
	 */
	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initializeOptions();
		// loadMethods();
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	/**
	 * Initialize the WebView with the options set by the user through
	 * preferences.
	 */
	private void initializeOptions() {
		// clearCache(true);
		// clearHistory();
		// this.setBackgroundResource(android.R.color.transparent); // 透明
		this.setBackgroundColor(0); // 透明
		// .getIdFromR_ColorClass(R.color.sh_webview_back)); // 透明

		handler.sendEmptyMessage(1);
	}

	// 配制离线使用的设置,,,用于最新活动
	private void configuredOfflineMode(WebSettings settings) {
		settings.setAppCacheMaxSize(8 * 1024 * 1024); // 缓存最多可以有8M
		// 设置可以使用localStorage
		settings.setDomStorageEnabled(true);
		// 应用可以有数据库
		settings.setDatabaseEnabled(true);
		String dbPath = ((Activity) mContext).getApplication()
				.getDir("database", Context.MODE_APPEND).getAbsolutePath();
		settings.setDatabasePath(dbPath);
		String appCaceDir = mContext.getApplicationContext()
				.getDir("cache", Context.MODE_APPEND).getAbsolutePath();
		settings.setAppCachePath(appCaceDir);
		settings.setAllowFileAccess(true); // 可以读取文件缓存(manifest生效)
		// 应用可以有缓存
		settings.setAppCacheEnabled(true);
		// 默认使用缓存
		// settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		// settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		if (SDKSettings.DEBUG) {
			Log.w(SDKSettings.LOG_TAG, "dbPath = " + dbPath);
			Log.w(SDKSettings.LOG_TAG, "appCaceDir = " + appCaceDir);
			Log.w(SDKSettings.LOG_TAG,
					"UserAgent int = " + settings.getUserAgent());
			Log.w(SDKSettings.LOG_TAG,
					"UserAgentstr = " + settings.getUserAgentString());
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		// final int action = ev.getAction();

		// Enable / disable zoom support in case of multiple pointer, e.g.
		// enable zoom when we have two down pointers,
		// disable with one pointer or when pointer up.
		// We do this to prevent the display of zoom controls, which are not
		// useful and override over the right bubble.
		// if ((action == MotionEvent.ACTION_DOWN)
		// || (action == MotionEvent.ACTION_POINTER_DOWN)
		// || (action == MotionEvent.ACTION_POINTER_1_DOWN)
		// || (action == MotionEvent.ACTION_POINTER_2_DOWN)
		// || (action == MotionEvent.ACTION_POINTER_3_DOWN)) {
		// if (ev.getPointerCount() > 1) {
		// this.getSettings().setBuiltInZoomControls(true);
		// this.getSettings().setSupportZoom(true);
		// } else {
		// this.getSettings().setBuiltInZoomControls(false);
		// this.getSettings().setSupportZoom(false);
		// }
		// } else if ((action == MotionEvent.ACTION_UP)
		// || (action == MotionEvent.ACTION_POINTER_UP)
		// || (action == MotionEvent.ACTION_POINTER_1_UP)
		// || (action == MotionEvent.ACTION_POINTER_2_UP)
		// || (action == MotionEvent.ACTION_POINTER_3_UP)) {
		// this.getSettings().setBuiltInZoomControls(false);
		// this.getSettings().setSupportZoom(false);
		// }
		return super.onTouchEvent(ev);
	}

	@Override
	public void loadUrl(String url) {
		mLoadedUrl = url;
		super.loadUrl(url);
	}

	/**
	 * Set the current loading progress of this view.
	 * 
	 * @param progress
	 *            The current loading progress.
	 */
	public void setProgress(int progress) {
		mProgress = progress;
	}

	/**
	 * Get the current loading progress of the view.
	 * 
	 * @return The current loading progress of the view.
	 */
	public int getProgress() {
		return mProgress;
	}

	/**
	 * Triggered when a new page loading is requested.
	 */
	public void notifyPageStarted() {
		mIsLoading = true;
	}

	/**
	 * Triggered when the page has finished loading.
	 */
	public void notifyPageFinished() {
		mProgress = 100;
		mIsLoading = false;
	}

	/**
	 * Check if the view is currently loading.
	 * 
	 * @return True if the view is currently loading.
	 */
	public boolean isLoading() {
		return mIsLoading;
	}

	/**
	 * Get the loaded url, e.g. the one asked by the user, without redirections.
	 * 
	 * @return The loaded url.
	 */
	public String getLoadedUrl() {
		return mLoadedUrl;
	}

	/**
	 * Reset the loaded url.
	 */
	public void resetLoadedUrl() {
		mLoadedUrl = null;
	}

	/**
	 * 相同的url
	 * 
	 * @param url
	 * @return
	 */
	public boolean isSameUrl(String url) {
		if (url != null) {
			return url.equalsIgnoreCase(this.getUrl());
		}

		return false;
	}

	/**
	 * Perform an 'onPause' on this WebView through reflexion.
	 */
	public void doOnPause() {
		if (mOnPauseMethod != null) {
			try {
				mOnPauseMethod.invoke(this);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doOnPause(): " + e.getMessage());
			}
		}
	}

	/**
	 * Perform an 'onResume' on this WebView through reflexion.
	 */
	public void doOnResume() {
		if (mOnResumeMethod != null) {
			try {

				mOnResumeMethod.invoke(this);

			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doOnResume(): " + e.getMessage());
			}
		}
	}

	@Override
	public WebBackForwardList saveState(Bundle outState) {
		return super.saveState(outState);
	}

	public void doSetFindIsUp(boolean value) {
		if (mSetFindIsUp != null) {
			try {
				mSetFindIsUp.invoke(this, value);
			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView", "doSetFindIsUp(): " + e.getMessage());
			}
		}
	}

	public void doNotifyFindDialogDismissed() {
		if (mNotifyFindDialogDismissed != null) {
			try {

				mNotifyFindDialogDismissed.invoke(this);

			} catch (IllegalArgumentException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			} catch (IllegalAccessException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			} catch (InvocationTargetException e) {
				Log.e("CustomWebView",
						"doNotifyFindDialogDismissed(): " + e.getMessage());
			}
		}
	}
}
