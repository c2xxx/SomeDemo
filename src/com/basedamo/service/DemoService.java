package com.basedamo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2016/2/16.
 */
public class DemoService extends Service {

    public final static String ACTION = "com.basedamo.service.DemoService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogController.d("DemoService   onBind");
        return new MyBinder();
    }

    private class MyBinder extends Binder implements IMyBinder {
        @Override
        public void doAction() {
            LogController.d("这里是DemoService。MyBinder。doAction");
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogController.d("DemoService   onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogController.d("DemoService   onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onCreate() {
        LogController.d("DemoService   onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogController.d("DemoService   onDestroy");
        super.onDestroy();
    }
}
