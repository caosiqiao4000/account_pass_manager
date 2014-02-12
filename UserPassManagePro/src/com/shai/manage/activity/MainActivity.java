package com.shai.manage.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.userpassmanagepro.R;
import com.shai.manage.adapter.ActionBarItemProvider;
import com.shai.manage.db.UserLocalGroupDao;
import com.shai.manage.db.UserLocalGroupDaoIml;
import com.shai.manage.fragment.ContentFragment;
import com.shai.manage.fragment.MenuFragment;
import com.shai.manage.impl.OnNavigationListenerImpl;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.util.Util;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

/**
 * 主程序界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends SlidingActivity {
	private ActionBar actionBar;
	private ActionBarItemProvider adapterBarProvide;
	private List<UserPassGroupBean> groups;
	private OnNavigationListener mOnNavigationListener;
	private UserLocalGroupDao dao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.title_main));
		setContentView(R.layout.frame_content);

		dao = new UserLocalGroupDaoIml(this);
		groups = dao.getAllGroupBeans();
		initView();
		initData();
	}

	private void initData() {
		adapterBarProvide = new ActionBarItemProvider(this, groups);

		mOnNavigationListener = new OnNavigationListenerImpl(this);

		actionBar.setListNavigationCallbacks(adapterBarProvide,
				mOnNavigationListener);
	}

	private void initView() {

		// set the Behind View 菜单
		setBehindContentView(R.layout.frame_menu);

		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		MenuFragment menuFragment = new MenuFragment();
		fragmentTransaction.replace(R.id.menu, menuFragment);
		fragmentTransaction.replace(R.id.content, new ContentFragment(
				getString(R.string.welcome),PassSetting.main_content_frist_flag,groups), "Welcome");

		fragmentTransaction.commit();

		initSlidingMenu();
		actionBar = getActionBar();
		// 使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	}

	private void initSlidingMenu() {
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(150);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(80);
		sm.setFadeDegree(0.35f);
		// 设置slding menu的几种手势模式
		// TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
		// TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding
		// menu
		// TOUCHMODE_NONE 自然是不能通过手势打开啦
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		// MenuItem actionItem = menu.findItem(R.id.menu_actionbar_shared);
		// ShareActionProvider actionProvider = (ShareActionProvider) actionItem
		// .getActionProvider();
		// actionProvider
		// .setShareHistoryFileName(PassSetting.DEFAULT_SHARE_HISTORY_FILE_NAME);

		// actionProvider.setShareIntent(createSharedIntent());
		return true;
	}

	// 创建一个分享的intent
	private Intent createSharedIntent() {
		// 如果改成其他的系统intent 也可以直接使用系统的 ShareActionProvider
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// toggle就是程序自动判断是打开还是关闭
			toggle();
			return true;
		case R.id.menu_actionbar_shared:
			if (PassSetting.Debug) {
				Util.showToast(MainActivity.this,
						"点击了  menu_select_group actionaBar");
			}
			// getSlidingMenu().showMenu();// show menu
			// getSlidingMenu().showContent();//show content
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}