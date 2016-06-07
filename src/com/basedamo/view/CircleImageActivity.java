package com.basedamo.view;

import com.basedamo.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class CircleImageActivity extends Activity {
	private CircleImageView civ_test;
	private CircleImageView civ_test2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_img);
//		initViews();
//		initData();
	}

	private void initData() {
		civ_test.setBackgroundResource(R.drawable.bg);
		for (int i = 0; i < 1000; i++) {//看效率
			civ_test2.setImageResource(R.drawable.bg);
		}
		civ_test2.setBackgroundColor(0x55ddcc00);;
	}

	private void initViews() {
		civ_test = (CircleImageView) this.findViewById(R.id.civ_test);
		civ_test2 = (CircleImageView) this.findViewById(R.id.civ_test2);
	}

}
