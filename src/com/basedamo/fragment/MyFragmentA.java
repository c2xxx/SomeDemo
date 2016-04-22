package com.basedamo.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basedamo.R;

/**
 * Created by ChenHui on 2016/4/19.
 */
public class MyFragmentA extends Fragment {
    private static final String TAG = "BASEDEMO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MyFragmentA onCreateView");
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MyFragmentA onCreate");
    }
}
