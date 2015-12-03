package com.basedamo.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class XUtilsActivity extends BaseActivity {

    protected static final String TAG = "XUtilsActivity";

    private TextView tv_xutils_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xutils);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tv_xutils_result = (TextView) findViewById(R.id.tv_xutils_result);
    }

    @Override
    protected void initData() {

    }

    public void utilGet(View v) {

        tv_xutils_result.setText("utilGet");
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET,
                "http://www.lidroid.com",
                new RequestCallBack<String>() {
                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                        show("onCancelled");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        show("onLoading	" + total + "-" + current + "-" + isUploading);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        show("onStart");
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        show("onFailure");

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        show("onSuccess	" + responseInfo.result);
                        tv_xutils_result.setText(responseInfo.result + "");
                    }
                });
    }

    public void utilPost(View v) {
        tv_xutils_result.setText("utilPost");
        HttpUtils http = new HttpUtils();
        http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST,
                "http://www.lidroid.com",
                new RequestCallBack<String>() {
                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                        show("onCancelled");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        show("onLoading	" + total + "-" + current + "-" + isUploading);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        show("onStart");
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        show("onFailure");

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        show("onSuccess	" + responseInfo.result);
                    }
                });
    }

    private void show(String msg) {
        Log.d(TAG, msg);
        tv_xutils_result.setText(msg);
    }
}
