package com.basedamo.utils;

import android.content.Context;
import android.widget.Toast;

import com.basedamo.MyApplication;

/**
 * Created by hui on 2015/11/20.
 */
public class ToastUtil {
    private static Toast toast=Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_LONG);
    public static void show(String msg){
        toast.setText(msg);
        toast.show();
    }
}
