package com.basedamo.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * 音频播放
 * Created by hui on 2016/1/18.
 */
public class MediaPlayerHelper {
    private MediaPlayer mPlayer;
    private MediaPlayerListener mListener;
    private String playingFile;//正在播放的音乐路径

    //单例
    private static MediaPlayerHelper instance = null;

    private MediaPlayerHelper() {

    }

    public static MediaPlayerHelper getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper();
                }
            }
        }
        return instance;
    }

    public void playSound(String filePathString, final MediaPlayerListener listener) {
        if (TextUtils.equals(playingFile, filePathString)) {//如果播放的和已经在播放的是同一段音乐则不再开始
            if (mListener != null) {
                mListener.onEnd(true);
                mListener = null;
            }
            release();
            return;
        }
        release();
        mPlayer = new MediaPlayer();
        //保险起见，设置报错监听
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlayer.reset();
                return false;
            }
        });

        mListener = listener;
        playingFile = filePathString;
        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playingFile = null;
                    listener.onEnd(true);
                }
            });
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
            listener.onStart();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    //停止函数
//    public void pause() {
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            mPlayer.pause();
//            isPause = true;
//        }
//    }
//
//    //继续
//    public void resume() {
//        if (mPlayer != null && isPause) {
//            mPlayer.start();
//            isPause = false;
//        }
//    }


    public void release() {
        playingFile = null;
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;

            if (mListener != null) {
                mListener.onEnd(false);
            }
        }
    }

    public interface MediaPlayerListener {
        void onStart();

        /**
         * @param isPlayComplete 是否播放完成，而不是继续播放下一段音频(区别于未播放完成被终止)
         */
        void onEnd(boolean isPlayComplete);
    }
}
