package com.basedamo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2015/12/1.
 */
public class RecyclerViewActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdaper;

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
        mHomeAdaper = new HomeAdapter(this, mList);
        mRecyclerView.setAdapter(mHomeAdaper);
    }

    public static class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        private List<String> mList;
        private Context mContext;

        public HomeAdapter(Context mContext, List<String> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        public void removeData(int position) {
            mList.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_recycler, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_item);
            }
        }
    }
}
