package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.helper.SharedPerferencesHelper;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2016/2/29.
 */
public class SharedPerferencesActivity extends BaseActivity {

    private EditText et_sharedperferences_input;
    private TextView tv_sharedperferences_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sharedperferences);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_sharedperferences_save).setOnClickListener(this);
        findViewById(R.id.btn_sharedperferences_read).setOnClickListener(this);
        et_sharedperferences_input = (EditText) findViewById(R.id.et_sharedperferences_input);
        tv_sharedperferences_result = (TextView) findViewById(R.id.tv_sharedperferences_result);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sharedperferences_save:
                String value = et_sharedperferences_input.getText().toString();
                SharedPerferencesHelper.save("name", value);
                ToastUtil.show("已保存");
                break;
            case R.id.btn_sharedperferences_read:
                String result = SharedPerferencesHelper.read("name");
                tv_sharedperferences_result.setText("读取结果：" + result);
                break;
        }
    }
}
