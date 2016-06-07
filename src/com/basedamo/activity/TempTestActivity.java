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
 * Created by ChenHui on 2016/4/25.
 */
public class TempTestActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_temptest);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_header_titiles);

    }

    @Override
    protected void initData() {
        //设置布局管理器
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measuredHeight = view.getMeasuredHeight();
                    setMeasuredDimension(measuredWidth, measuredHeight);
                }
            }
        };
        mRecyclerView.setLayoutManager(lm);
        // 设置item增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<String> mList = new ArrayList<>();
        for (int len = 15, i = 0; i < len; i++) {
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
            ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
            if(position==5){
                lp.width =400;
                lp.height =400;
            }
            holder.tv.setLayoutParams(lp);
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
