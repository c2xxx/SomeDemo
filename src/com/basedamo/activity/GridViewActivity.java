package com.basedamo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends BaseActivity {
    private GridView gv_expand;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gridview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("" + i);
        }
        adapter = new MyAdapter(list);
        gv_expand.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        gv_expand = (GridView) findViewById(R.id.gv_expand);
    }

    private class MyAdapter extends BaseAdapter {
        List<String> list;

        public MyAdapter(List<String> list) {
            super();
            this.list = list;
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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(GridViewActivity.this,
                        R.layout.item_gridview, null);
                holder.tv_item = (TextView) convertView
                        .findViewById(R.id.tv_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_item.setText(String.valueOf(position));
            Log.d("", "" + holder.tv_item.getHeight());
            return convertView;
        }

        class ViewHolder {
            TextView tv_item;
        }

    }
}
