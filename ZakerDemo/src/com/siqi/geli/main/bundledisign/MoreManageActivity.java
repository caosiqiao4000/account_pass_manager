package com.siqi.geli.main.bundledisign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siqi.geli.R;

/**
 * 更多
 * 
 * @author Administrator
 * 
 */
public class MoreManageActivity extends Activity implements OnClickListener {
	private TextView tv_title;
	private Button btn_left_title;
	private LinearLayout ly_company_info, ly_user_discuss, ly_app_updata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_more);
		init();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_left_title = (Button) findViewById(R.id.btn_left_title);
		ly_company_info = (LinearLayout) findViewById(R.id.ly_company_info);
		ly_user_discuss = (LinearLayout) findViewById(R.id.ly_user_discuss);
		ly_app_updata = (LinearLayout) findViewById(R.id.ly_app_updata);

		btn_left_title.setOnClickListener(this);
		tv_title.setText("更   多");
		ly_company_info.setOnClickListener(this);
		ly_user_discuss.setOnClickListener(this);
		ly_app_updata.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			MoreManageActivity.this.finish();
		} else if (v.getId() == ly_company_info.getId()) {
			
		} else if (v.getId() == ly_user_discuss.getId()) {

		} else if (v.getId() == ly_app_updata.getId()) {

		}
	}
}
