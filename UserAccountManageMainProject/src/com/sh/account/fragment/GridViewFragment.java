package com.sh.account.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.provider.Contacts.ContactMethods;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.useraccountmanangemainproject.R;

public class GridViewFragment extends Fragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// getListView().setBackgroundColor(Color.WHITE);
		// setListAdapter(ArrayAdapter.createFromResource(getActivity(),
		// R.array.data, android.R.layout.simple_list_item_1));
		LayoutInflater flater = getActivity().getLayoutInflater();
		GridView gridView = (GridView) flater.inflate(R.layout.layout_gridv,
				null);
		gridView.setAdapter(null);

	}

	// 根据使用次数排序
	private class GridAdapter extends BaseAdapter {
		private Context context;
		private List<?> object;

		public GridAdapter(Context content, List<?> object) {
			this.context = content;
			this.object = object;
		}

		@Override
		public int getCount() {

			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
