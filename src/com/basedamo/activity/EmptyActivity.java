package com.basedamo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by hui on 2015/11/27.
 */
public class EmptyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.title_bar);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
