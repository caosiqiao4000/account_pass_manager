package com.sh.account.adapter;

import com.sh.account.fragment.GridViewFragment;
import com.sh.account.tool.AppSetting;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class DragableFragmentPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public DragableFragmentPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "DragableFragmentPagerAdapter getItem is call");
		}
	}

	@Override
	public Fragment getItem(int position) {
		if (getCount() - 1 == position) {// adv
			return Fragment.instantiate(mContext,
					GridViewFragment.class.getSimpleName());
		}
		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "DragableFragmentPagerAdapter getItem is call");
		}
		
		// 使用九宫格,超过一屏的,横向添加一屏,两屏
		return Fragment.instantiate(mContext,
				GridViewFragment.class.getSimpleName());
	}

	@Override
	public int getCount() {
		// 依据本地或者后台传回的数据条数判断,与分布类似
		return 3;
	}
}