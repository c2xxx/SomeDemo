package com.basedamo;

import android.app.Application;
import android.content.Context;

import com.basedamo.file.FileUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by hui on 2015/11/20.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=565807f9");//讯飞语音注册

        FileUtil.init(context);
    }


    public static Context getContext() {
        return context;
    }
}
