package com.sh.account.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.useraccountmanangemainproject.R;
import com.sh.account.bean.UserInfoBean;
import com.sh.account.respondbean.InfoGroupBean;
import com.sh.account.respondbean.UserInfoGroupBean;
import com.sh.account.tool.AppSetting;

public class GridViewFragment extends Fragment {

	private InfoGroupBean infoGroupBean;// 用户使用列表
	private Context context;
	private LayoutInflater flater;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// getListView().setBackgroundColor(Color.WHITE);
		// setListAdapter(ArrayAdapter.createFromResource(getActivity(),
		// R.array.data, android.R.layout.simple_list_item_1));
		context = getActivity();
		flater = getActivity().getLayoutInflater();
		GridView gridView = (GridView) flater.inflate(R.layout.layout_gridv, null);
		initData(gridView);

		if (AppSetting.APPDEBUG) {
			Log.i(AppSetting.APPDEBUG_FLAG, "GridViewFragment onActivityCreated is call");
		}
	}

	private void initData(GridView gridView) {
		infoGroupBean = new InfoGroupBean();
		List<UserInfoGroupBean> groupBeans = new ArrayList<UserInfoGroupBean>();
		UserInfoGroupBean bean1 = new UserInfoGroupBean();
		bean1.setGroupDescribe("csdn的一个帐户");
		bean1.setGroupIDName("CSDN");
		bean1.setGroupLevelByGroupId("1");
		bean1.setRecentUseNum(0);
		bean1.setUserGroupID(0x001);

		UserInfoBean userBean = new UserInfoBean();
		// 信息的重要性 1:一般 2:重要 3:危险
		userBean.setImportanceLevel(1);
		userBean.setInfoByFristGroupId(15);
		userBean.setInfoBySecondGroupId(10);
		userBean.setInfoByThreeGroupId(1);
		userBean.setInfoDescribe("11年注册的");
		List<String> paList = new ArrayList<String>();
		paList.add("123456");
		paList.add("456789");
		paList.add("456782");
		paList.add("456781");
		paList.add("456783");
		userBean.setPassList(paList);
		userBean.setQuestionOne("我的电脑是什么品牌");
		userBean.setQuestionOneAnswer("thankpad");
		List<UserInfoBean> infoBeans = new ArrayList<UserInfoBean>();
		infoBeans.add(userBean);

		bean1.setInfos(infoBeans);
		groupBeans.add(bean1);
		infoGroupBean.setGroupBeans(groupBeans);

		GridAdapter adapter = new GridAdapter(context, infoGroupBean.getGroupBeans());
		gridView.setAdapter(adapter);
	}

	// 根据使用次数排序
	private class GridAdapter extends BaseAdapter {
		private Context context;
		private List<UserInfoGroupBean> object;

		public GridAdapter(Context content, List<UserInfoGroupBean> object) {
			this.context = content;
			this.object = object;
		}

		@Override
		public int getCount() {
			return object.size();
		}

		@Override
		public Object getItem(int position) {
			return object.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			UserInfoGroupBean groupBean = (UserInfoGroupBean) getItem(position);

			if (null == convertView) {
				convertView = flater.inflate(R.layout.v_item_by_gridview, null);
			}

			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_group_icon);
			ProgressBar pb = (ProgressBar) convertView.findViewById(R.id.pb_group);
			TextView tv = (TextView) convertView.findViewById(R.id.tv_group_name);

			iv.setImageResource(R.drawable.ic_launcher);
			tv.setText(groupBean.getGroupIDName());

			return convertView;
		}
	}
}
