package com.hover.newsclientapp2.widget;

import java.util.ArrayList;
import java.util.Map;

import android.app.Application;

import com.hover.newsclientapp2.adapter.NewsFragmentAdapter;

public class MyApplication extends Application {
	private ArrayList<String> showList1, otherList;
	private Map<String, String> categroyMap;
	private NewsFragmentAdapter fragmentAdapter;
	private static MyApplication app = null;

	public static MyApplication getInstance() {
		if (app == null) {
			app = new MyApplication();
		}
		return app;
	}

	public ArrayList<String> getOtherList() {
		return otherList;
	}

	public void setOtherList(ArrayList<String> otherList) {
		this.otherList = otherList;
	}

	public ArrayList<String> getShowList1() {
		return showList1;
	}

	public void setShowList1(ArrayList<String> showList1) {
		this.showList1 = showList1;
	}

	public Map<String, String> getCategroyMap() {
		return categroyMap;
	}

	public void setCategroyMap(Map<String, String> categroyMap) {
		this.categroyMap = categroyMap;
	}

	public NewsFragmentAdapter getFragmentAdapter() {
		return fragmentAdapter;
	}

	public void setFragmentAdapter(NewsFragmentAdapter fragmentAdapter) {
		this.fragmentAdapter = fragmentAdapter;
	}

}
