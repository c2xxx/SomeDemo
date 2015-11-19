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
 * @version ����ʱ�䣺2015-6-16 ����8:53:30
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

	// ������
	public void zhengdong(View v) {
		/*
		 * �������𶯴�С����ͨ���ı�pattern���趨���������ʱ��̫�̣���Ч�����ܸо�����
		 */
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 100, 400, 100, 400 }; // ֹͣ ���� ֹͣ ����
		int x = 0;
		if (x == 0) {// �����
			vibrator.vibrate(pattern, -1); // �ظ����������pattern���� ����벻ͣ�𶯣�index��Ϊ-1
		} else {// ������
			// �����𶯣�������ָ����ʱ��
			vibrator.vibrate(500);
		}
	}

	public void zhengdongCancel(View v) {
		/*
		 * �������𶯴�С����ͨ���ı�pattern���趨���������ʱ��̫�̣���Ч�����ܸо�����
		 */
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.cancel();
	}

	// ��������
	public void volumeClock(View v) {
		// ��������,��ʼ������
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// �������
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		float volume = ((1.0f * seekbar.getProgress()) / seekbar.getMax());
		int targetVolume = (int) (maxVolume * volume);
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, targetVolume,
				0); // tempVolume:��������ֵ
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
				AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	// ý������
	public void volumeMedia(View v) {
		// ��������,��ʼ������
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// �������
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = ((1.0f * seekbar.getProgress()) / seekbar.getMax());
		int targetVolume = (int) (maxVolume * volume);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume,
				0); // tempVolume:��������ֵ
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
				AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
	}

	// ��ͨ����������
	public void playTongHua(View v) {
		final MediaPlayer player = new MediaPlayer();
		// ��������,��ʼ������
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		try {
			AssetFileDescriptor afd = getResources().openRawResourceFd(
					R.raw.hardware_check_sound);
			player.setDataSource(afd.getFileDescriptor());
			int streamType = AudioManager.STREAM_VOICE_CALL;
			int max = audio.getStreamMaxVolume(streamType);
			audio.setStreamVolume(streamType, max, 1);
			player.setAudioStreamType(streamType);// ��prepareǰ���ã�����ͨ��MediaPlayer.create�õ���player�ǲ��е�
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

	// ��������������
	public void playAlarm(View v) {
		final MediaPlayer player = new MediaPlayer();
		// ��������,��ʼ������
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		try {
			AssetFileDescriptor afd = getResources().openRawResourceFd(
					R.raw.hardware_check_sound);
			player.setDataSource(afd.getFileDescriptor());
			int streamType = AudioManager.STREAM_ALARM;
			int max = audio.getStreamMaxVolume(streamType);
			audio.setStreamVolume(streamType, max, 1);
			player.setAudioStreamType(streamType);// ��prepareǰ���ã�����ͨ��MediaPlayer.create�õ���player�ǲ��е�
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
