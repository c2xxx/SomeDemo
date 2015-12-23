package com.basedamo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2015/12/23.
 */
public class AsyncTaskActivity extends BaseActivity {
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_asynctack);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("AsyncTask");
        tvResult = (TextView) findViewById(R.id.tv_asnyntask_result);
    }

    @Override
    protected void initData() {

    }

    public void click(View v) {
        tvResult.setText(tvResult.getText() + "\r\n" + "\n");
        MyAsynTask myAsynTask = new MyAsynTask();
        myAsynTask.execute("启动参数");
    }

    private class MyAsynTask extends AsyncTask<String, String, String> {

        public MyAsynTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvResult.setText(tvResult.getText() + "\r\n" + "开始执行");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvResult.setText(tvResult.getText() + "\r\n" + "执行结束:" + s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values.length > 0) {
                LogController.d("执行进度:  " + values[0]);
            }
        }

        @Override
        protected String doInBackground(String[] params) {
            if (params.length < 0) {
                tvResult.setText(tvResult.getText() + "\r\n" + params[0]);
            }
            for (int i = 0, len = 3; i < len; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onProgressUpdate(String.format("执行中(%s/%s)", i + 1, len));
            }
            return "执行结果123";
        }
    }
}
