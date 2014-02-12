package com.shai.manage.activity.other;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.BaseActivity;
import com.shai.manage.activity.PassSetting;
import com.shai.manage.adapter.GroupInfoAdapter;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.respondbean.UserPassItemBean;

/**
 * 依据传入的组ID查看相应的密码信息
 * 
 * @author Administrator
 * 
 */
public class PassGroupInfoActivity extends BaseActivity {
	private ListView lv_infos;
	private UserLocalGroupDao dao;
	private GroupInfoAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_group_info);
		Intent it = getIntent(); 
		setTitle(it.getStringExtra(PassSetting.default_intent_keyone));
		init(it.getIntExtra(PassSetting.default_intent_keytwo, 1));
	}

	// 组ID
	private void init(int groupid) {
		dao = new UserLocalGroupDaoIml(this);
		lv_infos = (ListView) findViewById(R.id.lv_infos);
		List<UserPassItemBean> beans = dao.getAllGroupItemBeans(String
				.valueOf(groupid));
		if (beans.size() > 0) {
			mAdapter = new GroupInfoAdapter(PassGroupInfoActivity.this, beans);
			lv_infos.setAdapter(mAdapter);
		}
	}
}
