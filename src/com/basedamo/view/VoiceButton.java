package com.basedamo.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.basedamo.R;
import com.basedamo.media.MediaRecoderDialog;
import com.basedamo.media.MediaRecoderHelper;
import com.basedamo.utils.LogController;

/**
 * 声音录制按钮
 * Created by hui on 2016/1/11.
 */
public class VoiceButton extends TextView {
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private static final int DISTANCE_Y_CANCEL = 0;
    private static final int MIN_TIME = 500;
    private int mCurrentState;
    private long startTime;//按下时间，用于计算最短时长
    private Thread thrad_updateVoiceLevel = null;

    private MediaRecoderDialog mediaRecoderDialog;
    private MediaRecoderHelper mediaRecoderHelper;

    /**
     * 设置录音监听
     *
     * @param mediaRecoderHelper
     */
    public void setMediaRecoderHelper(MediaRecoderHelper mediaRecoderHelper) {
        this.mediaRecoderHelper = mediaRecoderHelper;
    }

    public VoiceButton(Context context) {
        super(context);
    }

    public VoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoiceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VoiceButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                LogController.d("timeNow A==" + System.currentTimeMillis());
                LogController.d("ACTION_DOWN    开始录音");
                changeState(STATE_RECORDING);
                if (mediaRecoderHelper != null) {
                    mediaRecoderHelper.start();
                }
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                LogController.d("timeNow B==" + System.currentTimeMillis());
                if (wantToCancel(x, y)) {
                    LogController.d("ACTION_UP  取消录音");
                    if (mediaRecoderHelper != null) {
                        mediaRecoderHelper.cancel();
                    }
                } else if (System.currentTimeMillis() - startTime < MIN_TIME) {//时间太短
                    LogController.d("ACTION_UP  时间太短");
                    if (mediaRecoderDialog != null) {
                        mediaRecoderDialog.tooShort();
                        mediaRecoderDialog.setCancelable(true);
//                        final MediaRecoderDialog dialog = mediaRecoderDialog;
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(1500);
//                                    dialog.dimissDialog();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
                        mediaRecoderDialog = null;
                        if (mediaRecoderHelper != null) {
                            mediaRecoderHelper.cancel();
                        }
                    }
                } else {
                    if (mediaRecoderHelper != null) {
                        mediaRecoderHelper.finish();
                    }
                    LogController.d("ACTION_UP  发送录音");
                }
                changeState(STATE_NORMAL);
                break;
            case MotionEvent.ACTION_MOVE:
                if (wantToCancel(x, y)) {
                    changeState(STATE_WANT_TO_CANCEL);
                } else {
                    changeState(STATE_RECORDING);
                }
//                updateVoiceLevel();
                break;
        }
        return true;
    }

    /**
     * 更新声音大小
     */
    public void updateVoiceLevel() {
        if (mediaRecoderDialog != null && mediaRecoderHelper != null) {
            int level = mediaRecoderHelper.getVoiceLevel(mediaRecoderDialog.MAX_VOICE_LEVEL);
            LogController.d("声音大小：" + level);
            mediaRecoderDialog.updateVoiceLevel(level);
        }
    }


    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL://结束
                    setText("按住说话");
                    setBackgroundResource(R.drawable.bg_voicebutton_normal);
                    if (mediaRecoderDialog != null) {
                        mediaRecoderDialog.dimissDialog();
                        mediaRecoderDialog = null;
                    }
                    break;
                case STATE_RECORDING://按下、
                    setText("松开 结束");
                    setBackgroundResource(R.drawable.bg_voicebutton_press);
                    if (mediaRecoderDialog == null) {
                        mediaRecoderDialog = new MediaRecoderDialog(this.getContext());
                        mediaRecoderDialog.showRecordingDialog();
                    } else {
                        mediaRecoderDialog.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    setText("松开手指，结束发送");
                    if (mediaRecoderDialog != null) {
                        mediaRecoderDialog.wantToCancel();
                    }
                    break;
            }
        }

    }

    /**
     * 判断触点是否位于放开取消的位置
     *
     * @return
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {// 判断是否在左边，右边，上边，下边
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    /**
     * onPause时，取消录音
     * 设置dialog可以取消
     * 防止有些情况有ACTION_DOWN事件，没有ACTION_UP
     */
    public void onActivityPause() {
        if (mediaRecoderDialog != null) {
            mediaRecoderDialog.setCancelable(true);
        }
        if (mediaRecoderHelper != null) {
            mediaRecoderHelper.cancel();
        }
    }
}
