package com.siqi.geli.main.bundledisign;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.util.AppSetting;
import com.siqiao.sdk.common.util.PhoneHardwareUtil;

/**
 * 企业介绍
 * 
 * @author Administrator
 * 
 */
public class CompanyIntroductActivity extends Activity implements OnClickListener {
	private TextView tv_title, tv_describe, tv_click_info;
	private Button btn_left_title;

	public static int showCompanyIntroduct = 0x11;
	public static int showProductSave = 0x12;
	public static int showAftersaleserver = 0x12;
	private int showIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_companyintroduct);
		showIndex = getIntent().getIntExtra(AppSetting.KEY_ONE, showCompanyIntroduct);
		init();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_click_info = (TextView) findViewById(R.id.tv_click_info);
		tv_describe = (TextView) findViewById(R.id.tv_describe);
		btn_left_title = (Button) findViewById(R.id.btn_left_title);

		btn_left_title.setOnClickListener(this);
		tv_click_info.setOnClickListener(this);
		if (showIndex == showCompanyIntroduct) {
			tv_title.setText("企业介绍");
			tv_describe.setText(getResources().getString(R.string.company_info));
		} else if (showIndex == showProductSave) {
			tv_title.setText("产品保障");
			tv_describe.setText(getResources().getString(R.string.product_save_info));

		} else if (showIndex == showAftersaleserver) {
			tv_title.setText("售后服务");
			tv_describe.setText(getResources().getString(R.string.company_aftersalesserver_info));
		}
		tv_describe.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			CompanyIntroductActivity.this.finish();
		} else if (v.getId() == tv_title.getId()) {

		} else if (v.getId() == tv_click_info.getId()) {// 查看详情
			PhoneHardwareUtil.toOSWebViewIntent(this, "http://www.gree.com.cn");
		}
	}
}
