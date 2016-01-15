package com.basedamo.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2016/1/14.
 */
public class SupportLibraryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_support_library);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_support_library_snackbar).setOnClickListener(this);
        findViewById(R.id.btn_support_library_login).setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_support_library_snackbar:
                snackBarDemo();
                break;
            case R.id.btn_support_library_login:
                doLoginCheck();
                break;
        }
    }


    public void doLoginCheck() {
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        passwordWrapper.setError("Not a valid password!");
        usernameWrapper.setErrorEnabled(false);
    }

    private void snackBarDemo() {
        Snackbar.make(findViewById(R.id.btn_support_library_snackbar), "显示内容", Snackbar.LENGTH_LONG)
                .setAction("按钮", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show("Toast Action");
                    }
                }).show();
    }
}
