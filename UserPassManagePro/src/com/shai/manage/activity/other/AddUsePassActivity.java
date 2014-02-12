package com.shai.manage.activity.other;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.shai.manage.activity.PassSetting;
import com.shai.manage.adapter.ActionBarItemProvider;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.respondbean.UserPassItemBean;
import com.shai.manage.ui.CustomDialogFragment;
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
	private EditText et_name, et_pass, et_decri, et_relevance_account;
	// 安全等级
	private RadioGroup rg_level;
	private RadioButton rbt_two, rbt_one, rbt_three;
	private Button btn_submit, btn_cancel;

	private List<UserPassGroupBean> groups;
	private ActionBarItemProvider adapterBarProvide;
	private UserLocalGroupDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.title_addusepass));
		setContentView(R.layout.a_add_useinfo);
		initView();
	}

	private void initView() {
		spinner_group = (Spinner) findViewById(R.id.spinner_group);
		et_name = (EditText) findViewById(R.id.et_name);
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_decri = (EditText) findViewById(R.id.et_decri);
		et_relevance_account = (EditText) findViewById(R.id.et_relevance_account);
		rbt_one = (RadioButton) findViewById(R.id.rbt_one);
		rbt_two = (RadioButton) findViewById(R.id.rbt_two);
		rbt_three = (RadioButton) findViewById(R.id.rbt_three);
		btn_submit = (Button) findViewById(R.id.submit);
		btn_cancel = (Button) findViewById(R.id.cancel);

		btn_cancel.setOnClickListener(btn_click);
		btn_submit.setOnClickListener(btn_click);

		// ======================================================
		dao = new UserLocalGroupDaoIml(this);
		groups = dao.getAllGroupBeans();
		adapterBarProvide = new ActionBarItemProvider(this, groups);
		spinner_group.setAdapter(adapterBarProvide);
	}

	OnClickListener btn_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == btn_cancel.getId()) {
				final String userName = et_name.getText().toString();
				final String userpass = et_pass.getText().toString();
				if (TextUtils.isEmpty(userName.trim())
						&& TextUtils.isEmpty(userpass.trim())) {
					AddUsePassActivity.this.finish();
				} else {
					CustomDialogFragment dialog = CustomDialogFragment
							.newInstance(AddUsePassActivity.this, 11);
					// dialog.show(getSupportFragmentManager(), "aaaa");
					dialog.setTitle(getResources().getString(
							R.string._over_prompt));
					dialog.setMessage(getResources().getString(
							R.string._over_prompt_content));
					dialog.setPositiveButton(R.string.submit,
							mDialogClickListener);
					dialog.setNegativeButton(R.string.cancel, null);
					dialog.show(getSupportFragmentManager(), "aaaaa");
				}
			} else if (v.getId() == btn_submit.getId()) {
				final String userName = et_name.getText().toString();
				final String userpass = et_pass.getText().toString();
				final String userDecri = et_decri.getText().toString();
				final String userRelevanceAccount = et_relevance_account
						.getText().toString();
				if (TextUtils.isEmpty(userName.trim())
						|| TextUtils.isEmpty(userpass.trim())) {
					Util.showToast(AddUsePassActivity.this, "用户名或者密码不能为空...");
					return;
				}
				// 群组
				UserPassGroupBean bean = (UserPassGroupBean) adapterBarProvide
						.getItem(spinner_group.getSelectedItemPosition());
				UserPassItemBean itemBean = new UserPassItemBean();
				itemBean.setGroupID(String.valueOf(bean.getId()));
				itemBean.setUserName(userName);
				itemBean.setUserPass(userpass);
				itemBean.setUpdataTime(Util.getNowTime());
				if (rbt_two.isChecked()) {
					itemBean.setSecurityLevel(PassSetting.SECURITYLEVEL_TWO);
				} else if (rbt_one.isChecked()) {
					itemBean.setSecurityLevel(PassSetting.SECURITYLEVEL_ONE);
				} else if (rbt_three.isChecked()) {
					itemBean.setSecurityLevel(PassSetting.SECURITYLEVEL_THREE);
				}
				itemBean.setDescribe(null == userDecri ? "" : userDecri);
				itemBean.setUserRelevanceAccount(null == userRelevanceAccount ? ""
						: userRelevanceAccount);
				// 去重
				if (dao.saveUserPassItme(itemBean)) {
					//
					Intent it = new Intent();
					it.setClass(AddUsePassActivity.this,
							PassGroupInfoActivity.class);
					it.putExtra(PassSetting.default_intent_keyone,
							bean.getGroupName());
					it.putExtra(PassSetting.default_intent_keytwo, bean.getId());
					AddUsePassActivity.this.startActivity(it);
					AddUsePassActivity.this.finish();
				} else {
					Util.showToast(AddUsePassActivity.this, getResources()
							.getString(R.string._error_html));
				}
			}
		}
	};

	/**
	 * dialog以及确定、取消按钮监听
	 */
	private DialogInterface.OnClickListener mDialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (dialog != null) {
				// 取消浮层
				dialog.cancel();
			}
			AddUsePassActivity.this.finish();
		}
	};

}
