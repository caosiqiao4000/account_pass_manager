package com.shai.manage.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.userpassmanagepro.R;
import com.shai.manage.activity.PassSetting;

public class MenuFragment extends Fragment {

	private Context context;
	private ImageView iv_user_icon;
	private TextView tv_left_userName, tv_notepad, tv_concern;
	private ListView lv_menu_friend;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_menu_detail, container,
				false);
		iv_user_icon = (ImageView) view.findViewById(R.id.iv_user_icon);
		tv_left_userName = (TextView) view.findViewById(R.id.tv_left_userName);
		tv_notepad = (TextView) view.findViewById(R.id.tv_notepad);
		tv_concern = (TextView) view.findViewById(R.id.tv_concern);
		lv_menu_friend = (ListView) view.findViewById(R.id.lv_menu_friend);
//		if (PassSetting.Debug) {
//			Log.i(PassSetting.Debug_flag, "MenuFragment is onCreateView ...");
//		}
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (PassSetting.Debug) {
//			Log.i(PassSetting.Debug_flag, "MenuFragment is onCreate ...");
//		}
		context = getActivity();
	}

	public ImageView getIv_user_icon() {
		return iv_user_icon;
	}

	public TextView getTv_left_userName() {
		return tv_left_userName;
	}

	public TextView getTv_notepad() {
		return tv_notepad;
	}

	public TextView getTv_concern() {
		return tv_concern;
	}

	public ListView getLv_menu_friend() {
		return lv_menu_friend;
	}
}
