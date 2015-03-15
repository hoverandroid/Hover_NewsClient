package com.hover.newsclientapp2.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hover.newsclientapp2.R;
import com.hover.newsclientapp2.DB.DBHelper;
import com.hover.newsclientapp2.adapter.CategoryAdapter;
import com.hover.newsclientapp2.constant.NewsConstant;
import com.hover.newsclientapp2.widget.MyApplication;

public class CategoryActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private static final String TAG = "CategoryActivity";
	private Map<String, String> categroyMap = new HashMap<String, String>();
	private DBHelper dbHelper;
	private Context context;
	private GridView grid1, grid2;
	private MyApplication application;
	private CategoryAdapter adapter1, adapter2;
	private List<String> showList, otherList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.category);
		application = (MyApplication) getApplication();
		categroyMap = application.getCategroyMap();
		initDB();
		grid1 = (GridView) findViewById(R.id.grid1);
		grid2 = (GridView) findViewById(R.id.grid2);
		showList = getDBShowTitle();
		otherList = getDBOtherTitle();
		Log.i(TAG, "onCreate--- showList--" + showList.toString()
				+ " otherList--" + otherList.toString());
		adapter1 = new CategoryAdapter(context, showList);
		adapter2 = new CategoryAdapter(context, otherList);
		grid1.setAdapter(adapter1);
		grid1.setOnItemClickListener(this);
		grid2.setAdapter(adapter2);
		grid2.setOnItemClickListener(this);
	}

	private void initDB() {
		dbHelper = new DBHelper(context);

		if (!dbHelper.isHasRecord()) {
			dbHelper.insert("cell01", "1");
			dbHelper.insert("cell02", "1");
			dbHelper.insert("cell03", "1");
			dbHelper.insert("cell04", "1");
			dbHelper.insert("cell05", "1");
			dbHelper.insert("cell06", "1");
			dbHelper.insert("cell07", "1");
			dbHelper.insert("cell08", "1");
		}
	}

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
		// Log.i(TAG, "getDBShowTitle---- " + list2.toString());
		return list2;
	}

	private List<String> getDBOtherTitle() {
		Map<String, String> map = dbHelper.selectAll();
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for (String str : map.keySet()) {
			if ("1".equals(map.get(str))) {
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
		// Log.i(TAG, "getDBOtherTitle---- " + list2.toString());
		return list2;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn1:
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.grid1:
			otherList.add(showList.get(position));
			// getDBOtherTitle().add(getDBShowTitle().get(position));
			dbHelper.update(getKeyByValue(showList.get(position)), "1");
			showList.remove(position);
			Log.i(TAG, "grid1---showList--" + showList.toString()
					+ "otherList--" + otherList.toString());
			adapter1.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();
			break;
		case R.id.grid2:
			showList.add(otherList.get(position));
			// getDBShowTitle().add(getDBOtherTitle().get(position));
			dbHelper.update(getKeyByValue(otherList.get(position)), "0");
			otherList.remove(position);
			Log.i(TAG, "grid1---showList--" + showList.toString()
					+ "otherList--" + otherList.toString());
			adapter1.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();
			break;
		default:
			break;
		}
		application.setShowList1((ArrayList<String>) getDBShowTitle());
		application.setOtherList((ArrayList<String>) getDBOtherTitle());
	}

	private String getKeyByValue(String value) {
		String result = null;
		for (String id : categroyMap.keySet()) {
			if (value.equals(categroyMap.get(id))) {
				result = id;
			}
		}
		return result;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		sendBroadcast(new Intent(NewsConstant.getInstance().CATEGORY_ACTION));
	}

}
