package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 *
 * Created by ChenHui on 2016/7/4.
 */
public abstract class OneButtonActivity extends BaseActivity {

    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_onebutton);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        testButton = (Button) findViewById(R.id.btn_onebutton_test);
        testButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_onebutton_test:
                executeTest();
                break;
        }
    }

    /**
     * 执行测试
     */
    protected abstract void executeTest();
}
