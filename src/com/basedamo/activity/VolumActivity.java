package com.basedamo.activity;

import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;
import com.basedamo.R.raw;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.SeekBar;

/**
 * @author chen:
 * @version 创建时间：2015-6-16 下午8:53:30
 */
public class VolumActivity extends Activity {
	private SeekBar seekbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volum);
		init();
	}

	private void init() {
		seekbar = (SeekBar) findViewById(R.id.seekbar);
	}

	// 启动振动
	public void zhengdong(View v) {
		/*
		 * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
		 */
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
		int x = 0;
		if (x == 0) {// 间隔振动
			vibrator.vibrate(pattern, -1); // 重复两次上面的pattern次数 如果想不停震动，index设为-1
		} else {// 连续振动
			// 启动震动，并持续指定的时间
			vibrator.vibrate(500);
		}
	}

	public void zhengdongCancel(View v) {
		/*
		 * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
		 */
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.cancel();
	}

	// 闹钟音量
	public void volumeClock(View v) {
		// 音量控制,初始化定义
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// 最大音量
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		float volume = ((1.0f * seekbar.getProgress()) / seekbar.getMax());
		int targetVolume = (int) (maxVolume * volume);
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, targetVolume,
				0); // tempVolume:音量绝对值
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
				AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	// 媒体音量
	public void volumeMedia(View v) {
		// 音量控制,初始化定义
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// 最大音量
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = ((1.0f * seekbar.getProgress()) / seekbar.getMax());
		int targetVolume = (int) (maxVolume * volume);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume,
				0); // tempVolume:音量绝对值
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	// 以通话声音播放
	public void playTongHua(View v) {
		final MediaPlayer player = new MediaPlayer();
		// 音量控制,初始化定义
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		try {
			AssetFileDescriptor afd = getResources().openRawResourceFd(
					R.raw.hardware_check_sound);
			player.setDataSource(afd.getFileDescriptor());
			int streamType = AudioManager.STREAM_VOICE_CALL;
			int max = audio.getStreamMaxVolume(streamType);
			audio.setStreamVolume(streamType, max, 1);
			player.setAudioStreamType(streamType);// 在prepare前设置，所以通过MediaPlayer.create得到的player是不行的
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				player.release();
			}
		});

		player.setLooping(false);
		player.setVolume(1, 1);
		player.start();
	}

	// 以闹钟声音播放
	public void playAlarm(View v) {
		final MediaPlayer player = new MediaPlayer();
		// 音量控制,初始化定义
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		try {
			AssetFileDescriptor afd = getResources().openRawResourceFd(
					R.raw.hardware_check_sound);
			player.setDataSource(afd.getFileDescriptor());
			int streamType = AudioManager.STREAM_ALARM;
			int max = audio.getStreamMaxVolume(streamType);
			audio.setStreamVolume(streamType, max, 1);
			player.setAudioStreamType(streamType);// 在prepare前设置，所以通过MediaPlayer.create得到的player是不行的
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				player.release();
			}
		});

		player.setLooping(false);
		player.setVolume(1, 1);
		player.start();
	}
}
