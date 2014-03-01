package com.shai.manage.impl;

import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.util.Log;

import com.shai.manage.activity.PassSetting;
import com.shai.manage.util.Util;

public class OnNavigationListenerImpl implements OnNavigationListener {
	private Context context;

	public OnNavigationListenerImpl(Context context) {
		this.context = context;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (PassSetting.Debug) {
			Log.i(PassSetting.Debug_flag,
					"onNavigationItemSelected itemPosition = " + itemPosition
							+ " itemId= " + itemId);
		}
		return false;
	}

}
