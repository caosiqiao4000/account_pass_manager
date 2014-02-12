package com.shai.manage.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.widget.Toast;

public class Util {

	/**
	 * prompt user
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
}
