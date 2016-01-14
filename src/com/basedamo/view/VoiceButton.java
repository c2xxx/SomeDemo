package com.basedamo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.basedamo.R;
import com.basedamo.media.MediaRecoderDialog;
import com.basedamo.utils.LogController;

import java.util.Random;

/**
 * Created by hui on 2016/1/11.
 */
public class VoiceButton extends TextView {
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;
    private static final int DISTANCE_Y_CANCEL = 0;
    private int mCurrentState;
    private MediaRecoderDialog mediaRecoderDialog;

    public MediaRecoderDialog getMediaRecoderDialog() {
        return mediaRecoderDialog;
    }

    public void setMediaRecoderDialog(MediaRecoderDialog mediaRecoderDialog) {
        this.mediaRecoderDialog = mediaRecoderDialog;
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
                LogController.d("ACTION_DOWN    开始录音");
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_UP:
                if (wantToCancel(x, y)) {
                    LogController.d("ACTION_UP  取消录音");
//                } else if ("时间太短" == "x") {
                    if (mediaRecoderDialog != null) {
                        mediaRecoderDialog.tooShort();
                        mediaRecoderDialog.setCancelable(true);
                        final MediaRecoderDialog dialog = mediaRecoderDialog;
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    dialog.dimissDialog();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        mediaRecoderDialog = null;
                    }
                } else {
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
                updateVoiceLevel();
                break;
        }
        return true;
    }

    Random random;

    private void updateVoiceLevel() {
        if (random == null) {
            random = new Random();
        }
        mediaRecoderDialog.updateVoiceLevel(random.nextInt(7) + 1);
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
}
