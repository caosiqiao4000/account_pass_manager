package com.shai.manage.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.PassSetting;
import com.shai.manage.activity.other.AddUsePassActivity;
import com.shai.manage.activity.other.PassGroupInfoActivity;
import com.shai.manage.adapter.GridVAdapter;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.util.Util;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshBase.OnRefreshListener;
import com.siqiao.sdk.pull_listview.ui.PullToRefreshListView;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements OnItemClickListener, OnRefreshListener {
	// 标题栏标题
	private String title;
	// 创建哪一个内容view
	private int caseViewKey;
	private SatelliteMenu satellite_menu;
	private PullToRefreshListView prlv_main_content;
	private ListView lv_main_content;
	private ViewPager vPager;
	private Activity context;
	private List<UserPassGroupBean> groups;
	private GridVAdapter gridVAdapter;

	public ContentFragment(String text, int caseViewKey, List<UserPassGroupBean> groups) {
		Log.e("Krislq", text);
		this.title = text;
		this.caseViewKey = caseViewKey;
		this.groups = groups;
	}

	public ContentFragment(String text, int caseViewKey) {
		Log.e("Krislq", text);
		this.title = text;
		this.caseViewKey = caseViewKey;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		context = (Activity) getActivity();
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onCreate ");
		}
	}

	/**
	 * <!-- <GridView android:id="@+id/gridView1" android:layout_width="match_parent"
	 * android:layout_height="match_parent" android:layout_below="@+id/vp4_grid" android:columnWidth="60dp"
	 * android:horizontalSpacing="10dp" android:numColumns="auto_fit" android:padding="@dimen/layout_padd_or_margin_10"
	 * android:stretchMode="columnWidth" android:verticalSpacing="@dimen/layout_padd_or_margin_10" > </GridView> -->
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		switch (caseViewKey) {
		case PassSetting.main_content_frist_flag: // 主页
			// inflater the layout
			View view = inflater.inflate(R.layout.fragment_content_item, null);

			vPager = (ViewPager) view.findViewById(R.id.vp4_grid);
			prlv_main_content = (PullToRefreshListView) view.findViewById(R.id.prlv_main_content);
			gridVAdapter = new GridVAdapter(context, groups);
			lv_main_content = prlv_main_content.getRefreshableView();
			lv_main_content.setAdapter(gridVAdapter);
			lv_main_content.setOnItemClickListener(this);
			prlv_main_content.setOnRefreshListener(this);
			// ==========================
			satellite_menu = (SatelliteMenu) view.findViewById(R.id.satellite_menu);
			List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
			items.add(new SatelliteMenuItem(4, R.drawable.ic_1));
			items.add(new SatelliteMenuItem(4, R.drawable.ic_3));
			items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
//			items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
//			items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
			items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
			satellite_menu.addItems(items);
			if (PassSetting.Debug) {
				Util.showToast(context, "ContentFragment onCreateView");
			}
			satellite_menu.setOnItemClickedListener(new SateliteClickedListener() {
				public void eventOccured(int id) {
					if (PassSetting.Debug) {
						Util.showToast(context, "选中的ID = " + id);
					}
					if (id == 1) {// 增加一条用户信息
						Intent it = new Intent();
						it.setClass(context, AddUsePassActivity.class);
						context.startActivity(it);
					}
				}
			});

			return view;
		default:
			break;
		}
		return null;
	}

	@Override
	public void onDestroy() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onDestroy");
		}
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onDetach");
		}
		super.onDetach();
	}

	@Override
	public void onPause() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onPause");
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onResume");
		}
		super.onResume();
	}

	@Override
	public void onStart() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onStart");
		}
		super.onStart();
	}

	@Override
	public void onStop() {
		if (PassSetting.Debug) {
			Util.showToast(context, "ContentFragment onStop");
		}
		super.onStop();
	}

	// listview onItemClick
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserPassGroupBean bean = (UserPassGroupBean) gridVAdapter.getItem(arg2);
		Intent it = new Intent();
		it.setClass(getActivity(), PassGroupInfoActivity.class);
		it.putExtra(PassSetting.default_intent_keyone, bean.getGroupName());
		it.putExtra(PassSetting.default_intent_keytwo, bean.getId());
		getActivity().startActivity(it);
	}

	@Override
	public void onRefresh() {
		prlv_main_content.onRefreshComplete();
		
	}

}
