package com.shai.manage.adapter;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.respondbean.UserPassItemBean;

public class GroupInfoAdapter extends BaseAdapter {
	private List<UserPassItemBean> beans;
	private Activity context;

	public GroupInfoAdapter(Activity context, List<UserPassItemBean> beans) {
		this.beans = beans;
		this.context = context;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserPassItemBean bean = (UserPassItemBean) getItem(position);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			// 方框
			convertView = inflater.inflate(R.layout.adap_list_query, parent,
					false);
		}

		ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		// 网络下载 组图标需要与域名做个绑定 智能推荐
		iv_icon.setImageResource(R.drawable.icon_sina_on);
		//
		TextView tv_title_name = (TextView) convertView
				.findViewById(R.id.tv_title_name);
		tv_title_name.setText(bean.getUserName());
		// 点击进入
		TextView tv_clickInto = (TextView) convertView
				.findViewById(R.id.tv_clickshop);
		tv_clickInto.setOnClickListener(viewClick);
		Resources resources = context.getResources();
		// 关联帐号
		TextView tv_relevance_account = (TextView) convertView
				.findViewById(R.id.tv_relevance_account);
		final String tempStr = (null == bean.getUserRelevanceAccount() ? "无"
				: bean.getUserRelevanceAccount());
		final String tempStr2 = resources
				.getString(R.string.user_relevance_account);
		tv_relevance_account.setText(tempStr2 + tempStr);
		//
		TextView user_pass = (TextView) convertView
				.findViewById(R.id.user_pass);
		user_pass.setText(resources.getString(R.string.user_pass)
				+ bean.getUserPass());
		//
		TextView pass_security_level = (TextView) convertView
				.findViewById(R.id.pass_security_level);
		pass_security_level.setText(resources
				.getString(R.string.pass_security_level)
				+ bean.getSecurityLevel());
		TextView tv_descri = (TextView) convertView
				.findViewById(R.id.tv_descri);
		tv_descri.setText(bean.getDescribe());

		//
		convertView.setTag(position);
		return convertView;
	}

	OnClickListener viewClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

}
