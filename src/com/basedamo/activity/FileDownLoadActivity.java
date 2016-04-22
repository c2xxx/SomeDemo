package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.file.FileUtil;
import com.basedamo.utils.LogController;
import com.basedamo.utils.TimeUtil;
import com.lidroid.xutils.exception.HttpException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hui on 2015/12/28.
 */
public class FileDownLoadActivity extends BaseActivity {

    private Map<String, String> urlMap = new HashMap<>();
    private String url = null;
    private TextView tvFileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_filedownload);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("文件下载");
        tvFileInfo = (TextView) findViewById(R.id.tv_filedownload_fileinfo);
        findViewById(R.id.btn_filedownload_download).setOnClickListener(this);
        findViewById(R.id.btn_filedownload_fileinfo).setOnClickListener(this);
        findViewById(R.id.btn_filedownload_change_a).setOnClickListener(this);
        findViewById(R.id.btn_filedownload_change_b).setOnClickListener(this);
        findViewById(R.id.btn_filedownload_change_c).setOnClickListener(this);
        findViewById(R.id.btn_filedownload_delete).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        urlMap.put("小", "http://7xpgb3.com1.z0.glb.clouddn.com/ffff.png");
        urlMap.put("中", "http://7xpgb3.com1.z0.glb.clouddn.com/meinv.png");
        urlMap.put("大", "http://7xpgb3.com1.z0.glb.clouddn.com/IMG_20160407_072119.jpg");
        changeUrl("中");
        tvFileInfo.setText("url:文件B");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (url == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_filedownload_download:
                downLoad();
                break;
            case R.id.btn_filedownload_fileinfo:
                readFileInfo();
                break;
            case R.id.btn_filedownload_change_a:
                changeUrl("小");
                tvFileInfo.setText("url:文件A");
                break;
            case R.id.btn_filedownload_change_b:
                changeUrl("中");
                tvFileInfo.setText("url:文件B");
                break;
            case R.id.btn_filedownload_change_c:
                changeUrl("大");
                tvFileInfo.setText("url:文件C");
                break;
            case R.id.btn_filedownload_delete:
                deleteFile();
                break;
        }
    }

    private void deleteFile() {
        File file = new File(FileUtil.getLocolPath(url));
        Boolean result = file.delete();
        tvFileInfo.setText("文件已经删除" + result);
    }

    private void changeUrl(String key) {
        if (urlMap.containsKey(key)) {
            url = urlMap.get(key);
        }
    }

    private void readFileInfo() {
        File file = new File(FileUtil.getLocolPath(url));
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n文件名：%s", file.getAbsoluteFile()));
        sb.append(String.format("\n文件名：%s", file.getAbsolutePath()));
        sb.append(String.format("\n文件是否存在：%s", file.exists()));
        if (file.exists()) {
            sb.append(String.format("\n创建时间：%s", TimeUtil.timeStampFormat(file.lastModified())));
        }
        tvFileInfo.setText(sb);
    }

    private void downLoad() {

        FileUtil.download(url, new FileUtil.DownLoadResult() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                LogController.d(String.format("下载进度%s/%s", total, current));
            }

            @Override
            public void onSuccess(File file) {
                LogController.d("下载成功：" + file.getAbsolutePath());
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogController.d("下载失败：" + e.getMessage());
            }
        });
        LogController.d("下载文件：" + url);
    }
}
