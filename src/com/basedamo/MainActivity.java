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

import com.basedamo.activity.AsyncTaskActivity;
import com.basedamo.activity.AudioRecoderActivity;
import com.basedamo.activity.BaseRequestDemoActivity;
import com.basedamo.activity.CutImageActivity;
import com.basedamo.activity.DesktopLogoNumberActivity;
import com.basedamo.activity.EmptyActivity;
import com.basedamo.activity.FileDownLoadActivity;
import com.basedamo.activity.GifActivity;
import com.basedamo.activity.HardWareActivity;
import com.basedamo.activity.HardWareActivity2;
import com.basedamo.activity.PartControl2Activity;
import com.basedamo.activity.PartControlActivity;
import com.basedamo.activity.QiniuUploadActivity;
import com.basedamo.activity.RingToneActivity;
import com.basedamo.activity.SecurityDemoActivity;
import com.basedamo.activity.SupportLibraryActivity;
import com.basedamo.activity.UmengFeedBackActivity;
import com.basedamo.activity.VoiceTalkActivity;
import com.basedamo.activity.VolumActivity;
import com.basedamo.activity.XUtilsActivity;
import com.basedamo.activity.XunFeiVoiceInputActivity;
import com.basedamo.activity.XunFeiVoiceReaderActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    private ListView ll_list;
    private List<String> listStrs = new ArrayList<>();
    private List<Intent> listIntents = new ArrayList<>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        addItem("控件1_基本控件", PartControlActivity.class);
        addItem("控件2_进阶", PartControl2Activity.class);
        addItem("声音和振动", VolumActivity.class);
        addItem("电池、wifi、CPU信息", HardWareActivity.class);
        addItem("麦克风和距离传感器", HardWareActivity2.class);
        addItem("Volley使用", com.basedamo.volley.VolleyDemoActivity.class);
        addItem("XUtils使用", XUtilsActivity.class);
        addItem("桌面图标右上角数字", DesktopLogoNumberActivity.class);
        addItem("友盟反馈", UmengFeedBackActivity.class);
//        addItem("分享功能（未完成）", EmptyActivity.class);
        addItem("科大讯飞——语音输入", XunFeiVoiceInputActivity.class);
        addItem("科大讯飞——语音合成", XunFeiVoiceReaderActivity.class);
        addItem("裁剪图片(系统功能)", CutImageActivity.class);
        addItem("AsyncTask", AsyncTaskActivity.class);
        addItem("铃音播放", RingToneActivity.class);
        addItem("录音 AudioRecoder", AudioRecoderActivity.class);
        addItem("录音 MediaRecoder 仿微信", VoiceTalkActivity.class);
        addItem("文件下载", FileDownLoadActivity.class);
        addItem("网络访问 BaseRequest", BaseRequestDemoActivity.class);
        addItem("七牛上传图片", QiniuUploadActivity.class);
        addItem("安全机制（加密解密）（未完成RSA,DES）", SecurityDemoActivity.class);
        addItem("Gif图片播放（未完成）", GifActivity.class);
        addItem("视频播放（未完成）", EmptyActivity.class);
        addItem("JNI编程（未完成）", EmptyActivity.class);
        addItem("图片选择（未完成）", EmptyActivity.class);
        addItem("ImageCache（未完成）", EmptyActivity.class);
        addItem("Support Library（未完成）", SupportLibraryActivity.class);

    }

    @Override
    protected void initViews() {
        ll_list = (ListView) findViewById(R.id.ll_list);
        adapter = new MyListAdapter();
        ll_list.setAdapter(adapter);
        ll_list.setOnItemClickListener(this);
    }

    protected void addItem(String text, Class<? extends Activity> activity) {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = listIntents.get(position);
        String title = listStrs.get(position);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
