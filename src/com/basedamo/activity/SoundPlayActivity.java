package com.basedamo.activity;

import android.app.Activity;

import com.basedamo.utils.SoundPlay;

/**
 * Created by ChenHui on 2016/7/15.
 */
public class SoundPlayActivity extends OneButtonActivity {
    @Override
    protected void initData() {
        super.initData();
        SoundPlay.init(this);
    }

    @Override
    protected void executeTest() {
        SoundPlay.play();
    }
}
