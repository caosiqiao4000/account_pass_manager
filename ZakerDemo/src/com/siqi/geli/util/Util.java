package com.siqi.geli.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mobile.http.HttpReqCode;
import mobile.http.ResponseStateRecore;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Util {

	/**
	 * prompt user
	 * 
	 * @param context
	 * @param str
	 */
	public static void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	/**
	 * 取得当前时间 格式yyyyMMddHHMMss
	 * 
	 * @return
	 */
	public static String getNowTime() {
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		String formatTime = format.format(now);
		return formatTime;
	}

	public static boolean checkNetworkIsActive(Context context) {
		boolean mIsNetworkUp = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			mIsNetworkUp = info.isAvailable();
		}
		return mIsNetworkUp;
	}

	/**
	 * 检查返回结果
	 * 
	 * @param supportResponse
	 * @param context
	 * @return
	 */
	public static boolean HttpResponseStatus(Object supportResponse,
			Context context) {
		if (supportResponse == null) {
			showToast(context, "服务请求失败...");
			return false;
		} else if (supportResponse instanceof ResponseStateRecore) {
			ResponseStateRecore recore = (ResponseStateRecore) supportResponse;
			if (recore.getRequestResultStateCode() >= 400) { // 使用备份域名
				return false;
			}
			if (recore.getResponseStr().trim().length() > 0) {
				Log.i("supportResponse",
						"supportResponse= " + recore.getResponseStr());
			}
		} else {
			if (supportResponse instanceof HttpReqCode) {
				HttpReqCode httpReqCode = (HttpReqCode) supportResponse;
				if (httpReqCode.equals(HttpReqCode.no_network)) {
					showToast(context, "请检查您的网络！");
				} else if (httpReqCode.equals(HttpReqCode.error)) {
					showToast(context, "服务请求失败...");
				}
				return false;
			}
			if (AppSetting.DEBUG) {
				Log.w(AppSetting.LOG_TAG,
						"supportResponse= " + supportResponse.toString());
			}
		}
		return true;
	}

	public static void showToast(Context context, int aa) {
		Toast.makeText(context, aa, Toast.LENGTH_SHORT).show();
	}

	/** 判断邮箱是否存在 */
	public static boolean isEmpty(String x) {
		if (x == null) {
			return true;
		} else if (x.trim().equals("")) {
			return true;
		}
		return false;
	}

	  /**
     * 转换
     * 
     * @author caosq 2013-6-8 下午4:49:43
     * @return
     */
    public static String getNowPullTime() {
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
        String formatTime = format.format(now);
        return formatTime;
    }

}
