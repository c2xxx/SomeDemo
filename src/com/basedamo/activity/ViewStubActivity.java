package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2015/12/24.
 */
public class ViewStubActivity extends BaseActivity {
    private ViewStub vs_viewstub;
    private boolean isInflated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_viewstub);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        vs_viewstub = (ViewStub) findViewById(R.id.vs_viewstub);

    }

    @Override
    protected void initData() {
    }

    public void load(View v) {
        if (!isInflated) {
            isInflated = true;
            vs_viewstub.inflate();
            TextView textView = (TextView) findViewById(R.id.viewstub_demo_textview);
            textView.setText("这是后加载的内容");
        }else{
//            vs_viewstub.setVisibility(View.GONE);
            ToastUtil.show("已经加载过！");
        }
    }

}
