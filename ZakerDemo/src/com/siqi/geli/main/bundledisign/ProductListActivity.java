package com.siqi.geli.main.bundledisign;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.adapter.ProductBundleDesignAdapter;
import com.siqi.geli.main.BasePullRefreListViewActivity;
import com.siqi.geli.ui.PullToRefreshListView;

/**
 * 产品总览
 * 
 * @author Administrator
 * 
 */
public class ProductListActivity extends BasePullRefreListViewActivity
		implements OnClickListener {
	private TextView tv_title;
	private Button btn_left_title;
	private ProductBundleDesignAdapter adapter;
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
		super.findPullToRefreshListView((PullToRefreshListView) findViewById(R.id.prlv_buyer));

		tv_title.setText("产品总览");
		btn_left_title.setOnClickListener(this);
		designList = new ArrayList<String>();
		int a = 1;
		designList.add("这是产品总览" + a);
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		designList.add("这是产品总览" + (++a));
		adapter = new ProductBundleDesignAdapter(this, designList);
		lv_content.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_left_title.getId()) {
			ProductListActivity.this.finish();
		} else if (v.getId() == tv_title.getId()) {

		}

	}
}
