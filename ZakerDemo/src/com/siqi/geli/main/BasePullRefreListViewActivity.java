package com.siqi.geli.main;

import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshBase.OnRefreshListener;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 
 * @author Administrator
 * 
 */
public abstract class BasePullRefreListViewActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener {
	protected PullToRefreshListView prlv_base;
	protected ListView lv_content;
	// 标题栏
	protected TextView tv_title;
	protected Button btn_left_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void findPullToRefreshListView(PullToRefreshListView prlv_base) {
		this.prlv_base = prlv_base;
		lv_content = prlv_base.getRefreshableView();
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_left_title = (Button) findViewById(R.id.btn_left_title);
		
		prlv_base.setOnRefreshListener(this);
		lv_content.setOnItemClickListener(this);
	}

}
