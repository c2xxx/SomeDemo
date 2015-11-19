package com.basedamo.activity;

import java.util.List;

import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author chen:
 * @version 创建时间：2015-6-16 下午10:34:24
 */
public class HardWareActivity extends Activity {
	private static final String TAG = "HardWareActivity";
	private TextView tv_text, tv_text_wifi, tv_gravity_pb_x,
			tv_gravity_pb_y, tv_gravity_pb_z;
	private ChargingReceiver chargeReceiver;
	private SensorManager sensor_manager;
	private Sensor sensor_accelerometer;
	private MySensorEventListener mySensorEvent;
	private ProgressBar pb_gravity_pb_x;
	private ProgressBar pb_gravity_pb_y;
	private ProgressBar pb_gravity_pb_z;
	private TextView tv_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware);
		init();
		chargeReceiver = new ChargingReceiver();
		register();
	}

	private void register() {
		// 电池
		this.registerReceiver(this.chargeReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		// 重力感应
		sensor_manager.registerListener(mySensorEvent, sensor_accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

		// WIFI信息
		tv_text_wifi.setText(getWifiInfo());
		//版本信息
		getVersionInfo();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(chargeReceiver);
	}

	private void init() {
		tv_version = (TextView) findViewById(R.id.tv_version);

		pb_gravity_pb_x = (ProgressBar) findViewById(R.id.pb_gravity_pb_x);
		pb_gravity_pb_y = (ProgressBar) findViewById(R.id.pb_gravity_pb_y);
		pb_gravity_pb_z = (ProgressBar) findViewById(R.id.pb_gravity_pb_z);

		tv_gravity_pb_x = (TextView) findViewById(R.id.tv_gravity_pb_x);
		tv_gravity_pb_y = (TextView) findViewById(R.id.tv_gravity_pb_y);
		tv_gravity_pb_z = (TextView) findViewById(R.id.tv_gravity_pb_z);

		tv_text_wifi = (TextView) findViewById(R.id.tv_text_wifi);
		tv_text = (TextView) findViewById(R.id.tv_text);
		sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor_accelerometer = sensor_manager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mySensorEvent = new MySensorEventListener();
	}

	// 版本信息
	private void getVersionInfo() {
		String phoneInfo = "产品型号: " + android.os.Build.PRODUCT;
		phoneInfo += "\r\n USER: " + android.os.Build.USER;
		phoneInfo += "\r\n CPU_ABI: " + android.os.Build.CPU_ABI;
		phoneInfo += "\r\n TAGS: " + android.os.Build.TAGS;
		phoneInfo += "\r\n VERSION_CODES.BASE: "
				+ android.os.Build.VERSION_CODES.BASE;
		phoneInfo += "\r\n MODEL: " + android.os.Build.MODEL;
		phoneInfo += "\r\n VERSION.RELEASE: "
				+ android.os.Build.VERSION.RELEASE;
		phoneInfo += "\r\n DEVICE: " + android.os.Build.DEVICE;
		phoneInfo += "\r\n DISPLAY: " + android.os.Build.DISPLAY;
		phoneInfo += "\r\n BRAND: " + android.os.Build.BRAND;
		phoneInfo += "\r\n BOARD: " + android.os.Build.BOARD;
		phoneInfo += "\r\n ID: " + android.os.Build.ID;
		phoneInfo += "\r\n MANUFACTURER: " + android.os.Build.MANUFACTURER;
		phoneInfo += "\r\n FINGERPRINT: " + android.os.Build.FINGERPRINT;
		tv_version.setText(phoneInfo);
	}

	public void clear(View v) {
		tv_text.setText("");
		// WIFI信息
		tv_text_wifi.setText("");
		tv_version.setText("");
	}

	public void read(View v) {
		register();
	}

	// 传感器监听
	public class MySensorEventListener implements SensorEventListener {

		@Override
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_PROXIMITY:
				// float distance = event.values[0];
				// if (distance < event.sensor.getMaximumRange()) {
				// iv_proxi.setVisibility(View.INVISIBLE);
				// } else {
				// iv_proxi.setVisibility(View.VISIBLE);
				// }
				// break;
			case Sensor.TYPE_ACCELEROMETER:// 重力传感器信息
				float[] distances = event.values;
				tv_gravity_pb_x.setText("" + distances[0]);
				tv_gravity_pb_y.setText("" + distances[1]);
				tv_gravity_pb_z.setText("" + distances[2]);
				pb_gravity_pb_x.setProgress((int) (10 + distances[0]) * 5);
				pb_gravity_pb_y.setProgress((int) (10 + distances[1]) * 5);
				pb_gravity_pb_z.setProgress((int) (10 + distances[2]) * 5);
				break;
			default:
				break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	}

	/**
	 * 电池信息监听
	 * 
	 * @author hui
	 */
	class ChargingReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				int status = intent.getIntExtra("status", 0);
				int health = intent.getIntExtra("health", 1);
				boolean present = intent.getBooleanExtra("present", false);
				int level = intent.getIntExtra("level", 0);
				int scale = intent.getIntExtra("scale", 0);
				int plugged = intent.getIntExtra("plugged", 0);
				int voltage = intent.getIntExtra("voltage", 0);
				int temperature = intent.getIntExtra("temperature", 0);
				String technology = intent.getStringExtra("technology");

				String statusString = "Unknown";
				String healthString = "Unknown";
				String acString = "Unplugged";

				switch (status) {
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					statusString = "Unknown";
					break;
				case BatteryManager.BATTERY_STATUS_CHARGING:
					statusString = "Charging";
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					statusString = "Not Charging";
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					statusString = "Not Charging";
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					statusString = "Full";
					break;
				}

				switch (health) {
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					healthString = "Unknown";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					healthString = "Good";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					healthString = "Overheat";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					healthString = "Dead";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					healthString = "Voltage";
					break;
				case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
					healthString = "Unspecified failure";
					break;
				}

				switch (plugged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					acString = "AC";
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					acString = "USB";
					break;
				}
				String temp = "\r\n电池信息：\r\n present:" + present + "\r\n level"
						+ level + "\r\n scale最大电量" + scale + " plugged"
						+ plugged + "\r\n temperature温度" + temperature
						+ "\r\n technology电池种类" + technology + "\r\n 状态"
						+ statusString + "\r\n 情况" + healthString + "\r\n 插拔"
						+ acString + "\r\n 电压" + voltage;

				Log.i(TAG, temp);
				if (tv_text != null)
					tv_text.setText(temp);
			}
		}

	}

	private String getWifiInfo() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String maxText = info.getMacAddress();
		String ipText = intToIp(info.getIpAddress());
		String status = "";
		if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			status = "WIFI_STATE_ENABLED";
		}
		String SSID = info.getSSID();
		int linkSpeed = info.getLinkSpeed();
		int networkID = info.getNetworkId();
        String bssid = info.getBSSID();
        int rssi = info.getRssi();
		String temp = "\r\nWIFI信息\r\n ip: " + ipText
				+ "\r\n wifi status :" + status 
				+ "\r\n SSID :" + SSID 
				+ "\r\n net work id :" + networkID 
				+ "\r\n bssid :" + bssid
				+ "\r\n mac :" + maxText 
				+ "\r\n connection speed :" + linkSpeed + "Mbps"
				+ "\r\n rssi :" + rssi + "dB" + "\n\r";

		List<ScanResult> wifiSs = wifi.getScanResults();

		temp += "可用WIFI/信号强度:";
		// 当前可用的wifi信息
		for (int i = 0; i < wifiSs.size(); i++) {
			temp += "\r\n" + wifiSs.get(i).SSID + "/" + wifiSs.get(i).level
					+ "dB";
			if (i == 7) {
				break;
			}
		}
		return temp;
	}

	private String intToIp(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
	}
}