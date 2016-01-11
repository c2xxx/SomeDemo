package com.basedamo.utils;

import android.util.Log;

import com.basedamo.BuildConfig;

/**
 * Created by hui on 2015/11/20.
 */
public class LogController {
    private static final String TAG = "BASEDEMO";
    private static boolean isDebug=BuildConfig.DEBUG;
    public static void d(String msg){
        Log.d(TAG,msg);
    }


    /**
     * 打印完整的信息
     */
    public static void printWholeMsg(String msg) {
        if (isDebug) {
            if (msg == null) {
                return;
            }
            int len = msg.length();
            int maxLogSize = 300;//每次打印字符
            double count = Math.ceil(len / maxLogSize);//打印次数

            Log.d(TAG, "================START==================");
            for (int i = 0; i <= count; i++) {

                int start = i * maxLogSize;

                int end = (i + 1) * maxLogSize;

                end = end > len ? len : end;

                Log.d(TAG, String.format("Part%s    %s", i + 1, msg.substring(start, end)));

            }
            Log.d(TAG, "================END==================");
        }
    }
}
