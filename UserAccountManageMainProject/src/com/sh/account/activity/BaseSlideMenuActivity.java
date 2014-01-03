package com.sh.account.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.aretha.slidemenu.SlideMenu;
import com.example.useraccountmanangemainproject.R;
import com.sh.account.tool.AppSetting;

/**
 * BaseSlideMenuActivity
 * @author Administrator
 * 2014年1月3日下午3:31:08
 */
public class BaseSlideMenuActivity extends FragmentActivity {
	private SlideMenu mSlideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_slidemenu);
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "BaseSlideMenuActivity onCreate mSlideMenu is call");
		}
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "BaseSlideMenuActivity onContentChanged mSlideMenu is call");
		}
	}

	public void setSlideRole(int res) {
		if (null == mSlideMenu) {
			return;
		}
		
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "BaseSlideMenuActivity setSlideRole is call");
		}

		getLayoutInflater().inflate(res, mSlideMenu, true);
	}

	public SlideMenu getSlideMenu() {
		return mSlideMenu;
	}
}
