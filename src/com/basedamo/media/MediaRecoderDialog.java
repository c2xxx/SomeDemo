package com.basedamo.media;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basedamo.R;

/**
 * Created by hui on 2016/1/11.
 */
public class MediaRecoderDialog {

    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLable;

    private Context mContext;

    public MediaRecoderDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void dimissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 取消界面
     */
    public void wantToCancel() {
        // TODO Auto-generated method stub
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLable.setText(R.string.want_to_cancle);
        }

    }

    /**
     * 设置正在录音时的dialog界面
     */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLable.setText(R.string.shouzhishanghua);
        }
    }

    // 时间过短
    public void tooShort() {
        // TODO Auto-generated method stub
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLable.setText(R.string.tooshort);
        }

    }

    public void showRecordingDialog() {
        // TODO Auto-generated method stub

        mDialog = new Dialog(mContext, R.style.Theme_audioDialog);
        // 用layoutinflater来引用布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_mediarecoder, null);
        mDialog.setContentView(view);


        mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.dialog_voice);
        mLable = (TextView) mDialog.findViewById(R.id.recorder_dialogtext);
        mDialog.setCancelable(false);
        mDialog.show();

    }

    public void updateVoiceLevel(int level) {
        // TODO Auto-generated method stub

        if (mDialog != null && mDialog.isShowing()) {

            //通过level来找到图片的id，也可以用switch来寻址，但是代码可能会比较长
            int resId = mContext.getResources().getIdentifier("v" + level,
                    "drawable", mContext.getPackageName());

            mVoice.setImageResource(resId);
        }

    }

    public void setCancelable(boolean cancelAble) {
        if(mDialog!=null){
            this.mDialog.setCancelable(cancelAble);
        }
    }
}
