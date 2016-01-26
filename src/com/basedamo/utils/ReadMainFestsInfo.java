package com.basedamo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.basedamo.MyApplication;

/**
 * Created by hui on 2016/1/26.
 */
public class ReadMainFestsInfo {

    public static final int VERSION_NAME = 1;
    public static final int VERSION_CODE = 2;


    /**
     * 获取配置信息
     */
    public static String readVersion(Context context, int type) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        switch (type) {
            case VERSION_NAME:
                return pi.versionName;
            case VERSION_CODE:
                return pi.versionCode + "";
        }
        return null;
    }


    /**
     * 读取application 节点  meta-data 信息
     * @param context
     * @param keyName   键
     * @return
     */
    public static String readMetaData(Context context, String keyName) {
        String mTag = "";
        try {
            ApplicationInfo appInfo = MyApplication.getContext().getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            mTag = appInfo.metaData.getString(keyName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mTag;
    }
}
