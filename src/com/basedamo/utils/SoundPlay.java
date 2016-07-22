package com.basedamo.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.basedamo.R;

/**
 * 音效播放
 * Created by ChenHui on 2016/7/14.
 */
public class SoundPlay {
    private static SoundPool mSoundPool = null;
    private static int mSound = 0;

    public static void init(Context context) {
        //设置最多可容纳10个音频流，音频的品质为5
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //load方法加载指定音频文件，并返回所加载的音频ID。此处使用HashMap来管理这些音频流
        mSound = mSoundPool.load(context, R.raw.key, 5);
    }

    public static void play() {
        mSoundPool.play(mSound, 1, 1, 1, 0, 1);
    }
}

