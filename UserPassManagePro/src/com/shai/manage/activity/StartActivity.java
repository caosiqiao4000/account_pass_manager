package com.shai.manage.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.userpassmanagepro.R;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.util.SharedPreferencesUtil;

public class StartActivity extends Activity {

	private SharedPreferencesUtil sh_util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_start);
		sh_util = new SharedPreferencesUtil(this,
				SharedPreferencesUtil.S_H_SERVICEADDRS_FLAG);
		if (sh_util.getBoolean("isFirstcomeapp", true)) {
			// 如果是第一次进入,初始化一些数据(预置的群组)
			initAppData();
			sh_util.saveBoolean("isFirstcomeapp", false);
		}

		Handler handle = new Handler();
		handle.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent it = new Intent();
				it.setClass(StartActivity.this, MainActivity.class);
				StartActivity.this.startActivity(it);
			}
		}, 1000);

	}

	private void initAppData() {
		UserLocalGroupDao dao = new UserLocalGroupDaoIml(this);
		List<UserPassGroupBean> groups = new ArrayList<UserPassGroupBean>();
		UserPassGroupBean info1 = new UserPassGroupBean(
				String.valueOf(R.drawable.app_search_icon_p), "QQ");
		groups.add(info1);
		groups.add(new UserPassGroupBean(String
				.valueOf(R.drawable.app_search_icon_p), "yy"));
		groups.add(new UserPassGroupBean(String
				.valueOf(R.drawable.app_search_icon_p), "sina"));
		groups.add(new UserPassGroupBean(String
				.valueOf(R.drawable.app_search_icon_p), "360"));
		groups.add(new UserPassGroupBean(String
				.valueOf(R.drawable.app_search_icon_p), "xaomi"));
		dao.saveAllUserPassGroupBean(groups);
	}
}
