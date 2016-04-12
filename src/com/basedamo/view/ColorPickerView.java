package com.basedamo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.basedamo.R;


/**
 * 颜色选择器
 * Created by ChenHui on 2016/4/11.
 */
public class ColorPickerView extends View {
    private Bitmap mPickerBitmap;//预览
    private Bitmap mPickerBitmap_bg;//背景
    private float mPickerBitmapWidth;
    private Context mContext;
    private float mCurrentX;
    private int selectedColor = Color.BLACK;//选中的颜色
    private OnPickColorListener onPickColorListener;//选择颜色监听

    /**
     * @param color 起始颜色
     */
    public void setStartColor(int color) {
        this.selectedColor = color;
    }


    public OnPickColorListener getOnPickColorListener() {
        return onPickColorListener;
    }

    public void setOnPickColorListener(OnPickColorListener onPickColorListener) {
        this.onPickColorListener = onPickColorListener;
    }

    public ColorPickerView(Context context) {
        super(context);
        init();
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPickerBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pickcolor_draw);
        mPickerBitmap_bg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pickcolor_bg);
//        mPickerBitmap_bg = scale(mPickerBitmap_bg, DensityUtil.dip2px(mContext, 400), DensityUtil.dip2px(mContext, 50));
        mPickerBitmapWidth = mPickerBitmap.getWidth();  //图片左边半径
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        int radius = (int) (mPickerBitmapWidth * 0.35f);
        canvas.drawBitmap(mPickerBitmap_bg, 0, mPickerBitmap.getHeight(), paint);
        paint.setColor(Color.RED);
        paint.setColor(selectedColor);
        canvas.drawCircle(mCurrentX, mPickerBitmapWidth / 2, radius, paint);

        canvas.drawBitmap(mPickerBitmap, mCurrentX - mPickerBitmapWidth / 2, 0, paint);
    }

    private Bitmap scale(Bitmap bitmap, int width, int height) {
        Matrix matrix = new Matrix();
        float scaleW = width * 1.0f / bitmap.getWidth();
        float scaleH = height * 1.0f / bitmap.getHeight();
        matrix.postScale(scaleW, scaleH); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        mCurrentX = Math.max(x, 0);
        mCurrentX = Math.min(mCurrentX, mPickerBitmap_bg.getWidth());
        selectedColor = getLeftColor(x, y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (onPickColorListener != null) {
                    onPickColorListener.onChangedColor(selectedColor);
                }
                invalidate();
                break;
        }
        return true;
    }


    private int getLeftColor(float x, float y) {
        Bitmap temp = mPickerBitmap_bg;
        int intX = (int) x;
        int intY = (int) y;
        if (intX < 0) intX = 0;
        if (intY < 0) intY = 0;
        if (intX >= temp.getWidth()) {
            intX = temp.getWidth() - 1;
        }
        if (intY >= temp.getHeight()) {
            intY = temp.getHeight() - 1;
        }
        int rgb = temp.getPixel(intX, intY);
        return rgb;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = right - left;
        if (width > 0 && mPickerBitmap_bg.getWidth() != width) {
            mPickerBitmap_bg = scale(mPickerBitmap_bg, width, bottom - top);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mPickerBitmap != null && mPickerBitmap.isRecycled() == false) {
            mPickerBitmap.recycle();
        }
        if (mPickerBitmap_bg != null && mPickerBitmap_bg.isRecycled() == false) {
            mPickerBitmap_bg.recycle();
        }
        super.onDetachedFromWindow();
    }

    public interface OnPickColorListener {
        void onChangedColor(int newColor);
    }
}
