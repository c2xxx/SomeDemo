package com.basedamo.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Build;

/**
 * Created by hui on 2015/12/30.
 */
public class AudioUtils {
    public static final int default_frequence = 8000; //录制频率，单位hz.这里的值注意了，写的不好，可能实例化AudioRecord对象的时候，会出错。我开始写成11025就不行。这取决于硬件设备
    public static final int default_channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    public static final int default_audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

    /**
     * 请求音乐焦点(两次调用时间间隔太短时，第二次可能被忽略)
     *
     * @param bMute 值为true时为关闭背景音乐。
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static void requestAudioFocus(Context context, boolean bMute) {
        if (context == null) {
            return;
        }
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (bMute) {
            am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        } else {
            am.abandonAudioFocus(null);
        }
    }
}
