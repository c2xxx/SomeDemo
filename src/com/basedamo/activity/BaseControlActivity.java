package com.basedamo.activity;

import com.basedamo.R;
import com.basedamo.R.drawable;
import com.basedamo.R.id;
import com.basedamo.R.layout;
import com.readystatesoftware.viewbadger.BadgeView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/**
 * @author chen:
 * @version 创建时间：2015-6-13 下午11:25:45
 */
public class BaseControlActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basecontrol1);
		// 自动填充对话框
		AutoCompleteTextView actv_demo = (AutoCompleteTextView) findViewById(R.id.actv_demo);
		String[] strs = { "aaaa", "bbbb", "CBA", "ABC", "abc", "中国", "中华", "福建" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, strs);
		actv_demo.setAdapter(adapter);
		
		showBadgeView();
	}

	private void showBadgeView() {
		TextView tv=(TextView) findViewById(R.id.tv_basecontrol_badge);
		BadgeView badgeView = new BadgeView(this, tv);
        badgeView.setText("29");
        badgeView.show();
	}

	// 推送按钮
	public void showNocifiction(View v) {
		// Get a reference to the NotificationManager:得到服务
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		// Instantiate the Notification:初始化
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "Hello  tickerText";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		// 指定别的东西
		// 默认声音
		notification.defaults |= Notification.DEFAULT_SOUND;
		// 添加振动
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		// 不可清除
		notification.flags |= Notification.FLAG_NO_CLEAR;
		// 通知被点击后，自动消失
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// 自定义闪光灯的方式
		notification.ledARGB = 0xff0000ff;
		notification.ledOnMS = 500;
		notification.ledOffMS = 500;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		// Define the notification's message and PendingIntent: 创建PendingIntent
		Context context = getApplicationContext();
		CharSequence contentTitle = "My notification Title";
		CharSequence contentText = "Hello World!";
		Intent notificationIntent = new Intent(this, NiceButton.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		// Pass the Notification to the NotificationManager:显示
		final int HELLO_ID = 1;
		mNotificationManager.notify(HELLO_ID, notification);
	}
}
