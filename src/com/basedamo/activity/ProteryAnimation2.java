package com.basedamo.activity;

import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;
import com.basedamo.protertyanimation.MyAnimView;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProteryAnimation2 extends Activity implements OnClickListener {
	private Button btn_property2_FloatEvaluator;
	private Button btn_property_objectanimator;
	private MyAnimView mav_test;
	private ValueAnimator va;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proteryanimation2);
		initViews();
		initDates();
	}

	private void initDates() {
	}

	private void initViews() {
		btn_property2_FloatEvaluator = (Button) findViewById(R.id.btn_property2_FloatEvaluator);
		btn_property_objectanimator = (Button) findViewById(R.id.btn_property_objectanimator);
		mav_test = (MyAnimView) findViewById(R.id.mav_test);
		btn_property2_FloatEvaluator.setOnClickListener(this);
		btn_property_objectanimator.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (va != null) {
			va.cancel();
		}
		switch (v.getId()) {
		case R.id.btn_property2_FloatEvaluator:
			mav_test.startAnimation();
			break;
		case R.id.btn_property_objectanimator:
			mav_test.startAnimation2();
			break; 
		}
	}

}
