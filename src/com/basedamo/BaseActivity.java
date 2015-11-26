package com.basedamo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hui on 2015/11/19.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initViews();
        initData();
    }

    /**
     * @return ����layout
     */
    protected abstract int getLayoutID();

    /**
     * ��ʼ������
     */
    protected abstract void initViews();

    /**
     * ��ʼ������
     */
    protected abstract void initData();

    /**
     * @param enable ���ؼ��Ƿ����
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
     * @param title ����
     */
    protected void setTitleText(CharSequence title) {
        TextView view = (TextView) findViewById(R.id.tv_title_bar_title);
        if (view != null) {
            view.setText(title);
        }
    }

}
