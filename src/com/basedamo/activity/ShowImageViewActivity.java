package com.basedamo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.R.id;

/**
 * 图片展示
 *
 * @author hui
 */
public class ShowImageViewActivity extends BaseActivity {

    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_image_view);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        photoView = (ImageView) findViewById(id.photoview);
        photoView.setImageResource(R.drawable.photo);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseImageViewResouce(photoView);
    }

    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
}
