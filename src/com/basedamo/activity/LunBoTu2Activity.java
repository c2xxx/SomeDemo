package com.basedamo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.utils.LogController;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图2
 * Created by hui on 2016/2/3.
 */
public class LunBoTu2Activity extends BaseActivity {
    private ViewPager vp_lunbotu2_main;
    private List<View> mlist;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lunbotu2);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        vp_lunbotu2_main = (ViewPager) findViewById(R.id.vp_lunbotu2_main);
    }

    @Override
    protected void initData() {
        mlist = new ArrayList();
        List<Integer> sourceList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sourceList.add(i);
        }
        modifyList(sourceList);

        for (Integer i : sourceList) {
            TextView tv = new TextView(this);
            tv.setText("tv" + (i + 1));
            mlist.add(tv);
        }
        adapter = new MyAdapter(mlist);
        vp_lunbotu2_main.setAdapter(adapter);

        if (mlist.size() > 1) {
            vp_lunbotu2_main.setCurrentItem(1, false); //false:不显示跳转过程的动画
        }
        vp_lunbotu2_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int current = 0;
            int targetIndex = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogController.d("onPageSelected" + position);

                current = position;
                targetIndex = current;
                int len = mlist.size();
                if (len > 1) { //多于1，才会循环跳转
                    if (current == 0) {
                        targetIndex = mlist.size() - 2;
                    }
                    if (current == mlist.size() - 1) {
                        targetIndex = 1;
                    }
                }
//                LogController.d(String.format("curr=%s target=%s", current, targetIndex));
            }

            /**
             * ViewPager#SCROLL_STATE_IDLE
             * ViewPager#SCROLL_STATE_DRAGGING
             * ViewPager#SCROLL_STATE_SETTLING
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_SETTLING) {//状态变化(动作未完成)
                    LogController.d("onPageScrollStateChanged" + state);
                }

                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    LogController.d("onPageScrollStateChanged" + state);
                    if (targetIndex != current) {
                        LogController.d("changet");
                        vp_lunbotu2_main.setCurrentItem(targetIndex, false); //false:不显示跳转过程的动画
                    }
                }
            }
        });
    }

    private void modifyList(List list) {
        if (list == null || list.size() < 2) {//多于1，才会循环跳转
            return;
        }
        Object first = list.get(0);
        Object last = list.get(list.size() - 1);
        list.add(0, last);
        list.add(first);
    }


    //
    private class MyAdapter extends PagerAdapter {
        private List<View> list;


        public MyAdapter(List<View> list) {
            this.list = list;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(list.get(position));//添加页卡
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();//返回页卡的数量
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方提示这样写
        }
    }
}
