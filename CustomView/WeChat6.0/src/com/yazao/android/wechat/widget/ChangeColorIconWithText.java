package com.yazao.android.wechat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yazao.android.wechat.R;

/**
 * 
 * com.yazao.android.wechat.widget.ChangeColorIconWithText
 * 
 * @description:
 * @author yueliangrensheng create at 2015年10月22日下午1:56:04
 */
public class ChangeColorIconWithText extends View {
	/** View的颜色 */
	private int mColor = 0xFF45C01A;
	/** View的图标 */
	private Bitmap mIconBitmap;
	/** View的文字内容 */
	private String mText = "WeChat";
	/** View的文字大小 */
	private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f,
			getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;
	/** 透明度 */
	private float mAlpha;
	/** icon的范围 */
	private Rect mIconRect;
	/** 文字的范围 */
	private Rect mTextBound;
	/** 文字的画笔 */
	private Paint mTextPaint;

	public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// 获取自定义属性的值
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);
		mColor = a.getColor(R.styleable.ChangeColorIconWithText_color, 0xFF45C01A);
		mText = a.getString(R.styleable.ChangeColorIconWithText_text);
		mTextSize = (int) a.getDimension(R.styleable.ChangeColorIconWithText_text_Size, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
						getResources().getDisplayMetrics()));
		BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.ChangeColorIconWithText_icon);
		mIconBitmap = drawable.getBitmap();

		a.recycle();

		init();
	}

	public ChangeColorIconWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChangeColorIconWithText(Context context) {
		this(context, null);
	}

	private void init() {
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setColor(0xff555555);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// Log.i("YaZao", "MeasuredWidth= "+ getMeasuredWidth()
		// +",MeasuredHeight= "+ getMeasuredHeight());

		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
				getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (iconWidth + mTextBound.height()) / 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);// Icon是正方形

	}

	@Override
	protected void onDraw(Canvas canvas) {
		//1. 绘制 白色的icon（即 原图）
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

		int alpha = (int) Math.ceil(255 * mAlpha);

		//2. 在内存中去 准备mBitmap(需要在 其上面绘制
		// 纯色，在绘制纯色之前，需要设置setAlpha),再绘制图标，在绘制图标之前，设置下Xfermode
		setupTargetBitmap(alpha);
		
		//3.绘制 原文本
		drawSourceText(canvas,alpha);
		//4.绘制变色的文本
		drawTargetText(canvas,alpha);
		
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}
	
	/**
	 * 
	 * @description 绘制原文本 
	 * @param canvas
	 * @param alpha
	 * void
	 * @author yueliangrensheng create at 2015年10月23日上午11:10:03
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255-alpha);
		float x=getMeasuredWidth()/2-mTextBound.width()/2;
		float y=mTextBound.height()+mIconRect.bottom;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 
	 * @description 绘制变色的文本 
	 * @param canvas
	 * @param alpha
	 * void
	 * @author yueliangrensheng create at 2015年10月23日上午11:10:19
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		float x=getMeasuredWidth()/2-mTextBound.width()/2;
		float y=mTextBound.height()+mIconRect.bottom;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 
	 * @description 在内存中 绘制可变色的Icon
	 * @param alpha
	 *            void
	 * @author yueliangrensheng create at 2015年10月22日下午6:59:55
	 */
	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);// 绘制纯色区域

		// 设置 xfermode
		mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		mPaint.setAlpha(255);// 恢复
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);// 绘制带有颜色的图标

	}
	
	public void setIconAlpha(float alpha){
		this.mAlpha=alpha;
		//设置了alpha后，需要重新绘制view
		invalidateView();
	}

	private void invalidateView() {
		if (Looper.getMainLooper()==Looper.myLooper()) {
			//在 UI线程里
			invalidate();
		}else{
			//在 非UI线程里
			postInvalidate();
		}
	}
	
	private static final String INSTANCE_STATUS = "instance_status";
	private static final String STATUS_ALPHA = "status_alpha";

	
	@Override
	protected Parcelable onSaveInstanceState() {
		
		Bundle bundle = new Bundle();
		//存储super 所存储的 东西
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putFloat(STATUS_ALPHA, mAlpha);
		return bundle;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		
		if (state instanceof Bundle) {
			Bundle bundle =(Bundle)state;
			mAlpha = bundle.getFloat(STATUS_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			return ;
		}
		super.onRestoreInstanceState(state);
	}

}
