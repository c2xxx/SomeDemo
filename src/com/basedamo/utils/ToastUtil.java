package com.basedamo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hui on 2015/11/20.
 */
public class ToastUtil {
    public static void show(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
