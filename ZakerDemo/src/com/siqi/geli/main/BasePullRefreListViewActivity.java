package com.siqi.geli.main;

import android.os.Bundle;
import android.widget.ListView;

import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 
 * @author Administrator
 * 
 */
public abstract class BasePullRefreListViewActivity extends BaseActivity {
	protected PullToRefreshListView prlv_base;
	protected ListView lv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void findPullToRefreshListView(PullToRefreshListView prlv_base) {
		this.prlv_base = prlv_base;
		lv_content = prlv_base.getRefreshableView();
	}

}
