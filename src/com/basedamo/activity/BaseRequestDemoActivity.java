package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.net.BaseRequest;
import com.basedamo.net.OnParseHttpResponse;

import java.util.Map;

/**
 * Created by hui on 2016/1/22.
 */
public class BaseRequestDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_baserequest_demo);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("BaseRequest");
        findViewById(R.id.btn_baserequest_demo_test).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_baserequest_demo_test:
                test();
                break;
        }
    }

    private void test() {
        String url = "http://www.lidroid.com";
        Map<String, ? extends Object> params = null;
        new BaseRequest().doAsyncHttpFormPost(url, params, new OnParseHttpResponse() {
            @Override
            public void onParseHttpResponse(String response) {
//                LogController.d("访问结果内容：" + response);
            }
        });
    }
}
