package com.hover.newsclientapp2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.hover.newsclientapp2.DB.DBHelper;
import com.hover.newsclientapp2.adapter.NewsFragmentAdapter;
import com.hover.newsclientapp2.constant.NewsConstant;
import com.hover.newsclientapp2.ui.activity.CategoryActivity;
import com.hover.newsclientapp2.widget.MyApplication;

public class NewsActivity extends ActionBarActivity {
	// activity
	private String[] menutitles = new String[] { "搜索", "设置", "收藏", "通知", "离线",
			"活动", "反馈", "精彩应用", "夜间模式" };
	private List<Activity> activityList = new ArrayList<Activity>();
	private List<String> contentlist = new ArrayList<String>();
	private Context context;
	private MyApplication application;
	private MyRecevier receiver;
	private DBHelper dbHelper;
	private Map<String, String> categroyMap = new HashMap<String, String>();
	private static final String TAG = "NewsActivity";
	// DrawerLayout
	private DrawerLayout mDrawerLayout;
	private ListView mDrawer;
	private ActionBarHelper mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	// tabs
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private NewsFragmentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_main);
		context = this;
		registerReceiver();
		initDate();
		initDrawerLayout();
		initTabs();
	}

	/**
	 * 初始化Category数据
	 */
	private void initDate() {
		application = (MyApplication) getApplication();
		categroyMap.put("cell01", "热点");
		categroyMap.put("cell02", "电影");
		categroyMap.put("cell03", "直播");
		categroyMap.put("cell04", "娱乐");
		categroyMap.put("cell05", "科技");
		categroyMap.put("cell06", "军事");
		categroyMap.put("cell07", "记录");
		categroyMap.put("cell08", "综艺");
		application.setCategroyMap(categroyMap);
		dbHelper = new DBHelper(context);
	}

	/**
	 * 初始化Tabs
	 */
	private void initTabs() {
		contentlist.add("头条");
		contentlist.add("精选");
		contentlist.add("新闻");
		contentlist.addAll(getDBShowTitle());
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new NewsFragmentAdapter(getSupportFragmentManager(),
				contentlist, context);
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		tabs.setViewPager(pager);
	}

	/**
	 * Category广播
	 */
	class MyRecevier extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.i(TAG, "onReceive()-----" + getDBShowTitle().toString());
			contentlist.clear();
			contentlist.add("头条");
			contentlist.add("精选");
			contentlist.add("新闻");
			contentlist.addAll(getDBShowTitle());
			adapter.setDatas(contentlist);
			tabs.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		}
	}

	private void registerReceiver() {
		receiver = new MyRecevier();
		IntentFilter filter = new IntentFilter();
		filter.addAction(NewsConstant.getInstance().CATEGORY_ACTION);
		registerReceiver(receiver, filter);
	}

	/**
	 * 获取可展示tab
	 */
	private List<String> getDBShowTitle() {
		Map<String, String> map = dbHelper.selectAll();
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for (String str : map.keySet()) {
			if ("0".equals(map.get(str))) {
				list.add(str);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			for (String str : categroyMap.keySet()) {
				if (str.equals(list.get(i))) {
					list2.add(categroyMap.get(str));
				}
			}
		}
		return list2;
	}

	/**
	 * 初始化DrawerLayout
	 */
	private void initDrawerLayout() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawer = (ListView) findViewById(R.id.start_drawer);
		mDrawerLayout.setDrawerListener(new DemoDrawerListener());
		mDrawerLayout.setDrawerTitle(GravityCompat.START,
				getString(R.string.drawer_title));
		mDrawer.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menutitles));
		mDrawer.setOnItemClickListener(new DrawerItemClickListener());
		mActionBar = createActionBarHelper();
		mActionBar.init();
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open, R.string.drawer_close);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mDrawerLayout.closeDrawer(mDrawer);
		}
	}

	private class DemoDrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerOpened(View drawerView) {
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	private ActionBarHelper createActionBarHelper() {
		return new ActionBarHelper();
	}

	private class ActionBarHelper {
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		ActionBarHelper() {
			mActionBar = getSupportActionBar();
		}

		public void init() {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(false);
			mTitle = mDrawerTitle = getTitle();
		}

		public void onDrawerClosed() {
			mActionBar.setTitle(mTitle);
		}

		public void onDrawerOpened() {
			mActionBar.setTitle(mDrawerTitle);
		}

		public void setTitle(CharSequence title) {
			mTitle = title;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.add_category:
			Intent intent = new Intent();
			intent.setClass(NewsActivity.this, CategoryActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}
