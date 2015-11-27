package com.basedamo.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.basedamo.R;

public class ListViewTestActivity extends Activity implements OnClickListener {
	private String TAG = "ListViewTestActivity";
	private ListView lv_list;
	private ListAdapter listAdapter;
	private int index = 0;
	private Button bt_listview_up;
	private Button bt_listview_down;
	private Button bt_listview_addpage;
	private TextView lvFootView;
	private Button bt_listview_toTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listviewcontrol);
		initViews();
		initDatas();
		
	}

	private void initViews() {
		lv_list = (ListView) findViewById(R.id.lv_list);
		bt_listview_up = (Button) findViewById(R.id.bt_listview_up);
		bt_listview_down = (Button) findViewById(R.id.bt_listview_down);
		bt_listview_addpage = (Button) findViewById(R.id.bt_listview_addpage);
		bt_listview_toTop = (Button) findViewById(R.id.bt_listview_toTop);
		bt_listview_up.setOnClickListener(this);
		bt_listview_down.setOnClickListener(this);
		bt_listview_addpage.setOnClickListener(this);
		bt_listview_toTop.setOnClickListener(this);
		

		lv_list.setOnScrollListener(new OnScrollListener() {
			int firstVisibleItem=-1;
			int visibleItemCount=-1;
			int totalItemCount=-1;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE==scrollState){
					if (firstVisibleItem + visibleItemCount == totalItemCount) {
						refreshPage();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				this.firstVisibleItem=firstVisibleItem;
				this.visibleItemCount=visibleItemCount;
				this.totalItemCount=totalItemCount;
			}
		});
		lvFootView=new TextView(this);
		lvFootView.setHeight(100);
		lvFootView.setText("正在加载剩余条目");
		lv_list.addFooterView(lvFootView);
	}

	private void initDatas() {
		listAdapter = new ListAdapter(ListViewTestActivity.this);
		lv_list.setAdapter(listAdapter);
		refreshPage();
	}

	private void refreshPage() {
		if(index>=100){
			lvFootView.setText("加载完成");
			return;
		}
		int len = 10;
		List<String> member = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			index++;
			member.add("index" + index);
		}
		listAdapter.addMembers(member);
		listAdapter.notifyDataSetChanged();
	}

	private class ListAdapter extends BaseAdapter {

		List<String> list = new ArrayList<>();
		private Context context;

		public ListAdapter(Context context) {
			super();
			this.context = context;
		}

		public void addMembers(List<String> member) {
			list.addAll(member);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(context);
			tv.setText("" + position);
			tv.setBackgroundColor(Color.WHITE);
			tv.setHeight(200);
			return tv;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_listview_up:
			lv_list.smoothScrollBy(100, 0);
			break;
		case R.id.bt_listview_down:
			lv_list.smoothScrollBy(-100, 0);
			break;
		case R.id.bt_listview_addpage:
			refreshPage();
			break;
		case R.id.bt_listview_toTop:
			listAdapter.notifyDataSetChanged();
			lv_list.setSelection(0);
			break;
		}
	}
}
