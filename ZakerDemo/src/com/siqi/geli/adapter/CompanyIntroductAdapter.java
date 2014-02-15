package com.siqi.geli.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.main.bundledisign.CompanyIntroductActivity;

public class CompanyIntroductAdapter extends BaseAdapter {
private Activity activity;
private List<String> designList;
	public CompanyIntroductAdapter(
			CompanyIntroductActivity productBundleDesignActivity,
			List<String> designList) {
		this.activity = productBundleDesignActivity;
		this.designList = designList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return designList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return designList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String temp = (String) getItem(position);
		if (null == convertView) {
			convertView = activity.getLayoutInflater().inflate(R.layout.adap_simple_tv, null);
		}
		TextView tv_one = (TextView) convertView.findViewById(R.id.tv_one);
		tv_one.setText(temp);
		return convertView;
	}

}
