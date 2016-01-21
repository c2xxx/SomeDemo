package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.file.FileUtil;
import com.basedamo.media.AudioPlayer;
import com.basedamo.media.AudioRecoder;
import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;
import com.lidroid.xutils.exception.HttpException;

import java.io.File;

/**
 * Created by hui on 2015/12/25.
 */
public class AudioRecoderActivity extends BaseActivity {

    private String pcmUrl = "http://7xpgb3.com1.z0.glb.clouddn.com/recording-143268130.pcm";
    private Button btn_click;


    private TextView stateView;

    private Button btnStart, btnStop, btnPlay, btnFinish;


    private File audioFile;

    private AudioRecoder audioRecoder;
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_audiorecoder);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(this);

        stateView = (TextView) this.findViewById(R.id.view_state);
        stateView.setText("准备开始");
        btnStart = (Button) this.findViewById(R.id.btn_start);
        btnStop = (Button) this.findViewById(R.id.btn_stop);
        btnPlay = (Button) this.findViewById(R.id.btn_play);
        btnFinish = (Button) this.findViewById(R.id.btn_finish);
        this.findViewById(R.id.btn_remoteaudio_play_after_load).setOnClickListener(this);
        btnFinish.setText("停止播放");

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);
        btnFinish.setEnabled(false);


    }

    //
    @Override
    protected void initData() {
        audioPlayer = new AudioPlayer(this);
        audioRecoder = new AudioRecoder(this);
    }

    //
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_click:
                stateView.setText("文件是否存在：" + audioFile + " " + audioFile.exists());
                if (audioFile.exists() && audioFile.isFile()) {
                    stateView.setText(stateView.getText() + "\n" + audioFile.length());
                }

                break;
            case R.id.btn_start:
                //开始录制
                audioRecoder.start(FileUtil.getTempPath(), 30 * 1000, new AudioRecoder.RecoderListerner() {
                    @Override
                    public void onRecodStart() {
                        btnStart.setEnabled(false);
                        btnPlay.setEnabled(false);
                        btnFinish.setEnabled(false);
                        btnStop.setEnabled(true);
                    }

                    @Override
                    public void onRecodFinish(File file, long timeMillis, boolean isCanceled) {
                        audioFile = file;
                        btnStop.setEnabled(false);
                        btnStart.setEnabled(true);
                        btnPlay.setEnabled(true);
                        btnFinish.setEnabled(false);
                    }
                });
                break;
            case R.id.btn_stop:
                audioRecoder.stop();
                break;
            case R.id.btn_play:
                audioPlayer.play(audioFile, new AudioPlayer.PlayListener() {
                    @Override
                    public void onStart() {

                        btnStart.setEnabled(false);
                        btnStop.setEnabled(false);
                        btnPlay.setEnabled(false);
                        btnFinish.setEnabled(true);
                    }

                    @Override
                    public void onStop() {

                        btnPlay.setEnabled(true);
                        btnFinish.setEnabled(false);
                        btnStart.setEnabled(true);
                        btnStop.setEnabled(false);
                    }
                });
                break;
            case R.id.btn_finish:
                audioPlayer.stop();
                break;
            case R.id.btn_remoteaudio_play_after_load:
                FileUtil.download(pcmUrl, new FileUtil.DownLoadResult() {
                    @Override
                    public void onSuccess(File file) {
                        ToastUtil.show("下载完成");
                        playAudio(file);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        ToastUtil.show("下载失败");
                    }
                }, true);
                break;

        }
    }

    /**
     * 播放音频
     *
     * @param file
     */
    private void playAudio(File file) {
        audioPlayer.play(file, new AudioPlayer.PlayListener() {
            @Override
            public void onStart() {
                LogController.d("播放开始");
            }

            @Override
            public void onStop() {
                LogController.d("播放结束");
            }
        });
    }


}
