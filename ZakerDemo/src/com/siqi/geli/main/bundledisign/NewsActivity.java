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
import com.siqi.geli.adapter.NewsAdapter;
import com.siqi.geli.main.BasePullRefreListViewActivity;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 模块设计
 * 
 * @author Administrator
 * 
 */
public class NewsActivity extends BasePullRefreListViewActivity implements OnClickListener {
	private TextView tv_title;
	private Button btn_left_title;
	private NewsAdapter adapter;
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
		tv_title.setText("最新资讯");
		designList.add("这是最新资讯" + a);
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		designList.add("这是最新资讯" + (++a));
		adapter = new NewsAdapter(this, designList);
		lv_content.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			NewsActivity.this.finish();
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
