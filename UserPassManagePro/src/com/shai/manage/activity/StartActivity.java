package com.shai.manage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.userpassmanagepro.R;

public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_start);
		Handler handle = new Handler();
		handle.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent it = new Intent();
				it.setClass(StartActivity.this,MainActivity.class);
				StartActivity.this.startActivity(it);
			}
		}, 3000);
		
	}
}
