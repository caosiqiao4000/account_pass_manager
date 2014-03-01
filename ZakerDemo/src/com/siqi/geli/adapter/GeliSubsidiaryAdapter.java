package com.siqi.geli.adapter;

import java.util.List;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.siqi.geli.R;
import com.siqi.geli.localbean.GeliSubsidiaryBean;
import com.siqi.geli.main.bundledisign.GeliSubsidiaryActivity;
import com.siqiao.sdk.common.util.UtilByPhoneHardware;

public class GeliSubsidiaryAdapter extends BaseAdapter implements OnClickListener {

	private GeliSubsidiaryActivity activity;
	private List<GeliSubsidiaryBean> subBeans;

	public GeliSubsidiaryAdapter(GeliSubsidiaryActivity geliSubsidiaryActivity, List<GeliSubsidiaryBean> subBeans) {
		this.activity = geliSubsidiaryActivity;
		this.subBeans = subBeans;
	}

	@Override
	public int getCount() {
		return subBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return subBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final GeliSubsidiaryBean item = (GeliSubsidiaryBean) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.apa_gelisub_item, null);
		}
		//
		TextView tv_title_name = (TextView) convertView.findViewById(R.id.tv_title_name);
		TextView tv_shop_phone = (TextView) convertView.findViewById(R.id.tv_shop_phone);
		// 经营范围
		TextView tv_shop_scope = (TextView) convertView.findViewById(R.id.tv_shop_scope);
		tv_title_name.setText(item.getShopName());
		Resources resources = activity.getResources();
		tv_shop_phone.setText(resources.getString(R.string.user_phone_info) + item.getPhone());
		//
		TextView tv_shop_address = (TextView) convertView.findViewById(R.id.tv_shop_address);
		tv_shop_address.setText(resources.getString(R.string.user_address_info) + item.getAddress());
		tv_shop_scope.setText(resources.getString(R.string.user_shop_spoce) + item.getScope());
		// =======================================
		Button btn_call = (Button) convertView.findViewById(R.id.submit);
		Button btn_toweburl = (Button) convertView.findViewById(R.id.cancel);
		btn_call.setText("拨打电话");
		btn_toweburl.setText("前往官网");
		btn_call.setOnClickListener(this);
		btn_call.setTag(position);
		btn_toweburl.setOnClickListener(this);
		// ======================================
		return convertView;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.submit) { // 打电话
			int position = (Integer) v.getTag();
			final GeliSubsidiaryBean item = (GeliSubsidiaryBean) getItem(position);
			UtilByPhoneHardware.toOSCallPhoneIntent(activity, item.getPhone());
		}
	}

}
