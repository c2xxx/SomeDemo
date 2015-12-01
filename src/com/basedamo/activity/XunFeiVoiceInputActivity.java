package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.chen.utils.XunFeiVoiceInputHelper;

/**
 * Created by hui on 2015/11/27.
 */
public class XunFeiVoiceInputActivity extends BaseActivity {

    private EditText mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xunfei_voice_input);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        mResultText = ((EditText) findViewById(R.id.iat_text));
        findViewById(R.id.btn_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doVoiceInput();
            }
        });
    }

    @Override
    protected void initData() {
    }

    private void doVoiceInput() {
        XunFeiVoiceInputHelper inputer = new XunFeiVoiceInputHelper(this) {
            @Override
            protected void onReceiveText(String result) {
                mResultText.setText(mResultText.getText() + result);
            }
        };
        inputer.start();
    }

}
