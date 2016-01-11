package com.basedamo.activity;

import com.basedamo.MainActivity;
import com.basedamo.view.CircleImageActivity;
import com.basedamo.view.MyViewDemoActivity;

/**
 * Created by hui on 2016/1/8.
 */
public class PartControlActivity extends MainActivity {
    @Override
    protected void initData() {
        addItem("控件1", BaseControlActivity.class);
        addItem("控件2-漂亮Button", NiceButton.class);
        addItem("GridView", GridViewActivity.class);
        addItem("GridView_H", ExpendGridViewActivity.class);
        addItem("WebView", WebViewActivity.class);
        addItem("ListView控制", com.basedamo.listview.ListViewTestActivity.class);
        addItem("自定义控件1", MyViewDemoActivity.class);
        addItem("自定义控件2—圆角图片", CircleImageActivity.class);
        addItem("PopMenu、PopWindow", PopViewActivity.class);
        addItem("ViewStub", ViewStubActivity.class);

    }
}
