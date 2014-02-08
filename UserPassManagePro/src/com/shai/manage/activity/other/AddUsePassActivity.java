package com.shai.manage.activity.other;

import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.BaseActivity;
import com.shai.manage.adapter.ActionBarItemProvider;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.util.Util;

/**
 * 添加一条用户信息
 * 
 * @author Administrator
 * 
 */
public class AddUsePassActivity extends BaseActivity {
	// 分组
	private Spinner spinner_group;
	private EditText et_name, et_pass;
	// 安全等级
	private RadioGroup rg_level;
	private RadioButton rbt_two, rbt_one, rbt_three;
	private Button btn_submit,btn_cancel;
	
	private List<UserPassGroupBean> groups;
	private ActionBarItemProvider adapterBarProvide;
	private UserLocalGroupDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_useinfo);

		initView();
	}

	private void initView() {
		spinner_group = (Spinner) findViewById(R.id.spinner_group);
		et_name = (EditText) findViewById(R.id.et_name);
		et_pass = (EditText) findViewById(R.id.et_pass);
		rbt_one = (RadioButton) findViewById(R.id.rbt_one);
		rbt_two = (RadioButton) findViewById(R.id.rbt_two);
		rbt_three = (RadioButton) findViewById(R.id.rbt_three);
		btn_submit = (Button) findViewById(R.id.submit);
		btn_cancel = (Button) findViewById(R.id.cancel);
		
		btn_cancel.setOnClickListener(btn_click);
		btn_submit.setOnClickListener(btn_click);
		
		//======================================================
		dao = new UserLocalGroupDaoIml(this);
		groups = dao.getAllGroupBeans();
		adapterBarProvide = new ActionBarItemProvider(this, groups);
		spinner_group.setAdapter(adapterBarProvide);
	}
	
	OnClickListener btn_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId()==btn_cancel.getId()) {
				AddUsePassActivity.this.finish();
			}else if (v.getId() == btn_submit.getId()) {
				final String userName = et_name.getText().toString();
				final String userpass = et_pass.getText().toString();
				if (TextUtils.isEmpty(userName.trim())||TextUtils.isEmpty(userpass.trim())) {
					Util.showToast(AddUsePassActivity.this,"用户名或者密码不能为空...");
					return;
				}
				// 群组
				
			}
		}
	};
	
}
