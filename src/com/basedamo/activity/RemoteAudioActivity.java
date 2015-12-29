package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.file.FileUtil;
import com.basedamo.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;

import java.io.File;

/**
 * Created by hui on 2015/12/29.
 */
public class RemoteAudioActivity extends BaseActivity {

    String pcmUrl = "http://7xpgb3.com1.z0.glb.clouddn.com/recording-143268130.pcm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_remoteaudio);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_remoteaudio_play_after_load).setOnClickListener(this);
        findViewById(R.id.btn_remoteaudio_play_when_load).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_remoteaudio_play_after_load:
                playAfterLoad();
                break;
            case R.id.btn_remoteaudio_play_when_load:
                playWhenLoad();
                break;
        }
    }

    private void playAfterLoad() {
        FileUtil.download(pcmUrl, new FileUtil.DownLoadResult() {
            @Override
            public void onSuccess(File file) {
                ToastUtil.show("下载完成");
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void playWhenLoad() {
    }


}
