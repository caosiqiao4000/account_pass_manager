package com.siqi.geli.main.bundledisign;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.adapter.AgentManagerAdapter;
import com.siqi.geli.localbean.AgentInfo;
import com.siqi.geli.main.BasePullRefreListViewActivity;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 加盟商家
 * 
 * @author Administrator
 * 
 */
public class AgentManageActivity extends BasePullRefreListViewActivity implements OnClickListener {
	private AgentManagerAdapter adapter;
	private List<AgentInfo> designList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_agent_manage);
		init();
	}

	private void init() {
		super.findPullToRefreshListView((PullToRefreshListView) findViewById(R.id.prlv_news));

		btn_left_title.setOnClickListener(this);
		designList = new ArrayList<AgentInfo>();
		tv_title.setText("加盟商家");

		int a = 0;

		AgentInfo infoa = new AgentInfo();
		infoa.setName("广州市择涛机电设备工程有限公司 ");
		infoa.setUserName("店主名-张xx" + (a));
		infoa.setMobile("15920508470");
		infoa.setAddress(getResources().getString(R.string.user_address_info) + "广州市天河区员村二横路13号");
		infoa.setEmail(getResources().getString(R.string.user_email_info) + "geli120@163.com");
		infoa.setWebUrl("http://www.duotaozx.com");
		designList.add(infoa);

		for (int i = 0; i < 5; i++) {
			AgentInfo info = new AgentInfo();
			info.setName("我是加盟商家 " + (++a));
			info.setUserName("店主名-张xx" + (a));
			info.setMobile("158888888" + a);
			info.setWebUrl("http://www.duotaozx.com");
			info.setEmail(getResources().getString(R.string.user_email_info)+"geli120@163.com");
			info.setAddress(getResources().getString(R.string.user_address_info) + "无地址");
			designList.add(info);
		}
		adapter = new AgentManagerAdapter(this, designList);
		lv_content.setDividerHeight(0);
		lv_content.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			AgentManageActivity.this.finish();
		} else if (v.getId() == tv_title.getId()) {

		}

	}

	@Override
	public void onRefresh() {
		prlv_base.onRefreshComplete();

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
}
