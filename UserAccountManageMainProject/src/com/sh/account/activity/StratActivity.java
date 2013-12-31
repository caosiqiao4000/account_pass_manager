package com.sh.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.useraccountmanangemainproject.R;
import com.sh.account.tool.AppSetting;

public class StratActivity extends Activity implements Runnable {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.a_start);
		HandlerThread thread = new HandlerThread("startThread");
		thread.start();
		Handler mHandler = new Handler(thread.getLooper());
		mHandler.postDelayed(this, 3000);
	}

	@Override
	public void run() {
		// 加载是否有新版本
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG,this.getCallingPackage()==null?"":this.getCallingPackage());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getPackageCodePath());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getApplication().getPackageResourcePath());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getApplicationContext().toString());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getBaseContext().toString());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getClassLoader().toString());
			Log.i(AppSetting.APPDEBUG_FLAG,this.getTaskId()+"");
		}
		Intent intent = new Intent();
		intent.setClass(getBaseContext(),MainActivity.class);
		startActivity(intent);
	}
}
