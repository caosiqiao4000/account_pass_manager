package com.shai.manage.activity.addinfo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.BaseActivity;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.ui.CustomDialogFragment;
import com.shai.manage.util.Util;
import com.siqiao.sdk.common.util.UtilByDateAndString;

public class AddUserGroupInfoActivity extends BaseActivity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private EditText et_group_decri, et_group_name;
	private Button btn_submit, btn_cancel;
	private UserLocalGroupDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.title_addgroup));
		setContentView(R.layout.a_add_group);

		init();
	}

	private void init() {
		et_group_decri = (EditText) findViewById(R.id.et_group_decri);
		et_group_name = (EditText) findViewById(R.id.et_group_name);

		btn_submit = (Button) findViewById(R.id.submit);
		btn_cancel = (Button) findViewById(R.id.cancel);

		btn_cancel.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		dao = new UserLocalGroupDaoIml(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_cancel.getId()) {
			final String t_group_decri = et_group_decri.getText().toString();
			final String t_group_name = et_group_name.getText().toString();
			if (TextUtils.isEmpty(t_group_decri.trim()) && TextUtils.isEmpty(t_group_name.trim())) {
				AddUserGroupInfoActivity.this.finish();
			} else {
				CustomDialogFragment dialog = CustomDialogFragment.newInstance(AddUserGroupInfoActivity.this, 11);
				// dialog.show(getSupportFragmentManager(), "aaaa");
				dialog.setTitle(getResources().getString(R.string._over_prompt));
				dialog.setMessage(getResources().getString(R.string._over_prompt_content));
				dialog.setPositiveButton(R.string.submit, this);
				dialog.setNegativeButton(R.string.cancel, null);
				dialog.show(getSupportFragmentManager(), "aaaaa");
			}
		} else if (v.getId() == btn_submit.getId()) {//提交一组信息
			final String t_group_decri = et_group_decri.getText().toString();
			final String t_group_name = et_group_name.getText().toString();
			if (TextUtils.isEmpty(t_group_decri.trim()) || TextUtils.isEmpty(t_group_name.trim())||t_group_decri.length()<11) {
				UtilByDateAndString.showToast(this, "请填写组名称 或者 10字以上的描述");
				return;
			}
			UserPassGroupBean bean = new UserPassGroupBean();
			bean.setDecribe(t_group_decri);
			bean.setGroupName(t_group_name);
			bean.setUseNum(0);
			dao.saveUserPassGroup(bean);
		}
	}

	// 对话框点击事件
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (dialog != null) {
			// 取消浮层
			dialog.cancel();
		}
		AddUserGroupInfoActivity.this.finish();
	}

}
