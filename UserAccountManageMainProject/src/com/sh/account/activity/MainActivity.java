package com.sh.account.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.useraccountmanangemainproject.R;
import com.sh.account.adapter.DragableFragmentPagerAdapter;

public class MainActivity extends BaseSlideMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		setSlideRole(R.layout.layout_slidemenu_with_view_pager);
//		setSlideRole(R.layout.layout_primary_menu);
		setSlideRole(R.layout.layout_secondary_menu);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new DragableFragmentPagerAdapter(this,
				getSupportFragmentManager()));
	}
}
