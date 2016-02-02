package com.basedamo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Reply;

import java.util.List;

/**
 * Created by hui on 2016/2/2.
 */
public class SwipeRefreshLayoutActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_swipe_refresh_layout);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        new MyAsynTask().execute();
    }

    @Override
    protected void initData() {

    }

    private class MyAsynTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ToastUtil.show("开始刷新");
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ToastUtil.show("刷新结束");
            // SwipeRefreshLayout停止刷新
            refreshLayout.setRefreshing(false);
        }
    }
}
