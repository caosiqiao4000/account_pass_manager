package com.shai.manage.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.PassSetting;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class ContentFragment extends Fragment {
	// 标题栏标题
	private String tv_title;
	// 创建哪一个内容view
	private int caseViewKey;

	public ContentFragment() {
	}

	public ContentFragment(String text) {
		Log.e("Krislq", text);
		this.tv_title = text;
	}

	public ContentFragment(String text, int caseViewKey) {
		Log.e("Krislq", text);
		this.tv_title = text;
		this.caseViewKey = caseViewKey;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.e("Krislq", "onCreate:" + tv_title);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		switch (caseViewKey) {
		case PassSetting.main_content_frist_flag:
			Log.e("Krislq", "onCreateView:" + tv_title);
			// inflater the layout
			View view = inflater.inflate(R.layout.fragment_content_item, null);
			TextView textView = (TextView) view.findViewById(R.id.tv_title);
			if (!TextUtils.isEmpty(tv_title)) {
				textView.setText(tv_title);
			}

			ViewPager vpPager = (ViewPager) view.findViewById(R.id.vp4_grid);
			GridView gv = (GridView) view.findViewById(R.id.gridView1);

			return view;
		default:
			break;
		}

		// Log.e("Krislq", "onCreateView:" + text);
		// // inflater the layout
		// View view = inflater.inflate(R.layout.fragment_content_item, null);
		// TextView textView = (TextView) view.findViewById(R.id.textView);
		// if (!TextUtils.isEmpty(text)) {
		// textView.setText(text);
		// }
		return null;
	}

	public String getText() {
		return tv_title;
	}

	@Override
	public void onDestroy() {
		Log.e("Krislq", "onDestroy:" + tv_title);
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.e("Krislq", "onDetach:" + tv_title);
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.e("Krislq", "onPause:" + tv_title);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.e("Krislq", "onResume:" + tv_title);
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.e("Krislq", "onStart:" + tv_title);
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.e("Krislq", "onStop:" + tv_title);
		super.onStop();
	}

}
