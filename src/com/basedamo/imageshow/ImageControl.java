package com.basedamo.imageshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageControl   extends ImageView {
	public ImageControl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ImageControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ImageControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// ImageView img;
	Matrix imgMatrix = null; // ����ͼƬ�ı任����

	static final int DOUBLE_CLICK_TIME_SPACE = 300; // ˫��ʱ����
	static final int DOUBLE_POINT_DISTANCE = 10; // ����Ŵ��������С���
	static final int NONE = 0;
	static final int DRAG = 1; // �϶�����
	static final int ZOOM = 2; // �Ŵ���С����
	private int mode = NONE; // ��ǰģʽ

	float bigScale = 3f; // Ĭ�ϷŴ���
	Boolean isBig = false; // �Ƿ��ǷŴ�״̬
	long lastClickTime = 0; // ����ʱ��
	float startDistance; // ��㴥���������
	float endDistance; // ��㴥���������

	float topHeight; // ״̬���߶Ⱥͱ������߶�
	Bitmap primaryBitmap = null;

	float contentW; // ��Ļ���������
	float contentH; // ��Ļ�������߶�

	float primaryW; // ԭͼ���
	float primaryH; // ԭͼ�߶�

	float scale; // �ʺ���Ļ���ű���
	Boolean isMoveX = true; // �Ƿ�������X���϶�
	Boolean isMoveY = true; // �Ƿ�������Y���϶�
	float startX;
	float startY;
	float endX;
	float endY;
	float subX;
	float subY;
	float limitX1;
	float limitX2;
	float limitY1;
	float limitY2;
	ICustomMethod mCustomMethod = null;

	/**
	 * ��ʼ��ͼƬ
	 * 
	 * @param bitmap
	 *            Ҫ��ʾ��ͼƬ
	 * @param contentW
	 *            ����������
	 * @param contentH
	 *            ��������߶�
	 * @param topHeight
	 *            ״̬���߶Ⱥͱ������߶�֮��
	 */
	public void imageInit(Bitmap bitmap, int contentW, int contentH,
			int topHeight, ICustomMethod iCustomMethod) {
		this.primaryBitmap = bitmap;
		this.contentW = contentW;
		this.contentH = contentH;
		this.topHeight = topHeight;
		mCustomMethod = iCustomMethod;
		primaryW = primaryBitmap.getWidth();
		primaryH = primaryBitmap.getHeight();
		float scaleX = (float) contentW / primaryW;
		float scaleY = (float) contentH / primaryH;
		scale = scaleX < scaleY ? scaleX : scaleY;
		if (scale < 1 && 1 / scale < bigScale) {
			bigScale = (float) (1 / scale + 0.5);
		}

		imgMatrix = new Matrix();
		subX = (contentW - primaryW * scale) / 2;
		subY = (contentH - primaryH * scale) / 2;
		this.setImageBitmap(primaryBitmap);
		this.setScaleType(ScaleType.MATRIX);
		imgMatrix.postScale(scale, scale);
		imgMatrix.postTranslate(subX, subY);
		this.setImageMatrix(imgMatrix);
	}

	/**
	 * ���²���
	 * 
	 * @param event
	 */
	public void mouseDown(MotionEvent event) {
		mode = NONE;
		startX = event.getRawX();
		startY = event.getRawY();
		if (event.getPointerCount() == 1) {
			// ������ε��ʱ����С��һ��ֵ����Ĭ��Ϊ˫���¼�
			if (event.getEventTime() - lastClickTime < DOUBLE_CLICK_TIME_SPACE) {
				changeSize(startX, startY);
			} else if (isBig) {
				mode = DRAG;
			}
		}

		lastClickTime = event.getEventTime();
	}

	/**
	 * �ǵ�һ���㰴�²���
	 * 
	 * @param event
	 */
	public void mousePointDown(MotionEvent event) {
		startDistance = getDistance(event);
		if (startDistance > DOUBLE_POINT_DISTANCE) {
			mode = ZOOM;
		} else {
			mode = NONE;
		}
	}

	/**
	 * �ƶ�����
	 * 
	 * @param event
	 */
	public void mouseMove(MotionEvent event) {
		if ((mode == DRAG) && (isMoveX || isMoveY)) {
			float[] XY = getTranslateXY(imgMatrix);
			float transX = 0;
			float transY = 0;
			if (isMoveX) {
				endX = event.getRawX();
				transX = endX - startX;
				if ((XY[0] + transX) <= limitX1) {
					transX = limitX1 - XY[0];
				}
				if ((XY[0] + transX) >= limitX2) {
					transX = limitX2 - XY[0];
				}
			}
			if (isMoveY) {
				endY = event.getRawY();
				transY = endY - startY;
				if ((XY[1] + transY) <= limitY1) {
					transY = limitY1 - XY[1];
				}
				if ((XY[1] + transY) >= limitY2) {
					transY = limitY2 - XY[1];
				}
			}

			imgMatrix.postTranslate(transX, transY);
			startX = endX;
			startY = endY;
			this.setImageMatrix(imgMatrix);
		} else if (mode == ZOOM && event.getPointerCount() > 1) {
			endDistance = getDistance(event);
			float dif = endDistance - startDistance;
			if (Math.abs(endDistance - startDistance) > DOUBLE_POINT_DISTANCE) {
				if (isBig) {
					if (dif < 0) {
						changeSize(0, 0);
						mode = NONE;
					}
				} else if (dif > 0) {
					float x = event.getX(0) / 2 + event.getX(1) / 2;
					float y = event.getY(0) / 2 + event.getY(1) / 2;
					changeSize(x, y);
					mode = NONE;
				}
			}
		}
	}

	/**
	 * ���̧���¼�
	 */
	public void mouseUp() {
		mode = NONE;
	}

	/**
	 * ͼƬ�Ŵ���С
	 * 
	 * @param x
	 *            �����X����
	 * @param y
	 *            �����Y����
	 */
	private void changeSize(float x, float y) {
		if (isBig) {
			// ����������״̬����ԭ
			imgMatrix.reset();
			imgMatrix.postScale(scale, scale);
			imgMatrix.postTranslate(subX, subY);
			isBig = false;
		} else {
			imgMatrix.postScale(bigScale, bigScale); // ��ԭ�о����˷Ŵ���
			float transX = -((bigScale - 1) * x);
			float transY = -((bigScale - 1) * (y - topHeight)); // (bigScale-1)(y-statusBarHeight-subY)+2*subY;
			float currentWidth = primaryW * scale * bigScale; // �Ŵ��ͼƬ��С
			float currentHeight = primaryH * scale * bigScale;
			// ���ͼƬ�Ŵ�󳬳���Ļ��Χ����
			if (currentHeight > contentH) {
				limitY1 = -(currentHeight - contentH); // ƽ������
				limitY2 = 0;
				isMoveY = true; // ������Y�����϶�
				float currentSubY = bigScale * subY; // ��ǰƽ�ƾ���
				// ƽ�ƺ����������ϲ��пհ״���취
				if (-transY < currentSubY) {
					transY = -currentSubY;
				}
				// ƽ�ƺ����������²��пհ״���취
				if (currentSubY + transY < limitY1) {
					transY = -(currentHeight + currentSubY - contentH);
				}
			} else {
				// ���ͼƬ�Ŵ��û�г�����Ļ��Χ�����������϶�
				isMoveY = false;
			}

			if (currentWidth > contentW) {
				limitX1 = -(currentWidth - contentW);
				limitX2 = 0;
				isMoveX = true;
				float currentSubX = bigScale * subX;
				if (-transX < currentSubX) {
					transX = -currentSubX;
				}
				if (currentSubX + transX < limitX1) {
					transX = -(currentWidth + currentSubX - contentW);
				}
			} else {
				isMoveX = false;
			}

			imgMatrix.postTranslate(transX, transY);
			isBig = true;
		}

		this.setImageMatrix(imgMatrix);
		if (mCustomMethod != null) {
			mCustomMethod.customMethod(isBig);
		}
	}

	/**
	 * ��ȡ�任������X��ƫ������Y��ƫ����
	 * 
	 * @param matrix
	 *            �任����
	 * @return
	 */
	private float[] getTranslateXY(Matrix matrix) {
		float[] values = new float[9];
		matrix.getValues(values);
		float[] floats = new float[2];
		floats[0] = values[Matrix.MTRANS_X];
		floats[1] = values[Matrix.MTRANS_Y];
		return floats;
	}

	/**
	 * ��ȡ�����ľ���
	 * 
	 * @param event
	 * @return
	 */
	private float getDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * @author Administrator �û��Զ��巽��
	 */
	public interface ICustomMethod {
		public void customMethod(Boolean currentStatus);
	}
}