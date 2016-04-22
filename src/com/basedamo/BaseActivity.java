package com.basedamo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by hui on 2015/11/19.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftbutton(true);
        setDefaultTitle();
        initViews();
        initData();
    }

    private void setDefaultTitle() {
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            setTitleText(title);
        }
    }


    /**
     * 初始化界面
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * @param enable 返回键是否可用
     */
    protected void setLeftbutton(boolean enable) {
        View view = findViewById(R.id.iv_title_bar_left);
        if (view != null) {
            if (enable) {
                view.setVisibility(View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                view.setVisibility(View.GONE);
                view.setOnClickListener(null);
            }
        }
    }

    /**
     * @param title 标题
     */
    protected void setTitleText(CharSequence title) {
        TextView view = (TextView) findViewById(R.id.tv_title_bar_title);
        if (view != null) {
            view.setText(title);
        }
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {

    }
}
