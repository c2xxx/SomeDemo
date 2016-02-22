package com.basedamo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2016/2/16.
 */
public class BroadCastDemoActivity extends BaseActivity {
    public static final String ACTION_A = "android.action.ACTION_A";
    public static final String ACTION_B = "android.action.ACTION_B";
    public static final String ACTION_C = "android.action.ACTION_C";

    private BroadcastReceiver receiverA;
    private BroadcastReceiver receiverC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_broadcast);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_broadcasedemo_send_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_broadcasedemo_send_order_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_broadcasedemo_send_inner_broadcast).setOnClickListener(this);

    }

    @Override
    protected void initData() {
        receiverA = new MyReceiver();
        receiverC = new MyReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver_common();
        registerReceiver_local();
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegisterReceiver_common();
        unRegisterReceiver_local();
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver_common() {
        IntentFilter filtera = new IntentFilter();
        filtera.addAction(ACTION_A);
        registerReceiver(receiverA, filtera);
    }

    /**
     * 注册广播接收器(应用内广播)
     */
    private void registerReceiver_local() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_C);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiverC, intentFilter);
    }

    /**
     * 取消广播接收器
     */
    private void unRegisterReceiver_common() {
        unregisterReceiver(receiverA);
    }

    /**
     * 取消广播接收器(应用内广播)
     */
    private void unRegisterReceiver_local() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverC);
    }

    @Override
    public void onClick(View v) {
        Intent data;
        switch (v.getId()) {
            case R.id.btn_broadcasedemo_send_broadcast:
                data = new Intent(ACTION_A);
                data.putExtra("number", "33");
                this.sendBroadcast(data);
                break;
            case R.id.btn_broadcasedemo_send_order_broadcast:
                data = new Intent(ACTION_B);
                data.putExtra("number", "36");
                this.sendOrderedBroadcast(data, null);
                break;
            case R.id.btn_broadcasedemo_send_inner_broadcast:
                data = new Intent(ACTION_C);
                data.putExtra("number", "39");
                this.sendOrderedBroadcast(data, null);
                LocalBroadcastManager.getInstance(this).sendBroadcast(data);
                break;
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            ToastUtil.show("" + intent.getAction() + "\n" +
                    "number=" + intent.getStringExtra("number"));
        }
    }

    public static class MyReceiverB extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            ToastUtil.show("" + intent.getAction() + "\n" +
                    "number=" + intent.getStringExtra("number"));
            abortBroadcast();//终止广播，如果是有序广播，不再触发优先级更低的广播接收器
        }
    }
}
