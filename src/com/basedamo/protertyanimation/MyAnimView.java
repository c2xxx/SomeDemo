package com.basedamo.protertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * @author hui
 * 
 */
public class MyAnimView extends View {

	public static final float RADIUS = 50f;

	private String color;

	private Point currentPoint;

	private Paint mPaint;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		mPaint.setColor(Color.parseColor(color));
		invalidate();
	}

	public MyAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLUE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (currentPoint == null) {
			currentPoint = new Point(RADIUS, RADIUS);
			drawCircle(canvas);
		} else {
			drawCircle(canvas);
		}
	}

	private void drawCircle(Canvas canvas) {
		float x = currentPoint.getX();
		float y = currentPoint.getY();
		canvas.drawCircle(x, y, RADIUS, mPaint);
	}

	/**
	 * 位置从左上角到右下角
	 */
	public void startAnimation() {
		Point startPoint = new Point(RADIUS, RADIUS);
		Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
		ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),
				startPoint, endPoint);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPoint = (Point) animation.getAnimatedValue();
				invalidate();
			}
		});
		anim.setDuration(3000);
		anim.start();
	}

	/**
	 * 位置从左上角到右下角，且变色
	 */
	public void startAnimation2() {
		Point startPoint = new Point(RADIUS, RADIUS);
		Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
		ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),
				startPoint, endPoint);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPoint = (Point) animation.getAnimatedValue();
				invalidate();
			}
		});
		ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color",
				new ColorEvaluator(), "#0000FF", "#FF0000");
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim).with(anim2);
		animSet.setDuration(3000);
		animSet.start();
	}

	/**
	 * 垂直掉落,加速后减速
	 */
	public void startAnimation_vertical() {
		Point startPoint = new Point(getWidth() / 2, RADIUS);
		Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
		ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),
				startPoint, endPoint);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPoint = (Point) animation.getAnimatedValue();
				invalidate();
			}
		});
		anim.setDuration(2000);
		anim.start();
	}
	/**
	 * 垂直掉落,加速下落
	 */
	public void startAnimation_vertical2() {
	    Point startPoint = new Point(getWidth() / 2, RADIUS);
	    Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
	    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
	    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	        @Override
	        public void onAnimationUpdate(ValueAnimator animation) {
	            currentPoint = (Point) animation.getAnimatedValue();
	            invalidate();
	        }
	    });
	    anim.setInterpolator(new AccelerateInterpolator(2f));
	    anim.setDuration(2500);
	    anim.start();
	}
	/**
	 * 垂直掉落后反弹
	 */
	public void startAnimation_vertical3() {
	    Point startPoint = new Point(getWidth() / 2, RADIUS);
	    Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
	    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
	    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	        @Override
	        public void onAnimationUpdate(ValueAnimator animation) {
	            currentPoint = (Point) animation.getAnimatedValue();
	            invalidate();
	        }
	    });
	    anim.setInterpolator(new BounceInterpolator());  
	    anim.setDuration(3000);  
	    anim.start();
	}
	/**
	 * 自定义加速后减速
	 */
	public void startAnimation_vertical4() {
	    Point startPoint = new Point(getWidth() / 2, RADIUS);
	    Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
	    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
	    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	        @Override
	        public void onAnimationUpdate(ValueAnimator animation) {
	            currentPoint = (Point) animation.getAnimatedValue();
	            invalidate();
	        }
	    });
	    anim.setInterpolator(new DecelerateAccelerateInterpolator());
	    anim.setDuration(3000);
	    anim.start();
	}
}