package com.hover.newsclientapp2.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private List<String> datas;
	private Context context;

	public CategoryAdapter(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		this.datas = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView tv;
		if (convertView == null) {
			GridView.LayoutParams lp2 = new GridView.LayoutParams(100, 80);
			tv = new TextView(context);
			tv.setTextSize(22);
			tv.setGravity(Gravity.CENTER);
			tv.setLayoutParams(lp2);
		} else {
			tv = (TextView) convertView;
		}
		tv.setText(datas.get(arg0));
		return tv;
	}

}
