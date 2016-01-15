package com.basedamo.activity;

import android.os.Bundle;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.file.FileUtil;
import com.basedamo.media.MediaRecoderHelper;
import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;
import com.basedamo.view.VoiceButton;

import java.io.File;

/**
 * Created by hui on 2016/1/11.
 */
public class VoiceTalkActivity extends BaseActivity {

    private VoiceButton voiceButton;//录音按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activit_voice_talk);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("语言聊天");
        voiceButton= (VoiceButton) findViewById(R.id.btn_voiceButton);
    }

    @Override
    protected void initData() {
        MediaRecoderHelper.RecoderListener listener=new MediaRecoderHelper.RecoderListener(){

            @Override
            public void onFinish(File voiceFile) {
                ToastUtil.show("录音完成");
                LogController.d("录音完成:"+voiceFile.getAbsolutePath());
            }

            @Override
            public String getTempFileName() {
                return FileUtil.getTempPath();
            }
        };
        MediaRecoderHelper helper=new MediaRecoderHelper(listener);
        voiceButton.setMediaRecoderHelper(helper);
    }
}
