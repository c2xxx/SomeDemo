package com.basedamo.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2015/12/1.
 */
public class RecyclerViewActivity3 extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewActivity.HomeAdapter mHomeAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recyclerview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.id_recyclerview);

    }

    @Override
    protected void initData() {
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置item增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<String> mList = new ArrayList<>();
        for (int len = 10, i = 0; i < len; i++) {
            mList.add("item" + i);
        }
        mHomeAdaper = new RecyclerViewActivity.HomeAdapter(this, mList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(
                new RecyclerViewActivity2.DividerItemDecoration(
                        RecyclerViewActivity3.this,
                        RecyclerViewActivity2.DividerItemDecoration.HORIZONTAL_LIST
                )
        );mRecyclerView.addItemDecoration(
                new RecyclerViewActivity2.DividerItemDecoration(
                        RecyclerViewActivity3.this,
                        RecyclerViewActivity2.DividerItemDecoration.VERTICAL_LIST
                )
        );

        mRecyclerView.setAdapter(mHomeAdaper);
    }
}
