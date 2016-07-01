package com.basedamo.httpserver;

import android.util.Log;

/**
 * Created by ChenHui on 2016/7/1.
 */
public class MyLog {
    protected String tag;

    public MyLog(String tag) {
        this.tag = tag;
    }

    public void l(int level, String str, boolean sysOnly) {
        synchronized (MyLog.class) {
            str = str.trim();
            str = str + getPosition(7, 8);
            Log.println(level, tag, str);

        }
    }

    public void l(int level, String str) {
        l(level, str, false);
    }

    public void e(String s) {
        l(Log.ERROR, s, false);
    }

    public void d(String s) {
        l(Log.DEBUG, s, false);
    }

    /**
     * 获取调用位置
     *
     * @return
     */
    public static String getPosition() {
        return getPosition(4, 5);
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