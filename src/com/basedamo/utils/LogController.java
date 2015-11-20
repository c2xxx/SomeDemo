package com.basedamo.utils;

import android.util.Log;

import com.basedamo.BuildConfig;

/**
 * Created by hui on 2015/11/20.
 */
public class LogController {
    private static boolean isDebug=BuildConfig.DEBUG;
    public static void d(String msg){
        Log.d("CHEN",msg);
    }
}
