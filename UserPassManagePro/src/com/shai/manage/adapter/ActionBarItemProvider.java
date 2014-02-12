package com.shai.manage.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.respondbean.UserPassGroupBean;

/**
 * 提供actionBar的扩展选择
 * 
 * @author Administrator
 * 
 */
public class ActionBarItemProvider extends BaseAdapter {
	private List<UserPassGroupBean> groups;
	private Context mContext;

	public ActionBarItemProvider(Context context, List<UserPassGroupBean> groups) {
		this.groups = groups;
		this.mContext = context;
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
		// TODO Auto-generated method stub
		return groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserPassGroupBean bean = (UserPassGroupBean) getItem(position);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			// 横向
			convertView = inflater.inflate(R.layout.actionbar_adapter_item,
					parent, false);
		}

		ImageView iv_group_icon = (ImageView) convertView
				.findViewById(R.id.iv_group_icon);
		iv_group_icon.setImageResource(Integer.valueOf(bean.getIcon()));
		TextView tv_group_name = (TextView) convertView
				.findViewById(R.id.tv_group_name);
		tv_group_name.setText(bean.getGroupName());
		convertView.setTag(position);
		return convertView;
	}

}
