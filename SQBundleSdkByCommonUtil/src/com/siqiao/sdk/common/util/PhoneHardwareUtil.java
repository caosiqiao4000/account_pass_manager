package com.siqiao.sdk.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 1.取手机号 设置ID 系统版本 型号 操作系统版本... 2.手机网络判断
 * 
 * <pre>
 * 3. 手机调用系统功能(照相机,浏览器,音乐,视频...)
 * @author Administrator
 * 
 */
public class PhoneHardwareUtil {

	/**
	 * 检查是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkIsActive(Context context) {
		boolean mIsNetworkUp = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			mIsNetworkUp = info.isAvailable();
		}
		return mIsNetworkUp;
	}

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 *            http://www.duotaozx.com http://xxx
	 */
	public static void toOSWebViewIntent(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param phoneNum
	 *            
	 */
	public static void toOSCallPhoneIntent(Context context, String phoneNum) {
		Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
		context.startActivity(it);
	}
}
