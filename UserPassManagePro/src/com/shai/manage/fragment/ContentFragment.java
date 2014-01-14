package com.shai.manage.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
	private String title;
	// 创建哪一个内容view
	private int caseViewKey;

	private GridView gridView;
	private ViewPager vPager;
	private Context context;

	public ContentFragment(String text, int caseViewKey) {
		Log.e("Krislq", text);
		this.title = text;
		this.caseViewKey = caseViewKey;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.e("Krislq", "onCreate:" );
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
