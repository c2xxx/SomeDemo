package com.basedamo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.MainActivity;
import com.basedamo.R;

/**
 * 创建桌面快捷方式
 * Created by ChenHui on 2016/7/4.
 */
public class DesktopShortcutActivity extends BaseActivity {
    private static final String SHORT_CUT_NAME = "快捷方式chen";
    public static final String PARAMS = "startParm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_desktopd_shortcut);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_shortcut_create1).setOnClickListener(this);
        findViewById(R.id.btn_shortcut_clear1).setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_shortcut_create1:
                //微信创建桌面快捷方式都是先删除后创建的，可能是可以更新附带参数之类的，可以效仿一下
                removeShortCut1();
                setUpShortCut1();
                break;
            case R.id.btn_shortcut_clear1:
                removeShortCut1();
                break;
        }
    }


    private void removeShortCut1() {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, SHORT_CUT_NAME);
        /**改成以下方式能够成功删除，估计是删除和创建需要对应才能找到快捷方式并成功删除**/
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getShortcutIntent());
        sendBroadcast(shortcut);

    }

    /**
     * 创建快捷方式
     */
    private void setUpShortCut1() {

        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 设置快捷方式图片
        Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.fb_msg_tip);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        // 设置快捷方式名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, SHORT_CUT_NAME);

        // 设置是否允许重复创建快捷方式 false表示不允许
        intent.putExtra("duplicate", true);
        Intent targetIntent = getShortcutIntent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);

        // 发送广播
        sendBroadcast(intent);
    }

    @NonNull
    private Intent getShortcutIntent() {
        // 设置快捷方式要打开的intent
        Intent targetIntent = new Intent();
        targetIntent.setAction(Intent.ACTION_MAIN);
        targetIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        targetIntent.setClass(this, MainActivity.class);
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//清除现有activity
        targetIntent.putExtra(PARAMS, "通过快捷方式启动的");
        return targetIntent;
    }

    /**
     * 判断是否存在快捷方式
     * 需要权限：<uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
     */
    public boolean hasShortcut(Activity cx, String shortcutName) {
//        还不知道怎么做
//        查询快捷方式是否存在（三方rom大部分查询失败，cursor为null）
        return false;
    }


}