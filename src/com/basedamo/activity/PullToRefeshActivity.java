package com.basedamo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/2/23.
 */
public class PullToRefeshActivity extends BaseActivity {


    private List<String> mList;

    private PullToRefreshListView lv_pulltorefresh;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pulltorefresh);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {

        lv_pulltorefresh = (PullToRefreshListView) findViewById(R.id.lv_pulltorefresh);
        lv_pulltorefresh.setMode(PullToRefreshBase.Mode.BOTH);
        lv_pulltorefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                doRefresh();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        new GetDataTask().execute();
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add("item==" + i);
        }
        adapter = new ItemAdapter();
        lv_pulltorefresh.setAdapter(adapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            int size = mList.size();
            int len = size + 5;
            for (int i = size; i < len; i++) {
                mList.add("item==" + i + " ");
            }
            adapter.notifyDataSetChanged();
            lv_pulltorefresh.onRefreshComplete();
        }

    }

    private class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                convertView = new TextView(PullToRefeshActivity.this);
                convertView.setPadding(10, 10, 10, 10);
            }
            tv = (TextView) convertView;
            tv.setText(mList.get(position));
            return convertView;
        }
    }

}
