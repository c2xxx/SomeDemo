package com.basedamo.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.basedamo.R;

public class ProteryAnimation1 extends Activity implements OnClickListener {
	private Button btn_property_scale;
	private Button btn_property_alpha;
	private Button btn_property_translationX;
	private Button btn_property_rotation;
	private Button tv_property;
	private ValueAnimator va;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proteryanimation1);
		initViews();
		initDates();
	}

	private void initDates() {
	}

	private void initViews() {
		btn_property_scale = (Button) findViewById(R.id.btn_property_scale);
		btn_property_alpha = (Button) findViewById(R.id.btn_property_alpha);
		btn_property_translationX = (Button) findViewById(R.id.btn_property_translationX);
		btn_property_rotation = (Button) findViewById(R.id.btn_property_rotation);
		tv_property = (Button) findViewById(R.id.tv_property);
		btn_property_alpha.setOnClickListener(this);
		btn_property_scale.setOnClickListener(this);
		btn_property_translationX.setOnClickListener(this);
		btn_property_rotation.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (va != null) {
			va.cancel();
		}
		switch (v.getId()) {
		case R.id.btn_property_scale:
			va = ObjectAnimator.ofFloat(tv_property, "scaleY", 1f, 3f, 1f);
			va.setDuration(5000);
			va.start();
			break;
		case R.id.btn_property_alpha:
			va = ObjectAnimator.ofFloat(tv_property, "alpha", 1f, 0f, 1f);
			va.setDuration(5000);
			va.start();
			break;
		case R.id.btn_property_translationX:
			float curTranslationX = v.getTranslationX();
			va = ObjectAnimator.ofFloat(tv_property, "translationX",
					curTranslationX, -200f, curTranslationX);
			va.setDuration(5000);
			va.start();
			va = ObjectAnimator.ofFloat(tv_property, "translationY",
					curTranslationX, -100f, curTranslationX);
			va.setDuration(5000);
			va.start();
			break;
		case R.id.btn_property_rotation:
			va = ObjectAnimator.ofFloat(tv_property, "rotation", 0f, 360f);
			va.setDuration(5000);
			va.start();
			break;
		}
	}
}
