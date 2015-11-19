package com.basedamo.activity;

import java.io.File;
import java.io.IOException;

import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author chen:
 * @version ����ʱ�䣺2015-6-16 ����10:34:24
 */
public class HardWareActivity2 extends Activity implements SensorEventListener {
	private static final String TAG = null;
	private SensorManager mManager;// �������������
	private TextView tv_distance;
	private SoundRecorder recorder;// ¼��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware2);
		init();
		register();
	}

	private void init() {
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		recorder = new SoundRecorder();
	}

	private void register() {
		mManager.registerListener(this,
				mManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),// �����Ӧ��
				SensorManager.SENSOR_DELAY_NORMAL);// ע�ᴫ��������һ������Ϊ������������ڶ����Ǵ��������ͣ����������ӳ�����

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mManager.unregisterListener(this);
	}

	// ��ʼ¼��
	public void recoder_Start(View v) {
		recorder.startRecording();
	}

	// ����¼��
	public void recoder_Stop(View v) {
		recorder.stopRecording();
	}

	// ����¼��
	public void recoder_Play(View v) {
		recorder.play();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_PROXIMITY:
			float distance = event.values[0];
			if (distance < event.sensor.getMaximumRange()) {
				tv_distance.setText("������");
			} else {
				tv_distance.setText("û�п���");
			}
			break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO

	}

	/**
	 * ��˷�¼��
	 * 
	 * @author hui
	 * 
	 */
	class SoundRecorder {

		public SoundRecorder() {
			String mFileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			mFileName += File.separator + "rcd_music.3gp";
			Log.i(TAG, "mFileName==" + mFileName);
			fineName = mFileName;
		}

		private String fineName;
		private MediaRecorder mRecorder;

		private boolean isRecording;

		public void startRecording() {

			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			mRecorder.setOutputFile(newFileName());
			try {
				mRecorder.prepare();
				mRecorder.start();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "������1������", 0).show();
				Log.e(TAG, "prepare() failed:" + e.getMessage());
			}
		}

		public void play() {
			MediaPlayer mPlayer = new MediaPlayer();
			try {
				mPlayer.setDataSource(fineName);
				mPlayer.prepare();
				mPlayer.start();
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), "������2������", 0).show();
				Log.e(TAG, "prepare() failed" + e.getMessage());
			}
		}

		public void stopRecording() {
			try {
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;
			} catch (Exception e) {

			}
		}

		public String newFileName() {
			return fineName;
		}
	}

}
