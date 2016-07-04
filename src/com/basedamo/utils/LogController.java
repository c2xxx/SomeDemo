package com.basedamo.utils;

import android.util.Log;

import com.basedamo.BuildConfig;

/**
 * Created by hui on 2015/11/20.
 */
public class LogController {
    private static final String TAG = "BASEDEMO";
    private static boolean isDebug = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (!isDebug) {
            return;
        }
        msg = msg + getPosition();
        Log.d(TAG, msg);
    }


    public static void e(String msg) {
        if (!isDebug) {
            return;
        }
        msg = msg + getPosition();
        Log.e(TAG, msg);
    }

    /**
     * 打印异常信息
     *
     * @param e
     */
    public static void printExceptionInfo(Throwable e) {
        if (!isDebug) {
            return;
        }
        String msg = Log.getStackTraceString(e);
        msg = msg + getPosition();
        Log.e(TAG, msg);
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


    /**
     * 获取调用位置
     *
     * @return
     */
    public static String getPosition() {
        return getPosition(5, 6);
    }

    /**
     * 获取调用位置
     *
     * @return
     */
    public static String getPosition(int start, int end) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int total = elements.length;
        StringBuilder buffer = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (total <= i || i < 0) {
                break;
            }
            StackTraceElement stackTraceElement = elements[i];
            buffer.append("(");
            buffer.append(stackTraceElement.getFileName());
            buffer.append(":");
            buffer.append(stackTraceElement.getLineNumber());
            buffer.append(")");
        }
        return buffer.toString();
    }
}
