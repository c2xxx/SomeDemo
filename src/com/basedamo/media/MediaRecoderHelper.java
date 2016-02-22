package com.basedamo.media;

import android.media.MediaRecorder;
import android.os.AsyncTask;

import com.basedamo.utils.LogController;

import java.io.File;
import java.io.IOException;

/**
 * 录音
 * Created by hui on 2016/1/11.
 */
public class MediaRecoderHelper {


    /**
     * 音频录制采样率
     */
    private static final int AUDIO_RECODER_RATE = 8000;
    private MediaRecorder mRecorder;


    /**
     * 单例化这个类
     */
    private static MediaRecoderHelper mInstance;
    private boolean isPrepared;// 是否准备好了
    private RecoderListener mListener;
    private File voiceFile;

    public MediaRecoderHelper(RecoderListener listener) {
        this.mListener = listener;
    }

    /**
     * 开始录制
     */
    public void start() {
        new RecoderTask().execute();//使用任务，避免在UI线程做耗时操作

    }


    private class RecoderTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                // 一开始应该是false的
                isPrepared = false;
                String fileName = mListener.getTempFileName();
                voiceFile = new File(fileName);

                if (voiceFile.exists()) {
                    voiceFile.delete();
                }

                mRecorder = new MediaRecorder();

                // 设置输出文件
                mRecorder.setOutputFile(voiceFile.getAbsolutePath());
                // 设置meidaRecorder的音频源是麦克风
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // 设置文件音频的输出格式为amr
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                // 设置音频的编码格式为amr
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //设置采样率
                mRecorder.setAudioSamplingRate(AUDIO_RECODER_RATE);
                // 严格遵守google官方api给出的mediaRecorder的状态流程图
                mRecorder.prepare();

                mRecorder.start();
                // 准备结束
                isPrepared = true;

            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (mListener != null) {
                mListener.onStart();//准备结束，开始录音
            }
        }
    }

    // 获得声音的level
    public int getVoiceLevel(int maxLevel) {
        // mRecorder.getMaxAmplitude()这个是音频的振幅范围，值域是1-32767
        if (isPrepared && mRecorder != null) {
            try {
//                // 取整+1，否则去不到7
//                return maxLevel * mRecorder.getMaxAmplitude() / 32768 + 1;

                double maxAmplitude = (double) mRecorder.getMaxAmplitude();
                double db = 0;// 分贝
                if (maxAmplitude > 1) {
                    db = 20 * Math.log10(maxAmplitude);
                }
                LogController.d("分贝值：" + db);

                //分贝分布范围：【45,90】
                db = Math.max(db, 51);//最小51
                db = Math.min(db, 90);//最大90
                double dbMax = 90.3;//可能的最大值
                return (int) Math.ceil(((db - 50) / (dbMax - 50)) * maxLevel);
            } catch (Exception e) {

            }
        }

        return 1;
    }

    // 释放资源
    public void release() {
        // 严格按照api流程进行
        if (isPrepared && mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    //
    // 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
    // 这是与release的方法的区别
    public void cancel() {
        release();
        if (voiceFile != null) {
            voiceFile.delete();
            voiceFile = null;
        }
    }

    /**
     * 结束录制
     */
    public void finish() {
        release();
        mListener.onFinish(voiceFile);
        voiceFile = null;
    }

    /**
     * 回调函数，准备完毕，准备好后，button才会开始显示录音框
     *
     * @author nickming
     */
    public interface RecoderListener {
        /**
         * 录音开始
         */
        void onStart();

        /**
         * 结束录音
         *
         * @param voiceFile
         */
        void onFinish(File voiceFile);

        /**
         * @return 获取临时文件名称
         */
        String getTempFileName();
    }
}
