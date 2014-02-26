package com.siqi.geli.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.localbean.AgentInfo;
import com.siqi.geli.main.bundledisign.AgentManageActivity;
import com.siqiao.sdk.common.util.PhoneHardwareUtil;

public class AgentManagerAdapter extends BaseAdapter implements OnClickListener {
	private Activity activity;
	private List<AgentInfo> designList;

	public AgentManagerAdapter(AgentManageActivity productBundleDesignActivity, List<AgentInfo> designList) {
		this.activity = productBundleDesignActivity;
		this.designList = designList;
	}

	@Override
	public int getCount() {
		return designList.size();
	}

	@Override
	public Object getItem(int position) {
		return designList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final AgentInfo item = designList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.users_item, null);
		}
		//  
		ImageView user_icon = (ImageView) convertView.findViewById(R.id.iv_usericon);
		TextView tv_userName = (TextView) convertView.findViewById(R.id.tv_username);
		TextView tv_phone_num = (TextView) convertView.findViewById(R.id.tv_usersign);
		ImageView iv_into = (ImageView) convertView.findViewById(R.id.iv_into);
		user_icon.setImageResource(R.drawable.nav_head);
		tv_userName.setText(item.getName());
		tv_phone_num.setText(activity.getResources().getString(R.string.user_phone_info) + item.getMobile());
		//
		TextView tv_email = (TextView) convertView.findViewById(R.id.tv_email);
		tv_email.setText(item.getEmail());
		TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address);
		tv_address.setText(item.getAddress());
		TextView tv_weburl = (TextView) convertView.findViewById(R.id.tv_weburl);
		tv_weburl.setOnClickListener(this);
		tv_weburl.setTag(position);

		// ======================================
		convertView.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_weburl) {
			int position = (Integer) v.getTag();
			final AgentInfo item = (AgentInfo) getItem(position);
			PhoneHardwareUtil.toOSWebViewIntent(activity, item.getWebUrl());
		} else {
			View llview = v.findViewById(R.id.ll_hide_info);
			if (llview.getVisibility() == View.GONE) {
				llview.setVisibility(View.VISIBLE);
			} else {
				llview.setVisibility(View.GONE);
			}
		}
	}

}
