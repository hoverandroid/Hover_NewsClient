package com.hover.newsclientapp2.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hover.newsclientapp2.ui.activity.TestFragment;

public class NewsFragmentAdapter extends FragmentPagerAdapter {

	private static final String TAG = "NewsFragmentAdapter";
	private List<String> datas;
	private Context ctx;

	public NewsFragmentAdapter(FragmentManager fm, List<String> data,
			Context context) {
		super(fm);
		datas = data;
		ctx = context;
	}

	@Override
	public Fragment getItem(int position) {
		return TestFragment.newInstance(ctx, datas.get(position));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.i(TAG, "charSequence---" + datas.toString());
		return datas.get(position);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	public List<String> getDatas() {
		return datas;
	}

	public void setDatas(List<String> datas) {
		this.datas = datas;
	}

}
