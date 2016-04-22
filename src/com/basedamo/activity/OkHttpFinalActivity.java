package com.basedamo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by ChenHui on 2016/4/13.
 */
public class OkHttpFinalActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_okhttpfinal);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_okhttpfinal_get).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_okhttpfinal_get:
                doGet();
                break;
        }
    }

    private void doGet() {

    }
}
