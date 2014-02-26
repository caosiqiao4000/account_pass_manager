package com.siqi.geli.main.bundledisign;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.siqi.geli.R;
import com.siqi.geli.adapter.AgentManagerAdapter;
import com.siqi.geli.adapter.GeliSubsidiaryAdapter;
import com.siqi.geli.localbean.GeliSubsidiaryBean;
import com.siqi.geli.main.BasePullRefreListViewActivity;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshBase.OnRefreshListener;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 格力旗下
 * 
 * @author Administrator
 * 
 */
public class GeliSubsidiaryActivity extends BasePullRefreListViewActivity implements OnClickListener, OnRefreshListener {
	private List<GeliSubsidiaryBean> subBeans;
	private GeliSubsidiaryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_geli_sub);
		init();
	}

	private void init() {
		findPullToRefreshListView((PullToRefreshListView) findViewById(R.id.prlv_sub_geli));
		tv_title.setText(getResources().getString(R.string.a_title_geli_sub));
		btn_left_title.setOnClickListener(this);

		subBeans = new ArrayList<GeliSubsidiaryBean>();
		initDate(subBeans);
		adapter = new GeliSubsidiaryAdapter(this, subBeans);
		lv_content.setDividerHeight(0);
		lv_content.setAdapter(adapter);
		
		prlv_base.setOnRefreshListener(this);
	}

	private void initDate(List<GeliSubsidiaryBean> subBeans2) {
		GeliSubsidiaryBean bean1 = new GeliSubsidiaryBean();
		bean1.setShopName("广州市铎涛机电设备工程有限公司");
		bean1.setAddress("广州市天河区员村二横路13号 ");
		bean1.setShopNUM("");
		bean1.setScope("家用/商用/空气能热水器/小家电/冰箱");
		bean1.setPhone("02084179066");
		GeliSubsidiaryBean bean2 = new GeliSubsidiaryBean();
		bean2.setShopName("佛山格力专卖店");
		bean2.setAddress("广东省佛山市禅城富民5号");
		bean2.setShopNUM("粤E0089");
		bean2.setScope("家用/商用/小家电");
		bean2.setPhone("075882232201");
		GeliSubsidiaryBean bean3 = new GeliSubsidiaryBean();
		bean3.setShopName("中山格力专卖店");
		bean3.setAddress("广东省中山市石岐区富华大道28号");
		bean3.setShopNUM("粤T0064");
		bean3.setScope("家用/商用/空气能热水器/小家电/冰箱");
		bean3.setPhone("076086118758");
		GeliSubsidiaryBean bean4 = new GeliSubsidiaryBean();
		bean4.setShopName("云浮格力专卖店");
		bean4.setAddress("广东省云浮市云浮城中路50号");
		bean4.setShopNUM("粤W0123");
		bean4.setScope("家用/商用/小家电/空气能热水器");
		bean4.setPhone("07668933500");
		GeliSubsidiaryBean bean5 = new GeliSubsidiaryBean();
		bean5.setShopName("东莞格力专卖店");
		bean5.setAddress("广东省东莞市大岭山振华路102号");
		bean5.setShopNUM("粤S0070");
		bean5.setScope("家用/商用");
		bean5.setPhone("076985601888");

		subBeans2.add(bean1);
		subBeans2.add(bean2);
		subBeans2.add(bean3);
		subBeans2.add(bean4);
		subBeans2.add(bean5);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			this.finish();
		} else {

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
