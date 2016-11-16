package com.yazao.android.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.yazao.android.view.R;

/**
 * Created by shaopingzhai on 15/11/24.
 */
public class RotationView extends View {

	/**
	 * 宽度
	 */
	private int width = 0;
	/**
	 * 高度
	 */
	private int height = 0;
	/**
	 * 外层圆形的画笔
	 */
	private Paint mPaintCircleOut;
	/**
	 * 内层圆形的画笔
	 */
	private Paint mPaintCircleIn;
	/**
	 * 外层圆形的半径
	 */
	private float mRadiusOut;
	/**
	 * 内层圆形的半径
	 */
	private float mRadiusIn;
	/**
	 * 外层圆形的颜色
	 */
	private int mColorCircleOut = Color.parseColor("#C8C845");
	/**
	 * 内层圆形的颜色
	 */
	private int mColorCircleIn = Color.parseColor("#3E3E3E");
	/**
	 * 外层圆形画笔的宽度
	 */
	private float mStrokeWidthCircleOut = 1.0f;
	/**
	 * 内层圆形画笔的宽度
	 */
	private float mStrokeWidthCircleIn = 8.0f;

	private Bitmap mBitmap;
	private Paint mPaintBitmap;
	private float mRectBitmap;
	private RectF mRectFBitmap;
	private int SAVE_FLAG = Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
			| Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG;

	/**
	 * 内层圆的 范围
	 */
	private RectF mRectFBitmapBound;
	/**
	 * 内层圆的 padding
	 */
	private float mBitmapPadding;


	private RotationViewAnim anim;


	public RotationView(Context context) {
		this(context, null);
	}

	public RotationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RotationView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public RotationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {

		anim = new RotationViewAnim();
		anim.setRepeatCount(Animation.INFINITE);
		anim.setInterpolator(new LinearInterpolator());

		mBitmapPadding = 10.0f;

		//设置默认view的 宽高
		width = 400;
		height = 400;

		mPaintBitmap = new Paint();
		mPaintBitmap.setAntiAlias(true);
		mPaintBitmap.setColor(Color.RED);
		mPaintBitmap.setDither(true);
		mPaintBitmap.setStrokeWidth(mStrokeWidthCircleOut);
		mPaintBitmap.setStyle(Paint.Style.FILL);

		mPaintCircleOut = new Paint();
		mPaintCircleOut.setAntiAlias(true);
		mPaintCircleOut.setColor(mColorCircleOut);
		mPaintCircleOut.setDither(true);
		mPaintCircleOut.setStrokeWidth(mStrokeWidthCircleOut);
		mPaintCircleOut.setStyle(Paint.Style.FILL);

		mPaintCircleIn = new Paint();
		mPaintCircleIn.setAntiAlias(true);
		mPaintCircleIn.setColor(mColorCircleIn);
		mPaintCircleIn.setDither(true);
		mPaintCircleIn.setStrokeWidth(mStrokeWidthCircleIn);
		mPaintCircleIn.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		// UNSPECIFIED 不考虑 ,但是要考虑 子元素的padding
		if (modeWidth == MeasureSpec.AT_MOST && modeHeight == MeasureSpec.AT_MOST) {
			setMeasuredDimension(width, height);
		} else if (modeWidth == MeasureSpec.AT_MOST) {
			setMeasuredDimension(width, sizeHeight);
		} else if (modeHeight == MeasureSpec.AT_MOST) {
			setMeasuredDimension(sizeWidth, height);
		}


		height = getMeasuredHeight();
		width = getMeasuredWidth();
		mRadiusOut = Math.min((width - -getPaddingLeft() - getPaddingRight()) / 2, (height - getPaddingTop() - getPaddingBottom()) / 2);
		mRadiusIn = mRadiusOut / 2 - mStrokeWidthCircleIn;


		float left = getPaddingLeft() + mRadiusOut / 2 + mStrokeWidthCircleIn;
//		float left = (width - -getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft() - mRadiusIn;
		float top = getPaddingTop() + mRadiusOut / 2 + mStrokeWidthCircleIn;
//		float top = (height - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop() - mRadiusIn;


		int delt = Math.abs(width - height);

		//判断width 和 height 的 那个小那个大
		if (width >= height) {
			//宽比较长
			left = left + delt / 2;
		} else {
			//高比较长
			top = top + delt / 2;
		}

		float right = left + 2 * mRadiusIn;
		float bottom = top + 2 * mRadiusIn;

		mRectFBitmap = new RectF(left + mBitmapPadding, top + mBitmapPadding, right - mBitmapPadding, bottom - mBitmapPadding);
//		mRectFBitmapBound = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
		mRectFBitmapBound = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
		getBitmap(R.drawable.aa, (int) (2 * mRadiusIn), (int) (2 * mRadiusIn));
		Log.i("YaZao", "width= " + width + ", height= " + height + ",mRadiusOut= " + mRadiusOut + ",mRadiusIn= "
				+ mRadiusIn + ", padLeft= " + getPaddingLeft() + ",padTop= " + getPaddingTop() + ",padRight= " + getPaddingRight() + ",padBottom= " + getPaddingBottom());
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//1. 绘制 圆形（淡黄色）背景色
		canvas.drawCircle(width / 2, height / 2, mRadiusOut, mPaintCircleOut);
		canvas.drawCircle(width / 2, height / 2, mRadiusIn, mPaintCircleIn);

		canvas.saveLayerAlpha(mRectFBitmapBound, 255, SAVE_FLAG);

		mPaintBitmap.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

		canvas.drawBitmap(mBitmap, null, mRectFBitmap, mPaintBitmap);
		canvas.restore();

		//属性动画
//		ObjectAnimator rotation = ObjectAnimator.ofFloat(mBitmap, "rotation", 0, 360);
//		rotation.setRepeatCount(ObjectAnimator.INFINITE);
//		rotation.setRepeatMode(ObjectAnimator.RESTART);
//		rotation.start();

		//view动画
//		RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, 0.5f, 0.5f);
//		rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
//		rotateAnimation.setInterpolator(new LinearInterpolator());
//		startAnimation(mBitmap);

	}

	public void startAnim(RotationView rotationView) {
		//view动画
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setRepeatCount(2);
//		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotationView.startAnimation(rotateAnimation);

		TranslateAnimation anim = new TranslateAnimation(0, 300, 0, 300);
		anim.setDuration(2000);
		anim.setFillAfter(false);

		rotationView.startAnimation(anim);
	}


	private void getBitmap(int resId, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		mBitmap = BitmapFactory.decodeResource(getResources(), resId, options);
//		Log.i("YaZao", "mBitmap1 : width= " + mBitmap.getWidth() + ",height= " + mBitmap.getHeight());

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		mBitmap = BitmapFactory.decodeResource(getResources(), resId, options);
		Log.i("YaZao", "mBitmap2 : width= " + mBitmap.getWidth() + ",height= " + mBitmap.getHeight() + "inSampleSize= " + options.inSampleSize);
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;

		if (width > reqWidth || height > reqHeight) {
			int halfWidth = width / 2;
			int halfHeight = height / 2;
			while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * 动画开始
	 */
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

	}

	/**
	 * 动画停止
	 */
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

	}


	class RotationViewAnim extends Animation {

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);


		}
	}
}
