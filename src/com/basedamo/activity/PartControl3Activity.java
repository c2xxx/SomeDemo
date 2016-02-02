package com.basedamo.activity;


import com.basedamo.MainActivity;

/**
 * Created by hui on 2016/2/2.
 */
public class PartControl3Activity extends MainActivity {
    @Override
    protected void initData() {
        addItem("SnackBar、TextInputLayout", SupportLibraryActivity.class);
        addItem("下拉刷新", SwipeRefreshLayoutActivity.class);
    }
}
