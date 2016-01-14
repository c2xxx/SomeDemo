package com.basedamo.activity;

import android.os.Bundle;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by hui on 2016/1/11.
 */
public class VoiceTalkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activit_voice_talk);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("语言聊天");
    }

    @Override
    protected void initData() {

    }
}
