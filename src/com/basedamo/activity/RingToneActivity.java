package com.basedamo.activity;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by hui on 2015/12/24.
 */
public class RingToneActivity extends BaseActivity {

    /**
     * 通知声音类型
     */
    private int ringtonType;
    /**
     * 通知声音Uri
     */
    private Uri ringtoneUri;
    private MediaPlayer ringtonePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ringtone);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("播放铃音");
        findViewById(R.id.btn_ringtone_start).setOnClickListener(this);
        findViewById(R.id.btn_ringtone_stop).setOnClickListener(this);
    }

    @Override
    protected void initData() {

        ringtonType = RingtoneManager.TYPE_RINGTONE;
        ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, ringtonType);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_ringtone_start:
                playRingtone();
                break;
            case R.id.btn_ringtone_stop:
                stopRingtone();
                break;
        }
    }

    private void stop() {

    }

    /**
     * 播放通知声音
     */
    private void playRingtone() {
        if (null == ringtonePlayer) {
            ringtonePlayer = MediaPlayer.create(this, ringtoneUri);
            ringtonePlayer.setLooping(true);
        }
        if (!ringtonePlayer.isPlaying()) {
            ringtonePlayer.start();
        }
    }

    /**
     * 停止播放通知声音
     */
    private void stopRingtone() {
        if (null != ringtonePlayer) {
            ringtonePlayer.stop();
            ringtonePlayer.release();
            ringtonePlayer = null;
        }
    }
}
