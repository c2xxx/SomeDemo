package com.basedamo.utils;

import android.content.Context;
import android.widget.Toast;

import com.basedamo.MyApplication;

/**
 * Created by hui on 2015/11/20.
 */
public class ToastUtil {
    public static void show(String msg){
        Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
