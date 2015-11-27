package com.basedamo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.basedamo.activity.BaseControlActivity;
import com.basedamo.activity.BitMapActivity;
import com.basedamo.activity.DesktopLogoNumberActivity;
import com.basedamo.activity.EmptyActivity;
import com.basedamo.activity.ExpendGridViewActivity;
import com.basedamo.activity.GridViewActivity;
import com.basedamo.activity.HardWareActivity;
import com.basedamo.activity.HardWareActivity2;
import com.basedamo.activity.NiceButton;
import com.basedamo.activity.ProteryAnimation1;
import com.basedamo.activity.ProteryAnimation2;
import com.basedamo.activity.ProteryAnimation3;
import com.basedamo.activity.ShowImageViewActivity;
import com.basedamo.activity.UmengFeedBackActivity;
import com.basedamo.activity.VolumActivity;
import com.basedamo.activity.WebViewActivity;
import com.basedamo.activity.XUtilsActivity;
import com.basedamo.view.CircleImageActivity;
import com.basedamo.view.MyViewDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    private ListView ll_list;
    private List<String> listStrs = new ArrayList<String>();
    private List<Intent> listIntents = new ArrayList<Intent>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        addItem("控件1", BaseControlActivity.class);
        addItem("控件2-漂亮Button", NiceButton.class);
        addItem("图片模糊圆角黑白", BitMapActivity.class);
        addItem("GridView", GridViewActivity.class);
        addItem("GridView_H", ExpendGridViewActivity.class);
        addItem("WebView", WebViewActivity.class);
        addItem("声音和振动", VolumActivity.class);
        addItem("电池、wifi、CPU信息", HardWareActivity.class);
        addItem("麦克风和距离传感器", HardWareActivity2.class);
        addItem("属性动画（上）", ProteryAnimation1.class);
        addItem("属性动画（中）", ProteryAnimation2.class);
        addItem("属性动画（下）", ProteryAnimation3.class);
        addItem("图片放大", ShowImageViewActivity.class);
        addItem("Volley使用", com.basedamo.volley.VolleyDemoActivity.class);
        addItem("轮播图", com.basedamo.slideviewpaper.ViewPaperActivity.class);
        addItem("ListView控制", com.basedamo.listview.ListViewTestActivity.class);
        addItem("XUtils使用", XUtilsActivity.class);
        addItem("自定义控件1", MyViewDemoActivity.class);
        addItem("自定义控件2—圆角图片", CircleImageActivity.class);
        addItem("桌面图标右上角数字", DesktopLogoNumberActivity.class);
        addItem("友盟反馈", UmengFeedBackActivity.class);
        addItem("分享功能（未完成）", EmptyActivity.class);
        addItem("语音输入（未完成）", EmptyActivity.class);
    }

    @Override
    protected void initViews() {
        ll_list = (ListView) findViewById(R.id.ll_list);
        adapter = new MyListAdapter();
        ll_list.setAdapter(adapter);
        ll_list.setOnItemClickListener(this);
    }

    private void addItem(String text, Class<? extends Activity> activity) {
        listStrs.add(text);
        Intent intent = new Intent(this, activity);
        intent.putExtra("title", text);
        listIntents.add(intent);
    }

    private class MyListAdapter extends BaseAdapter {

        private float density;

        public MyListAdapter() {
            DisplayMetrics outMetrics = new DisplayMetrics();
            MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            density = outMetrics.density;
        }

        @Override
        public int getCount() {
            return listStrs.size();
        }

        @Override
        public Object getItem(int position) {
            return listStrs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(MainActivity.this);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(params);
            int panding = (int) (density * 10);
            tv.setPadding(panding, panding, panding, panding);
            tv.setText((position + 1) + "-" + listStrs.get(position));
            tv.setTextSize(21);
            return tv;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = listIntents.get(position);
        startActivity(intent);
    }
}
