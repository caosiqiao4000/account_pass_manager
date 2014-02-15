package com.siqi.geli.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.main.bundledisign.AgentManageActivity;
import com.siqi.geli.main.bundledisign.CompanyIntroductActivity;
import com.siqi.geli.main.bundledisign.FriendLinkActivity;
import com.siqi.geli.main.bundledisign.MoreManageActivity;
import com.siqi.geli.main.bundledisign.NewsActivity;
import com.siqi.geli.main.bundledisign.ProductListActivity;

/**
 * 自定义适配器
 * 
 * @author wulianghuan
 * 
 */
public class MyPagerAdapter extends PagerAdapter {
	private Vibrator vibrator;
	private ArrayList<ImageInfo> data;
	private Activity activity;
	private LayoutParams params;

	public MyPagerAdapter(Activity activity, ArrayList<ImageInfo> data) {
		this.activity = activity;
		this.data = data;
		vibrator = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int index) {
		Log.v("test", index + "index");

		View view = activity.getLayoutInflater().inflate(R.layout.grid, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);
		gridView.setAdapter(new BaseAdapter() {

			@Override
			public int getCount() {
				return data.size();
			}

			@Override
			public Object getItem(int position) {
				return data.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (null == convertView) {
					convertView = LayoutInflater.from(activity).inflate(
							R.layout.grid_item, null);
				}
				ImageView iv = (ImageView) convertView
						.findViewById(R.id.imageView1);
				RelativeLayout relativeLayout = (RelativeLayout) convertView
						.findViewById(R.id.relativeLayout);
				iv.setImageResource((data.get(position)).imageId);
				relativeLayout.setBackgroundResource((data.get(position)).bgId);
				relativeLayout.getBackground().setAlpha(225);
				TextView tv = (TextView) convertView.findViewById(R.id.msg);
				// tv.setText((data.get(index *8 + position)).imageMsg);
				tv.setText((data.get(position)).imageMsg);
				return convertView;
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {//产品总览
					Intent it = new Intent(activity,ProductListActivity.class);
					activity.startActivity(it);
				}else if (arg2 == 1 ) {//最新资讯
					Intent it = new Intent(activity,NewsActivity.class);
					activity.startActivity(it);
				}else if (arg2 == 2 ) {//企业介绍
					Intent it = new Intent(activity,CompanyIntroductActivity.class);
					activity.startActivity(it);
				}else if (arg2 == 3 ) {//加盟商家
					Intent it = new Intent(activity,AgentManageActivity.class);
					activity.startActivity(it);
				}else if (arg2 == 4) {//友情链接
					Intent it = new Intent(activity,FriendLinkActivity.class);
					activity.startActivity(it);
				}else if (arg2 == 5 ) {//更   多
					Intent it = new Intent(activity,MoreManageActivity.class);
					activity.startActivity(it);
				}
			}
		});
		((ViewPager) container).addView(view);

		return view;
	}
}
