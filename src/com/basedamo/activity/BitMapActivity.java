package com.basedamo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.chen.utils.BitMapAccess;
import com.chen.utils.FastBlur;

/**
 * 
 * @author Author CH
 * @version CreateTime 2015-6-11 上午10:56:30
 */
public class BitMapActivity extends BaseActivity {
	private ImageView iv_bitmap;
	private SeekBar sb_seekbar;
	BitMapAccess ba = new BitMapAccess();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.at_bitmap);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initViews() {
		sb_seekbar = (SeekBar) this.findViewById(R.id.sb_seekbar);
		iv_bitmap = (ImageView) this.findViewById(R.id.iv_bitmap);
		iv_bitmap.setImageResource(R.drawable.bg);
	}

	@Override
	protected void initData() {

	}

	// 亮度
	public void bt_light(View v) {
		Bitmap bm = getBitMap();
		Bitmap newB = ba.convertToBlur(bm, sb_seekbar.getProgress());
		iv_bitmap.setImageBitmap(newB);

	}

	// 得到图片
	private Bitmap getBitMap() {
		Drawable drawable = iv_bitmap.getDrawable();
		Bitmap bm = ba.drawable2Bitmap(drawable);
		if (bm == null) {
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		}
		return bm;
	}

	// 模糊
	public void bt_blur(View v) {
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		bm = blur(bm, sb_seekbar.getProgress());
		iv_bitmap.setImageBitmap(bm);
	}

	private Bitmap blur(Bitmap bitmap, int radius) {
		Bitmap overlay = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.drawBitmap(bitmap, 0, 0, new Paint());
		return FastBlur.doBlur(overlay, radius, true);
	}

	// 圆角
	public void bt_conner(View v) {
		Drawable drawable = iv_bitmap.getDrawable();
		Bitmap bm = ba.drawable2Bitmap(drawable);
		Bitmap newB = ba.convertToRoundedCorner(bm,
				sb_seekbar.getProgress() * 3);
		iv_bitmap.setImageBitmap(newB);
	}

	// 黑白
	public void bt_black_white(View v) {
		Drawable drawable = iv_bitmap.getDrawable();
		Bitmap bm = ba.drawable2Bitmap(drawable);
		Bitmap newB = ba.convertToBlackWhite(bm);
		iv_bitmap.setImageBitmap(newB);
	}

	// 重新加载
	public void bt_reload(View v) {
		iv_bitmap.setImageResource(R.drawable.bg);
	}

}
