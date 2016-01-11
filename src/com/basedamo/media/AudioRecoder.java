package com.basedamo.media;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import com.basedamo.utils.LogController;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by hui on 2015/12/30.
 */
public class AudioRecoder {

    private final boolean IS_REQUEST_AUDIO_FOCUS = true;//是否获取音乐焦点，暂停其他音乐播放

    private Context context;
    private int frequence;
    private int channelConfig;
    private int audioEncoding;
    private RecordTask recordTask;

    public AudioRecoder(Context context) {
        this.frequence = AudioUtils.default_frequence;
        this.channelConfig = AudioUtils.default_channelConfig;
        this.audioEncoding = AudioUtils.default_audioEncoding;
        this.context = context;
        init();
    }

    private void init() {
    }

    public AudioRecoder(Context context, int frequence, int channelConfig, int audioEncoding) {
        this.frequence = frequence;
        this.channelConfig = channelConfig;
        this.audioEncoding = audioEncoding;
        this.context = context;
        init();
    }


    /**
     * @param targetPath 保存路径
     * @param maxMillis  最长录音时间
     */
    public void start(String targetPath, int maxMillis, RecoderListerner recoderListerner) {
        stop();
        recordTask = new RecordTask(new File(targetPath), maxMillis, recoderListerner);
        recordTask.execute();
    }

    public void cancel() {
        if (recordTask != null) {
            recordTask.isRecording = false;
            recordTask.isCanceled = true;
            recordTask = null;
        }
    }

    public void stop() {
        if (recordTask != null) {
            recordTask.isRecording = false;
            recordTask = null;
        }
    }

    private class RecordTask extends AsyncTask<Void, Double, Void> {

        private boolean isRecording;
        private boolean isCanceled = false;
        RecoderListerner recoderListerner;
        File audioFile;
        long startTime;//录音开始时间
        long maxMillis;//录音最大时长

        public RecordTask(File audioFile, int maxMillis, RecoderListerner recoderListerner) {
            this.audioFile = audioFile;
            this.recoderListerner = recoderListerner;
            this.maxMillis = maxMillis;
            if (this.maxMillis < 3000) {
                this.maxMillis = 3000;
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                //开通输出流到指定的文件
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
                //根据定义好的几个配置，来获取合适的缓冲大小
                int bufferSize = AudioRecord.getMinBufferSize(frequence, channelConfig, audioEncoding);
                //实例化AudioRecord
                AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, frequence, channelConfig, audioEncoding, bufferSize);
                //定义缓冲
                short[] buffer = new short[bufferSize / 4];
                int bufferLenth = buffer.length;
                //开始录制
                record.startRecording();


                long preTime = 0;
                //定义循环，根据isRecording的值来判断是否继续录制
                while (isRecording && !isOutTime()) {
                    int bufferReadResult = record.read(buffer, 0, bufferLenth);//有建议改为160
                    //循环将buffer中的音频数据写入到OutputStream中
                    for (int i = 0; i < bufferReadResult; i++) {
                        dos.writeShort(buffer[i]);
                    }

                    //控制每秒打印分贝次数
                    if (System.currentTimeMillis() - preTime > 200) {
                        preTime = System.currentTimeMillis();

                        double volume = getVolume(buffer, bufferReadResult);
                        publishProgress(volume); //向UI线程报告当前进度
                    }

                }
                //录制结束
                record.stop();
                record.release();
                dos.close();
            } catch (Exception e) {
            }
            return null;
        }

        /**
         * 计算分贝值
         *
         * @param buffer
         * @param bufferReadResult
         * @return
         */
        private double getVolume(short[] buffer, double bufferReadResult) {
            //计算分贝值===start
            long v = 0;
            // 将 buffer 内容取出，进行平方和运算
            for (int i = 0; i < buffer.length; i++) {
                v += buffer[i] * buffer[i];
            }
            // 平方和除以数据总长度，得到音量大小。
            double mean = v / bufferReadResult;
            //                    Log.d(TAG, "分贝值:" + volume);
            //计算分贝值===end

            double volume = 10 * Math.log10(mean);
            if (Double.isInfinite(volume)) {
                volume = 0;
            }

            return volume;
        }

        //当在上面方法中调用publishProgress时，该方法触发,该方法在UI线程中被执行
        @Override
        protected void onProgressUpdate(Double... progress) {
            if (progress.length > 0) {
                LogController.d(String.format("音量：%.2f分贝", progress[0]));
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            if (IS_REQUEST_AUDIO_FOCUS) {
                AudioUtils.requestAudioFocus(context, false);
            }
            isRecording = false;
            if (recoderListerner != null) {
                recoderListerner.onRecodFinish(audioFile, System.currentTimeMillis() - startTime, isCanceled);
            }
        }

        @Override
        protected void onPreExecute() {
            isRecording = true;
            if (IS_REQUEST_AUDIO_FOCUS) {
                AudioUtils.requestAudioFocus(context, true);
            }
            startTime = System.currentTimeMillis();
            if (recoderListerner != null) {
                recoderListerner.onRecodStart();
            }
        }


        /**
         * @return 判断是否已经超时
         */
        boolean isOutTime() {
            return System.currentTimeMillis() - startTime > maxMillis;
        }
    }

    /**
     *
     */
    public interface RecoderListerner {
        /**
         * 录音开始
         */
        void onRecodStart();

        /**
         * 录音结束
         *
         * @param file       保存的文件
         * @param timeMillis 时长
         * @param isCanceled 是否已经取消
         */
        void onRecodFinish(File file, long timeMillis, boolean isCanceled);
    }

}
