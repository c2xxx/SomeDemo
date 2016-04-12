package com.basedamo.test;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author:TangYihang
 * Date:2014年12月8日 11:44:38
 * Describe:
 */
public class ColorPickerView extends View {
    private Context mContext;
    private Bitmap mBitmap;
    private Paint mPaint;
    private int mHeight;
    private int mWidth;

    private int[] mColors;
    private int LEFT_WIDTH;
    private Bitmap mPickerBitmap;
    private Bitmap mPickerBitmap2;
    private Paint mBitmapPaint;
    private Paint mCirclePaint;//画圆的画笔
    private PointF mLeftSelectPoint;
    private boolean mLeftMove = false;
    private float mLeftBitmapRadius;
    private Bitmap bitmapTemp;
    private Bitmap mGradualChangeBitmap;
    int newWidgth;
    int newHeight;
    public int Circlecolor;
    String getColor;
    String getR;
    String getG;
    String getB;

    public ColorPickerView(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setBitmap(Bitmap bitmap) {   //底层图片
        bitmapTemp = bitmap;
        newHeight = bitmapTemp.getHeight();
        newWidgth = bitmapTemp.getWidth();
        postInvalidate();
    }

    private void init() {
        // TODO Auto-generated method stub
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);  //画笔宽度为一
        mColors = new int[3];
        mColors[0] = Color.WHITE; //始点颜色
        mColors[2] = Color.BLACK; //重点颜色
        mBitmapPaint = new Paint();
        mCirclePaint = new Paint(); //圆的画笔
        mPickerBitmap = BitmapFactory.decodeResource(mContext.getResources(), 000000);
        mPickerBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), 000000); //小圆圈
        mLeftBitmapRadius = mPickerBitmap.getWidth() / 2;  //图片左边半径
        mLeftSelectPoint = new PointF(0, 0);  //图片初始的坐标点
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        float x = event.getX();//获取相对于View的坐标 ，而getRawX()则是绝对坐标（相对于屏幕）
        float y = event.getY() + 200;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                getColor = getLeftColor(x, y);

                mLeftMove = true;
                proofLeft(x, y);
                invalidate(); //刷新view
                break;
            case MotionEvent.ACTION_UP:
                getColor = getLeftColor(x, y);
                mColorPickerListener.colorChanged(mCirclePaint.getColor());
                mColorPickerListener.colorPickeColor(getColor);
                mColorPickerListener.colorPickeRed(getR);
                mColorPickerListener.colorPickeGreen(getG);
                mColorPickerListener.colorPickeBlue(getB);
                mLeftMove = false;
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    OnColorPickerListener mColorPickerListener;

    public void setOnColorPickerListener(OnColorPickerListener listener) {
        mColorPickerListener = listener;
    }

    public interface OnColorPickerListener {
        public void colorPickeColor(String Color);

        public void colorChanged(int color);

        public void colorPickeRed(String Red);

        public void colorPickeGreen(String Green);

        public void colorPickeBlue(String Blue);
    }

    //View的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = newWidgth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = newHeight;
        }
        LEFT_WIDTH = mWidth;
        setMeasuredDimension(mWidth, mHeight);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.drawBitmap(getGradual(), null, new Rect(0, 0, newWidgth, newHeight), mBitmapPaint);  //底层图片
        if (mLeftMove) {   //懂的时候不显示颜色
            mCirclePaint.setColor(Circlecolor); //小圆圈
            canvas.drawCircle(mLeftSelectPoint.x,
                    (mLeftSelectPoint.y - mLeftBitmapRadius) - 100, (mPickerBitmap2.getWidth() / 2) + 5, mCirclePaint);

            canvas.drawBitmap(mPickerBitmap, mLeftSelectPoint.x - mLeftBitmapRadius,
                    mLeftSelectPoint.y - mLeftBitmapRadius - mLeftBitmapRadius - (mLeftBitmapRadius * (2 / 4)) - 100, mBitmapPaint); //定位的取色图标
        } else {
            try {
                mCirclePaint.setColor(Circlecolor); //小圆圈
                canvas.drawCircle(mLeftSelectPoint.x,
                        (mLeftSelectPoint.y - mLeftBitmapRadius) - 100, (mPickerBitmap2.getWidth() / 2) + 5, mCirclePaint);
                canvas.drawBitmap(mPickerBitmap, mLeftSelectPoint.x - mLeftBitmapRadius,
                        mLeftSelectPoint.y - mLeftBitmapRadius - mLeftBitmapRadius - (mLeftBitmapRadius * (2 / 4)) - 100, mBitmapPaint);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        if (mGradualChangeBitmap != null && mGradualChangeBitmap.isRecycled() == false) {
            mGradualChangeBitmap.recycle();
        }
        if (mPickerBitmap != null && mPickerBitmap.isRecycled() == false) {
            mPickerBitmap.recycle();
        }
        if (mPickerBitmap2 != null && mPickerBitmap2.isRecycled() == false) {
            mPickerBitmap2.recycle();
        }
        super.onDetachedFromWindow();
    }

    private String getLeftColor(float x, float y) {
        // TODO Auto-generated method stub
        Bitmap temp = getGradual();

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
        System.out.println("color=" + temp.getPixel(intX, intY));  //getPixel获取像素
        int rgb = temp.getPixel(intX, intY);
        Circlecolor = rgb;

        getR = Integer.toHexString(Color.red(rgb)); //16进制的转换
        getG = Integer.toHexString(Color.green(rgb));
        getB = Integer.toHexString(Color.blue(rgb));
        return "#" + Integer.toHexString(temp.getPixel(intX, intY));
    }

    private Bitmap getGradual() {
        // TODO Auto-generated method stub
        if (mGradualChangeBitmap == null) {
            Paint leftPaint = new Paint();
            leftPaint.setStrokeWidth(1);
            mGradualChangeBitmap = Bitmap.createBitmap(newWidgth, mHeight, Config.RGB_565);
            mGradualChangeBitmap.eraseColor(Color.WHITE);
            Canvas canvas = new Canvas(mGradualChangeBitmap);
            canvas.drawBitmap(bitmapTemp, null, new Rect(0, 0, newWidgth, mHeight), mBitmapPaint);
        }

        return mGradualChangeBitmap;
    }

    private void proofLeft(float x, float y) {
        // TODO Auto-generated method stub
        if (x < 0) {
            mLeftSelectPoint.x = 0;
        } else if (x > (newWidgth)) {
            mLeftSelectPoint.x = newWidgth;
        } else {
            mLeftSelectPoint.x = x;
        }

        if (y < 0) {
            mLeftSelectPoint.y = 0;
        } else if (y > (newHeight - 0)) {
            mLeftSelectPoint.y = newHeight - 0;
        } else {
            mLeftSelectPoint.y = y;
        }
    }
}
