package com.basedamo.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.adapter.VoiceTalkAdapter;
import com.basedamo.data.VoiceTalk;
import com.basedamo.file.FileUtil;
import com.basedamo.media.AudioUtils;
import com.basedamo.media.MediaPlayerHelper;
import com.basedamo.media.MediaRecoderHelper;
import com.basedamo.utils.LogController;
import com.basedamo.view.VoiceButton;
import com.lidroid.xutils.exception.HttpException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 仿微信语音聊天
 * Created by hui on 2016/1/11.
 */
public class VoiceTalkActivity extends BaseActivity {

    private VoiceButton voiceButton;//录音按钮
    //    private File mVoiceFile = null;
    private MediaPlayerHelper mediaPlayerHelper = MediaPlayerHelper.getInstance();
    private ListView lv_voice_talk_list;
    private VoiceTalkAdapter adapter;
    private List<VoiceTalk> mList;
    private boolean isDestory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activit_voice_talk);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("语言聊天");
        voiceButton = (VoiceButton) findViewById(R.id.btn_voiceButton);
        lv_voice_talk_list = (ListView) findViewById(R.id.lv_voice_talk_list);
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        VoiceTalkAdapter.VoickeItemClickListener voiceClick = new VoiceTalkAdapter.VoickeItemClickListener() {
            @Override
            public void onClick(VoiceTalk voiceTalk, int position) {
                doPlayVoice(voiceTalk.getUrl(), position);
            }
        };
        adapter = new VoiceTalkAdapter(this, mList, voiceClick);
        lv_voice_talk_list.setAdapter(adapter);

        MediaRecoderHelper.RecoderListener listener = new MediaRecoderHelper.RecoderListener() {

            @Override
            public void onFinish(File voiceFile) {
                LogController.d("录音完成:" + voiceFile.getAbsolutePath() + " " + voiceFile.exists());
                sendVoice(voiceFile);
            }


            @Override
            public String getTempFileName() {
                return FileUtil.getTempPath("amr");
            }
        };
        MediaRecoderHelper helper = new MediaRecoderHelper(listener);
        voiceButton.setMediaRecoderHelper(helper);
        new Thread() {
            @Override
            public void run() {
                while (!isDestory) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            voiceButton.updateVoiceLevel();
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * 播放音乐
     *
     * @param url      播放的url
     * @param position id
     */
    private void doPlayVoice(String url, final int position) {
        File voiceFile = new File(url);

        if (voiceFile == null || !voiceFile.exists()) {
            return;
        }

        mediaPlayerHelper.playSound(voiceFile.getAbsolutePath(), new MediaPlayerHelper.MediaPlayerListener() {
            @Override
            public void onStart() {
                LogController.d("播放开始");
                AudioUtils.requestAudioFocus(VoiceTalkActivity.this, true);
                adapter.setPlayingPosition(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onEnd(boolean isPlayComplete) {
                LogController.d("播放结束");
                adapter.clearPlayingPosition();
                adapter.notifyDataSetChanged();
                if (isPlayComplete) {
                    AudioUtils.requestAudioFocus(VoiceTalkActivity.this, false);
                }
            }
        });

    }

    public void playVoice(View v) {
//        playLocalVoice(mVoiceFile);
        String remoteAmr = "http://7xpgb3.com1.z0.glb.clouddn.com/IOS1452830408.amr";
        playRemoteVoice(remoteAmr);
    }

    /**
     * 发送文件
     *
     * @param voiceFile
     */
    private void sendVoice(File voiceFile) {
        VoiceTalk voiceTalk = new VoiceTalk();
        voiceTalk.setUrl("" + voiceFile.getAbsolutePath());
        MediaPlayer player = MediaPlayer.create(this, Uri.fromFile(voiceFile));
        int time = player.getDuration();
        player.release();
        time = (int) Math.round(time / 1000.0);
        time = Math.max(time, 1);
        voiceTalk.setTime(time + "");
        mList.add(voiceTalk);
        adapter.notifyDataSetChanged();
    }

    /**
     * 播放远程音频
     *
     * @param remoteAmr 音频路径
     */
    private void playRemoteVoice(String remoteAmr) {
        FileUtil.download(remoteAmr, new FileUtil.DownLoadResult() {
            @Override
            public void onSuccess(File file) {
                playLocalVoice(file);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogController.d("下载失败" + e.getExceptionCode() + "--" + e.getMessage() + "--" + s);
            }
        }, true);
    }

    /**
     * 播放本地音频
     *
     * @param voiceFile 音频文件
     */
    private void playLocalVoice(File voiceFile) {
        if (voiceFile == null || !voiceFile.exists()) {
            return;
        }

        mediaPlayerHelper.playSound(voiceFile.getAbsolutePath(), new MediaPlayerHelper.MediaPlayerListener() {
            @Override
            public void onStart() {
                LogController.d("播放开始");
            }

            @Override
            public void onEnd(boolean isPlayComplete) {
                LogController.d("播放结束");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayerHelper.release();
        voiceButton.onActivityPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
    }
}
