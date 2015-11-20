package com.basedamo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.helper.LauncherBadgeHelper;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2015/11/19.
 */
public class DesktopLogoNumberActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_desktop_logo_number;
    }

    @Override
    protected void initViews() {
        setTitleText("修改桌面图标右上角提示符");
        setLeftbutton(true);
        findViewById(R.id.btn_desktop_logo_number_shownum).setOnClickListener(this);
        findViewById(R.id.btn_desktop_logo_number_clearnum).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.et_desktop_logo_number_input);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_desktop_logo_number_shownum:
                String numText = editText.getText().toString().trim();
                int num = Integer.parseInt(numText);
                LauncherBadgeHelper.setBadgeCount(this, num);
                ToastUtil.show(this,"请查看桌面图标右上角是否有数字");
                break;
            case R.id.btn_desktop_logo_number_clearnum:
                LauncherBadgeHelper.resetBadgeCount(this);
                ToastUtil.show(this, "请查看桌面图标是否有变化");
                break;
        }
    }
}
