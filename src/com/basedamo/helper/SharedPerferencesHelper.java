package com.basedamo.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.basedamo.MyApplication;

/**
 * Created by hui on 2016/2/29.
 */
public class SharedPerferencesHelper {
    private static SharedPreferences sp;

    public static void save(String key, String value) {
        sp = getSharedPreferences();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String read(String key) {
        sp = getSharedPreferences();
        return sp.getString(key, "");
    }

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences("basedemo", Context.MODE_PRIVATE);
    }
}
