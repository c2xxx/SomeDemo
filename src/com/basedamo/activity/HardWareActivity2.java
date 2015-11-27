package com.basedamo.activity;

import java.io.File;
import java.io.IOException;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author chen:
 * @version 创建时间：2015-6-16 下午10:34:24
 */
public class HardWareActivity2 extends BaseActivity implements SensorEventListener {
    private static final String TAG = null;
    private SensorManager mManager;// 传感器管理对象
    private TextView tv_distance;
    private SoundRecorder recorder;// 录音

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.hardware2);
        super.onCreate(savedInstanceState);
        register();
    }

    @Override
    protected void initViews() {

        tv_distance = (TextView) findViewById(R.id.tv_distance);
        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        recorder = new SoundRecorder();
    }

    @Override
    protected void initData() {

    }


    private void register() {
        mManager.registerListener(this,
                mManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),// 距离感应器
                SensorManager.SENSOR_DELAY_NORMAL);// 注册传感器，第一个参数为距离监听器，第二个是传感器类型，第三个是延迟类型

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.unregisterListener(this);
    }

    // 开始录音
    public void recoder_Start(View v) {
        recorder.startRecording();
    }

    // 结束录音
    public void recoder_Stop(View v) {
        recorder.stopRecording();
    }

    // 播放录音
    public void recoder_Play(View v) {
        recorder.play();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY:
                float distance = event.values[0];
                if (distance < event.sensor.getMaximumRange()) {
                    tv_distance.setText("靠近了");
                } else {
                    tv_distance.setText("没有靠近");
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 麦克风录音
     *
     * @author hui
     */
    class SoundRecorder {

        public SoundRecorder() {
            String mFileName = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            mFileName += File.separator + "rcd_music.3gp";
            Log.i(TAG, "mFileName==" + mFileName);
            fineName = mFileName;
        }

        private String fineName;
        private MediaRecorder mRecorder;

        private boolean isRecording;

        public void startRecording() {

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setOutputFile(newFileName());
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "报错了1！！！", 0).show();
                Log.e(TAG, "prepare() failed:" + e.getMessage());
            }
        }

        public void play() {
            MediaPlayer mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(fineName);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "报错了2！！！", 0).show();
                Log.e(TAG, "prepare() failed" + e.getMessage());
            }
        }

        public void stopRecording() {
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            } catch (Exception e) {

            }
        }

        public String newFileName() {
            return fineName;
        }
    }

}
