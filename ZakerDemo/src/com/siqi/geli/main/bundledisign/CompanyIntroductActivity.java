package com.siqi.geli.main.bundledisign;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.siqi.geli.R;

/**
 * 企业介绍
 * 
 * @author Administrator
 * 
 */
public class CompanyIntroductActivity extends Activity implements
		OnClickListener {
	private TextView tv_title, tv_describe;
	private Button btn_left_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_companyintroduct);
		init();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_describe = (TextView) findViewById(R.id.tv_describe);
		btn_left_title = (Button) findViewById(R.id.btn_left_title);

		btn_left_title.setOnClickListener(this);
		tv_title.setText("企业介绍");
		tv_describe.setText(getResources().getString(R.string.company_info));
		tv_describe.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			CompanyIntroductActivity.this.finish();
		} else if (v.getId() == tv_title.getId()) {
			
		}

	}
}
