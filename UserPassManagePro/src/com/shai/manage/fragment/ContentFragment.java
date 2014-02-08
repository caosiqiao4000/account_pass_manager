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
import android.widget.GridView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.PassSetting;
import com.shai.manage.activity.other.AddUsePassActivity;
import com.shai.manage.util.Util;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class ContentFragment extends Fragment {
	// 标题栏标题
	private String title;
	// 创建哪一个内容view
	private int caseViewKey;
	private SatelliteMenu satellite_menu;
	private GridView gridView;
	private ViewPager vPager;
	private Activity context;

	public ContentFragment(String text, int caseViewKey) {
		Log.e("Krislq", text);
		this.title = text;
		this.caseViewKey = caseViewKey;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.e("Krislq", "onCreate:");
		context = (Activity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		switch (caseViewKey) {
		case PassSetting.main_content_frist_flag:
			Log.e("Krislq", "onCreateView:");
			// inflater the layout
			View view = inflater.inflate(R.layout.fragment_content_item, null);
			// if (!TextUtils.isEmpty(tv_title)) {
			// textView.setText(tv_title);
			// }

			vPager = (ViewPager) view.findViewById(R.id.vp4_grid);
			gridView = (GridView) view.findViewById(R.id.gridView1);
			// ==========================
			satellite_menu = (SatelliteMenu) view
					.findViewById(R.id.satellite_menu);
			List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
			items.add(new SatelliteMenuItem(4, R.drawable.ic_1));
			items.add(new SatelliteMenuItem(4, R.drawable.ic_3));
			items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
			items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
			items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
			items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
			satellite_menu.addItems(items);
			satellite_menu
					.setOnItemClickedListener(new SateliteClickedListener() {
						public void eventOccured(int id) {
							if (PassSetting.Debug) {
								Util.showToast(context, "选中的ID = " + id);
							}
							if (id == 1) {// 增加一条用户信息
								Intent it = new Intent();
								it.setClass(context,AddUsePassActivity.class);
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

	public String getText() {
		return title;
	}

	@Override
	public void onDestroy() {
		Log.e("Krislq", "onDestroy:");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.e("Krislq", "onDetach:");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.e("Krislq", "onPause:");
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.e("Krislq", "onResume:");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.e("Krislq", "onStart:");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.e("Krislq", "onStop:");
		super.onStop();
	}

}
