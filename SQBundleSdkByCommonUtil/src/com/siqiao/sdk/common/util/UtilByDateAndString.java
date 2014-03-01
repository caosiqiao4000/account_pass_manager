package com.siqiao.sdk.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * 日期 转换 String处理
 * 
 * @author Administrator
 * 
 */
public class UtilByDateAndString {

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
