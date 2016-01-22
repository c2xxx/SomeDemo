package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2016/1/22.
 */
public class QiniuUploadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qiniu_upload);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        findViewById(R.id.btn_qiniu_upload).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qiniu_upload:
                ToastUtil.show("test");
                break;
        }
    }
}
