package com.basedamo.activity;

import android.os.Bundle;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by hui on 2016/1/25.
 */
public class GifActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gif);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("GIF动画");
    }

    @Override
    protected void initData() {

    }
}
