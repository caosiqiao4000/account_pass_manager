package com.shai.manage.adapter;

import java.util.List;

import android.R.plurals;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.respondbean.UserPassGroupBean;

public class ContentFragOneAdapter extends BaseAdapter {
	private Activity mContext;
	private List<UserPassGroupBean> groups;

	public ContentFragOneAdapter(Activity context, List<UserPassGroupBean> groups) {
		this.mContext = context;
		this.groups = groups;
	}

	@Override
	public int getCount() {
		if (null == groups) { 
			return 0;
		}
		return groups.size();
	}

	@Override
	public Object getItem(int position) {
		return groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserPassGroupBean bean = (UserPassGroupBean) getItem(position);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			// 方框
			convertView = inflater.inflate(R.layout.adap_grid_pass_group,
					parent, false);
		}

		ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		// 网络下载 组图标需要与域名做个绑定 智能推荐
		iv_icon.setImageResource(Integer.valueOf(bean.getIcon()));

		TextView tv_group_name = (TextView) convertView
				.findViewById(R.id.tv_groupname);
		TextView tv_descript = (TextView) convertView
				.findViewById(R.id.tv_descript);
		
		tv_group_name.setText(bean.getGroupName());
		tv_descript.setText(bean.getDecribe());
		convertView.setTag(position);
		return convertView;
	}

}
