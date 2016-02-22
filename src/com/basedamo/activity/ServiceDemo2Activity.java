package com.basedamo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.service.IMyBinder;
import com.basedamo.utils.LogController;

/**
 * Created by hui on 2016/2/16.
 */
public class ServiceDemo2Activity extends BaseActivity {

    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_servicedemo2);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_service_bind).setOnClickListener(this);
        findViewById(R.id.btn_service_unbind).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        conn = new MyConn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service_bind:
                bind();
                break;
            case R.id.btn_service_unbind:
                unbind();
                break;
        }
    }

    protected void bind() {
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        bindService(intent, conn, BIND_AUTO_CREATE);
    }


    protected void unbind() {
        unbindService(conn);
    }

    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogController.d("onServiceConnected");
            if(service instanceof IMyBinder){
                IMyBinder myBinder= (IMyBinder) service;
                myBinder.doAction();//调用Service里面的方法
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogController.d("onServiceDisconnected");
        }
    }
}
