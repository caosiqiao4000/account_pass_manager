package com.shai.manage.util;

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

}
