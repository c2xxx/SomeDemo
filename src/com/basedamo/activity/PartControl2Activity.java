package com.basedamo.activity;

import com.basedamo.MainActivity;

/**
 * Created by hui on 2016/1/8.
 */
public class PartControl2Activity extends MainActivity {
    @Override
    protected void initData() {
        addItem("CardView", CardViewActivity.class);
        addItem("RecyclerView(一)", RecyclerViewActivity.class);
        addItem("RecyclerView(二)", RecyclerViewActivity2.class);
        addItem("RecyclerView(三)", RecyclerViewActivity3.class);
        addItem("RecyclerView(四)", RecyclerViewActivity4.class);
        addItem("属性动画（上）", ProteryAnimation1.class);
        addItem("属性动画（中）", ProteryAnimation2.class);
        addItem("属性动画（下）", ProteryAnimation3.class);
        addItem("图片放大", ShowImageViewActivity.class);
        addItem("图片模糊圆角黑白", BitMapActivity.class);
        addItem("侧滑", SlidingActivity.class);
        addItem("轮播图", LunBoTuActivity.class);
        addItem("轮播图(循环)", LunBoTu2Activity.class);
    }
}
