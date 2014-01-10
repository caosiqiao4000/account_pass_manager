package com.shai.manage.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userpassmanagepro.R;

public class MenuFragment extends Fragment {
	
	private Context context;
	private ImageView iv_user_icon;
	private TextView tv_left_userName;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_menu_detail, container,
				false);
		iv_user_icon = (ImageView) view.findViewById(R.id.iv_user_icon);
		tv_left_userName = (TextView) view.findViewById(R.id.tv_left_userName);
		
		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = getActivity();
		
		
		
	}
}
