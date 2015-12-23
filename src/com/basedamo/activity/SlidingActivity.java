package com.basedamo.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

/**
 * Created by hui on 2015/12/23.
 */
public class SlidingActivity extends BaseActivity {
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sliding);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("侧滑");
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.btn_sliding_hidden).setOnClickListener(this);
        findViewById(R.id.btn_sliding_display).setOnClickListener(this);
        findViewById(R.id.btn_sliding_setdisable).setOnClickListener(this);
        findViewById(R.id.btn_sliding_setenable).setOnClickListener(this);
        mDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                ToastUtil.show("开始变化");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                ToastUtil.show("侧边栏打开");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                ToastUtil.show("侧边栏关闭");
            }

            /**
             * 打开关闭都会触发
             *  @param newState
             */
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sliding_display:
                toggleDrawer();
                break;
            case R.id.btn_sliding_hidden:
                toggleDrawer();
                break;
            case R.id.btn_sliding_setdisable:
                //设置不可划定，并保持打开状态
//                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                //设置不可划定，并保持关闭状态
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case R.id.btn_sliding_setenable:
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
        }
    }

    /**
     * 开关抽屉
     */
    private void toggleDrawer() {
        if (null == mDrawer) {
            return;
        }
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }
}
