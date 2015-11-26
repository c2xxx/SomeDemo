package com.basedamo;

import android.app.Application;
import android.content.Context;

/**
 * Created by hui on 2015/11/20.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
