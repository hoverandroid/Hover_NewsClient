package com.hover.newsclientapp2.ui.activity;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hover.newsclientapp2.R;

public final class TestFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";
	// ListView
	private PullToRefreshListView mPullRefreshListView;
	private LinearLayout footer_layout;
	private static Context cxt;
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;
	private String[] mStrings = { "aaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbb",
			"cccccccccccccccc", "dddddddddddddddddd", "eeeeeeeeeeeeeeeee",
			"fffffffffffffffff" };

	public static TestFragment newInstance(Context context, String content) {
		cxt = context;
		TestFragment fragment = new TestFragment();
		return fragment;
	}

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.viewpage_layout, container, false);
		footer_layout = (LinearLayout) view.findViewById(R.id.footer_layout);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(cxt,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetDataTask().execute();
					}
				});
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						footer_layout.setVisibility(View.VISIBLE);
						new GetDataTask2().execute();
					}
				});

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		mAdapter = new ArrayAdapter<String>(cxt,
				android.R.layout.simple_list_item_1, mListItems);

		/**
		 * Add Sound Event Listener
		 */
		// SoundPullEventListener<ListView> soundListener = new
		// SoundPullEventListener<ListView>(
		// cxt);
		// soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		// soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		// soundListener.addSoundEvent(State.REFRESHING,
		// R.raw.refreshing_sound);
		// mPullRefreshListView.setOnPullEventListener(soundListener);
		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		actualListView.setAdapter(mAdapter);
		return view;
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.clear();
			mListItems.addAll(Arrays.asList(mStrings));
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private class GetDataTask2 extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addAll(Arrays.asList(mStrings));
			mAdapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			footer_layout.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
