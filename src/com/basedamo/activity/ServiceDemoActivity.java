package com.basedamo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.MyApplication;
import com.basedamo.R;
import com.basedamo.service.DemoService;

/**
 * Created by hui on 2016/2/16.
 */
public class ServiceDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_servicedemo);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_service_start).setOnClickListener(this);
        findViewById(R.id.btn_service_start2).setOnClickListener(this);
        findViewById(R.id.btn_service_stop).setOnClickListener(this);
        findViewById(R.id.btn_service_stop2).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service_start:
                doStart();
                break;
            case R.id.btn_service_start2:
                doStart2();
                break;
            case R.id.btn_service_stop:
                doStop();
                break;
            case R.id.btn_service_stop2:
                doStop2();
                break;
        }
    }

    protected void doStart() {
        Intent intent = new Intent(this, DemoService.class);
        startService(intent);
    }


    protected void doStop() {
        Intent intent = new Intent(this, DemoService.class);
        stopService(intent);
    }

    protected void doStart2() {
        Intent intent = new Intent();
        intent.setAction(DemoService.ACTION);
        intent.setPackage(getPackageName());//这里你需要设置你应用的包名（Android5.0以上系统需要）
        startService(intent);
    }

    protected void doStop2() {
        Intent intent = new Intent();
        intent.setAction(DemoService.ACTION);
        intent.setPackage(getPackageName());//这里你需要设置你应用的包名（Android5.0以上系统需要）
        stopService(intent);
    }
}
