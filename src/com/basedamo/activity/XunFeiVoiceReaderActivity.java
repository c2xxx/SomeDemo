package com.basedamo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.chen.utils.XunFeiVoiceReadHelper;

/**
 * Created by hui on 2015/12/1.
 */
public class XunFeiVoiceReaderActivity extends BaseActivity {

    private EditText et_xunfei_reader;//输入框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xunfei_voice_reader);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        et_xunfei_reader = (EditText) findViewById(R.id.et_xunfei_reader);
    }

    @Override
    protected void initData() {
    }

    public void onRead(View v) {
        String text = et_xunfei_reader.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            text = "没有内容，您让我读什么？";
        }
        XunFeiVoiceReadHelper helper = new XunFeiVoiceReadHelper(this);
        helper.readText(text);
    }
}
