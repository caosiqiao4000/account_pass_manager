package com.siqi.geli.main.bundledisign;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.adapter.FriendLinkAdapter;
import com.siqi.geli.main.BasePullRefreListViewActivity;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 友情链接
 * 
 * @author Administrator
 * 
 */
public class FriendLinkActivity extends BasePullRefreListViewActivity implements
		OnClickListener {
	private TextView tv_title;
	private Button btn_left_title;
	private FriendLinkAdapter adapter;
	private List<String> designList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_product_design);
		init();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_left_title = (Button) findViewById(R.id.btn_left_title);
		super.findPullToRefreshListView((PullToRefreshListView) findViewById(R.id.prlv_productlist));

		btn_left_title.setOnClickListener(this);
		designList = new ArrayList<String>();
		int a = 1;
		tv_title.setText("友情链接");
		designList.add("这是友情链接" + a);
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		designList.add("这是友情链接" + (++a));
		adapter = new FriendLinkAdapter(this, designList);
		lv_content.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			FriendLinkActivity.this.finish();
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
