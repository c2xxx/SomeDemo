package com.basedamo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basedamo.R;

public class MyViewDemo extends LinearLayout {

	public MyViewDemo(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 在构造函数中将Xml中定义的布局解析出来。
		LayoutInflater.from(context).inflate(R.layout.view_myview, this, true);
		init();
	}

	private void init() {
		TextView tv_bbb=(TextView) this.findViewById(R.id.tv_bbb);
		tv_bbb.setText(tv_bbb.getText()+"_"+this.getClass().getSimpleName());
	}

}
