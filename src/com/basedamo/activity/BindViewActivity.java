package com.basedamo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by ChenHui on 2016/4/12.
 */
public class BindViewActivity extends BaseActivity {
//    @Bind
    private TextView tv_bindview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bindview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
