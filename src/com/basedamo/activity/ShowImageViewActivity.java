package com.basedamo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.basedamo.R;
import com.basedamo.R.id;
import com.basedamo.R.layout;
import com.basedamo.imageshow.ImageControl;
import com.basedamo.imageshow.ImageControl.ICustomMethod; 

/**
 * Õº∆¨’π æ
 * @author hui
 *
 */
public class ShowImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_image_view);
		findView();
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		init();
	}

	ImageControl imgControl;
	private void findView() {
		imgControl = (ImageControl) findViewById(id.common_imageview_imageControl1);
	}

	private void init() {		
		Bitmap bmp;
		if (imgControl.getDrawingCache() != null) {
			bmp = Bitmap.createBitmap(imgControl.getDrawingCache());
		} else {
			bmp = ((BitmapDrawable) imgControl.getDrawable()).getBitmap();
		}
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
		int screenH = this.getWindowManager().getDefaultDisplay().getHeight()
				- statusBarHeight;
		if (bmp != null) {
			imgControl.imageInit(bmp, screenW, screenH, statusBarHeight,
					null);
		}
		else
		{
			Toast.makeText(ShowImageViewActivity.this, "Õº∆¨º”‘ÿ ß∞‹£¨«Î…‘∫Ú‘Ÿ ‘£°", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
