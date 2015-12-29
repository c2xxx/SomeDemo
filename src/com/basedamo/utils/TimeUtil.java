package com.basedamo.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hui on 2015/12/28.
 */
public class TimeUtil {
    public static String timeStampFormat(String timeStamp) {
        return timeStampFormat(timeStamp, null);
    }
    public static String timeStampFormat(long timeStamp) {
        return timeStampFormat(String.valueOf(timeStamp), null);
    }

    public static String timeStampFormat(String timeStamp, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(Long.valueOf(timeStamp)));
    }
}
