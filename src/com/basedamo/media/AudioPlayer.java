package com.basedamo.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 音频播放
 * Created by hui on 2015/12/29.
 */
public class AudioPlayer {
    private final boolean IS_REQUEST_AUDIO_FOCUS = true;//是否获取音乐焦点，暂停其他音乐播放
    private int bufferSize = 0;
    private int frequence;
    private int channelConfig;
    private int audioEncoding;
    private PlayTask playTask;
    private Context context;

    public AudioPlayer(Context context) {
        this.frequence = AudioUtils.default_frequence;
        this.channelConfig = AudioUtils.default_channelConfig;
        this.audioEncoding = AudioUtils.default_audioEncoding;
        this.context = context;
        init();
    }

    private void init() {
        bufferSize = AudioTrack.getMinBufferSize(frequence, channelConfig, audioEncoding);
    }

    public AudioPlayer(Context context, int frequence, int channelConfig, int audioEncoding) {
        this.frequence = frequence;
        this.channelConfig = channelConfig;
        this.audioEncoding = audioEncoding;
        this.context = context;
        init();
    }

    public void stop() {
        if (playTask != null) {
            playTask.isPlaying = false;
            playTask = null;
        }
    }

    /**
     * 播放音频
     * 播放时停止前一段音频
     * 如果停止的和当前是同一段音频，则不再播放、
     *
     * @param file
     * @param playListener
     */
    public void play(File file, PlayListener playListener) {
        if (file != null && file.exists()) {
            if (playTask != null && playTask.isPlaying) {
                playTask.isPlaying = false;
                String fileName = playTask.fileName;
                playTask = null;
                if (TextUtils.equals(fileName, file.getAbsolutePath())) {
                    return;
                }
            }

            playTask = new PlayTask(file, playListener);
            playTask.execute();
        }
    }

    class PlayTask extends AsyncTask<Void, Integer, Void> {
        PlayListener playListener = null;
        private boolean isPlaying;//正在播放

        String fileName = null;
        File file;

        public PlayTask(File file, PlayListener playListener) {
            this.file = file;
            fileName = file.getAbsolutePath();
            this.playListener = playListener;


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            short[] buffer = new short[bufferSize / 4];
            try {

                //定义输入流，将音频写入到AudioTrack类中，实现播放
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

                //实例AudioTrack
                AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, frequence, channelConfig, audioEncoding, bufferSize, AudioTrack.MODE_STREAM);

                //开始播放
                track.play();
                //由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
                while (isPlaying && dis.available() > 0) {
                    int i = 0;
                    while (dis.available() > 0 && i < buffer.length) {
                        buffer[i] = dis.readShort();
                        i++;
                    }
                    //然后将数据写入到AudioTrack中
                    track.write(buffer, 0, buffer.length);

                }

                //播放结束
                track.stop();
                track.release();
                track = null;
                dis.close();
                dis = null;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            isPlaying = false;
            if (playListener != null) {
                playListener.onStop();
            }

            if (IS_REQUEST_AUDIO_FOCUS) {
                AudioUtils.requestAudioFocus(context, false);
            }
            System.gc();
            playTask = null;
        }

        protected void onPreExecute() {
            isPlaying = true;

            if (IS_REQUEST_AUDIO_FOCUS) {
                AudioUtils.requestAudioFocus(context, true);
            }
            if (playListener != null) {
                playListener.onStart();
            }
        }

    }

    public interface PlayListener {
        /**
         * 播放开始
         */
        void onStart();

        /**
         * 录音结束
         */
        void onStop();
    }
}
