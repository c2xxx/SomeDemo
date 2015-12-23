package com.basedamo.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.basedamo.R;
import com.basedamo.utils.ToastUtil;

import java.lang.reflect.Field;

/**
 * Created by hui on 2015/12/22.
 */
public class PopViewActivity extends ActionBarActivity implements View.OnClickListener {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popview);
        initViews();
    }

    protected void initViews() {
        findViewById(R.id.btn_popview_popmenu).setOnClickListener(this);
        findViewById(R.id.btn_popview_popmenu_v7).setOnClickListener(this);
        findViewById(R.id.btn_popview_popwindow).setOnClickListener(this);
        findViewById(R.id.iv_title_bar_left).setOnClickListener(this);
        TextView tv= (TextView) findViewById(R.id.tv_title_bar_title);
        tv.setText("PopWindow,PopMenu");
    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_left:
                finish();
                break;
            case R.id.btn_popview_popmenu:
                showPopMenu(v);
                break;
            case R.id.btn_popview_popmenu_v7:
                showPopMenu_v7(v);
                break;
            case R.id.btn_popview_popwindow:
                showPopwindow(v);
                break;
        }
    }


    /**
     * 显示菜单
     *
     * @param v
     */
    private void showPopwindow(View v) {

        // 获取popWin要显示的布局
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_popwindow, null);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        View menu_news =contentView.findViewById(R.id.menu_news);
        menu_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("打电话");
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        if (null != popupWindow) {
            final View view = popupWindow.getContentView();
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int anchorMeasuredWidth = v.getMeasuredWidth();
            int menuMeasuredWidth = view.getMeasuredWidth();
            int anchorPaddingRight = v.getPaddingRight();
            popupWindow.showAsDropDown(v, anchorMeasuredWidth - menuMeasuredWidth - anchorPaddingRight, 0);
//            popupWindow.showAsDropDown(v, 0, 0);
        }
    }

    /**
     * 传统PopMenu
     *
     * @param v
     */
    private void showPopMenu(View v) {
        android.widget.PopupMenu menu = new android.widget.PopupMenu(this, v);
        getMenuInflater().inflate(R.menu.main, menu.getMenu());
        menu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        ToastUtil.show("选择A");
                        break;
                    case R.id.action_settings2:
                        ToastUtil.show("选择B");
                        break;
                    case R.id.action_settings3:
                        ToastUtil.show("选择C");
                        break;
                    case R.id.action_settings4:
                        ToastUtil.show("选择D");
                        break;

                }
                return false;
            }
        });
        menu.show();
    }

    private void showPopMenu_v7(View v) {
        PopupMenu menu = new PopupMenu(this, v);
        getMenuInflater().inflate(R.menu.main, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        ToastUtil.show("选择A");
                        break;
                    case R.id.action_settings2:
                        ToastUtil.show("选择B");
                        break;
                    case R.id.action_settings3:
                        ToastUtil.show("选择C");
                        break;
                    case R.id.action_settings4:
                        ToastUtil.show("选择D");
                        break;

                }
                return false;
            }
        });
        //使用反射，强制显示菜单图标
        try {
            Field field = menu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(menu);
            mHelper.setForceShowIcon(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu.show();
    }
}
