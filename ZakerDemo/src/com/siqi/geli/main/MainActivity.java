package com.siqi.geli.main;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.widget.TextView;

import com.siqi.geli.R;

public class MainActivity extends Activity {
	ArrayList<ImageInfo> data; // 菜单数据
	private static TextView mynum; // 页码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mynum = (TextView) findViewById(R.id.mynum);
		// 初始化数据
		initData();
		ViewPager vpager = (ViewPager) findViewById(R.id.vPager);
		vpager.setAdapter(new MyPagerAdapter(MainActivity.this, data));
		vpager.setPageMargin(50);
		vpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mynum.setText("" + (int) (arg0 + 1));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initData() {
		data = new ArrayList<ImageInfo>();
		mynum.setText("1");
		// data.add(new ImageInfo("模块设计", R.drawable.icon1, R.drawable.icon_bg01));
		data.add(new ImageInfo("产品总览", R.drawable.icon1, R.drawable.icon_bg01));
		data.add(new ImageInfo("最新资讯", R.drawable.icon2, R.drawable.icon_bg01));
		data.add(new ImageInfo("企业介绍", R.drawable.icon3, R.drawable.icon_bg02));
		data.add(new ImageInfo("加盟商家", R.drawable.icon4, R.drawable.icon_bg02));
		data.add(new ImageInfo("产品保障", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("售后服务", R.drawable.icon6, R.drawable.icon_bg02));
		data.add(new ImageInfo("格力旗下", R.drawable.icon7, R.drawable.icon_bg01));

		data.add(new ImageInfo("更   多", R.drawable.icon8, R.drawable.icon_bg01));
		// data.add(new ImageInfo("ͨ娱乐八卦", R.drawable.icon9, R.drawable.icon_bg02));
		// data.add(new ImageInfo("体育新闻", R.drawable.icon10, R.drawable.icon_bg02));
		// data.add(new ImageInfo("互联网新闻", R.drawable.icon11, R.drawable.icon_bg02));
		// data.add(new ImageInfo("奢侈品频道", R.drawable.icon12, R.drawable.icon_bg02));
		// data.add(new ImageInfo("时尚频道", R.drawable.icon13, R.drawable.icon_bg02));
		// data.add(new ImageInfo("财经频道", R.drawable.icon14, R.drawable.icon_bg02));
		// data.add(new ImageInfo("财经新闻", R.drawable.icon15, R.drawable.icon_bg02));
		// data.add(new ImageInfo("福布斯中文网", R.drawable.icon16,
		// R.drawable.icon_bg02));
		// data.add(new ImageInfo("旅游频道", R.drawable.icon3, R.drawable.icon_bg02));
		// data.add(new ImageInfo("游戏频道", R.drawable.icon8, R.drawable.icon_bg02));
		// data.add(new ImageInfo("开心笑话", R.drawable.icon10, R.drawable.icon_bg02));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
