package com.shai.manage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.shai.manage.activity.PassSetting;
import com.shai.manage.fragment.ContentFragment;

@SuppressLint("NewApi")
public class DemoFragmentAdapter extends PagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction = null;
    
    private List<ContentFragment> mFragmentList = new ArrayList<ContentFragment>(4);
    
    public DemoFragmentAdapter(FragmentManager fm) {
        mFragmentManager = fm;

        mFragmentList.add(new ContentFragment("ViewPager#Frist",PassSetting.main_content_frist_flag));
        mFragmentList.add(new ContentFragment("ViewPager#Second",PassSetting.main_content_frist_flag));
        mFragmentList.add(new ContentFragment("ViewPager#Third",PassSetting.main_content_frist_flag));

    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        String name = getTag(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mTransaction.attach(fragment);
        } else {
            fragment = getItem(position);
            mTransaction.add(container.getId(), fragment,
                    getTag(position));
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        mTransaction.detach((Fragment) object);
    }
    @Override
    public void finishUpdate(ViewGroup container) {
        if (mTransaction != null) {
            mTransaction.commitAllowingStateLoss();
            mTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }
    public Fragment getItem(int position){
       return  mFragmentList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    protected  String getTag(int position){
        return mFragmentList.get(position).getText();
    }

}
