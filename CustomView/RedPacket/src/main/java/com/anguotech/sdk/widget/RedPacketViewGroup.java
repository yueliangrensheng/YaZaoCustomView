package com.anguotech.sdk.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class RedPacketViewGroup extends LinearLayout {

	/**
	 * 红包左边颜色的画笔
	 */
	private Paint mLeftPaint;
	private Rect mLeftRect;
	/**
	 * 左边的宽度
	 */
	private int LEFT_COLOR_WIDTH = (int) (getContext().getResources().getDisplayMetrics().density * 5 + 0.5f);
	/**
	 * 左边的默认颜色(#D9D9D9)
	 */
	private int COLOR_LEFT = Color.rgb(217,217,217);

	// 左边半圆
	private float radius = 0;

	private int mLeft;

	private int mTop;

	private int mRight;

	private int mBottom;

	private Paint mCirclePaint;

	// 整个白色布局
	private Paint mAllPaint;
	private static final int LAYERS_FLAGS = Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
			| Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG;
	private static final String INSTANCE_STATUS = "ViewOne";
	private static final String VALUE_LEFT_COLOR = "value_left_color";

	/**
	 * viewgroup的默认宽度
	 */
	private int widthDefault = (int) (getContext().getResources().getDisplayMetrics().widthPixels - 20);
	private int heightDefault = (int) (getContext().getResources().getDisplayMetrics().density * 160 + 0.5f);

	public RedPacketViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RedPacketViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RedPacketViewGroup(Context context) {
		this(context, null);
	}

	private void init() {

		setWillNotDraw(false);

		if (!isInEditMode()){
			this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}


		mAllPaint = new Paint();
		mAllPaint.setStyle(Paint.Style.FILL);
		mAllPaint.setAntiAlias(true);
		mAllPaint.setStrokeWidth(2);
		mAllPaint.setColor(Color.WHITE);

		mLeftPaint = new Paint();
		mLeftPaint.setColor(COLOR_LEFT);
		mLeftPaint.setAntiAlias(true);
		mLeftPaint.setDither(true);
		mLeftPaint.setStrokeWidth(2);
		mLeftPaint.setStyle(Paint.Style.FILL);// 实心

		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.RED);
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setDither(true);
		mCirclePaint.setStrokeWidth(2);
		mCirclePaint.setStyle(Paint.Style.FILL);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 子元素的 个数
		int childCount = getChildCount();

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		//处理子view
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		if (childCount == 0) {
			setMeasuredDimension(0, 0);
		} else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthDefault + getPaddingLeft() + getPaddingRight(), heightDefault + getPaddingBottom() + getPaddingTop());
		} else if (widthMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthDefault + getPaddingLeft() + getPaddingRight(), heightSize);
		} else if (heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSize, heightDefault + getPaddingTop() + getPaddingBottom());
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		mLeft = getPaddingLeft();
		mTop = getPaddingTop();
		mRight = mLeft + LEFT_COLOR_WIDTH;
		mBottom = getMeasuredHeight();
		mLeftRect = new Rect(mLeft, mTop, mRight, mBottom);

		// 半圆的直径是 高度/5
		radius = (getMeasuredHeight()) / (2 * 5);


		int childCount = getChildCount();
		int lefts = getPaddingLeft();
		int tops = getPaddingTop();


		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() == View.GONE) {
				continue;
			}
			int lc = lefts;
			int tc = tops;
			int rc = lc + child.getMeasuredWidth();
			int bc = tc + child.getMeasuredHeight();
			child.layout(lc, tc, rc, bc);
			tops += child.getMeasuredHeight();
		}
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// 0.绘制透明布局
		canvas.drawColor(Color.TRANSPARENT);

		// 在调用canvas.saveLayerAlpha 创建一个新图层之后，后续的canvas.drawCircle 都会发生的这个新图层上，
		// canvas.restore() 将这个新图层绘制的图像“复制”到Canvas的缺省图层上
		// 创建一个图层
		canvas.saveLayerAlpha(mLeft, mTop, getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(), 255, LAYERS_FLAGS);
		// 给图层绘制白色背景
		canvas.drawColor(Color.WHITE);
		mLeftPaint.reset();

		// 1.绘制左边颜色带
		mLeftPaint.setColor(COLOR_LEFT);
		mLeftPaint.setAntiAlias(true);
		mLeftPaint.setDither(true);
		mLeftPaint.setStrokeWidth(2);
		mLeftPaint.setStyle(Paint.Style.FILL);// 实心
		canvas.drawRect(mLeftRect, mLeftPaint);
		// 2.设置xfermode
		mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		// 3. 绘制左边的半圆
		canvas.drawCircle(mLeft, 5 * radius, radius, mCirclePaint);

		// 4.绘制右边的半圆
		canvas.drawCircle(getMeasuredWidth()-getPaddingRight(), 5 * radius, radius, mCirclePaint);
		canvas.restore();
	}

	/**
	 * 设置左边的彩色带的颜色
	 *
	 * @param color
	 */
	public  void setLeftColor(final RedPacketViewGroup redPacketViewGroup,int color) {
		this.COLOR_LEFT = color;
		// 设置了color后，需要重新绘制view
		redPacketViewGroup.postDelayed(new Runnable() {
			@Override
			public void run() {
				invalidateView(redPacketViewGroup);
			}
		}, 500);

	}

	public int getCOLOR_LEFT() {
		return COLOR_LEFT;
	}

	private void invalidateView(RedPacketViewGroup redPacketViewGroup) {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			// 在 UI线程里
			redPacketViewGroup.invalidate();
//			redPacketViewGroup.invalidate(mLeft, mTop, mRight, mBottom);
		} else {
			// 在 非UI线程里
			redPacketViewGroup.postInvalidate();
//			redPacketViewGroup.postInvalidate(mLeft, mTop, mRight, mBottom);
		}
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putInt(VALUE_LEFT_COLOR, COLOR_LEFT);
		return bundle;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {

		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			COLOR_LEFT = bundle.getInt(VALUE_LEFT_COLOR);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			return;
		}
		super.onRestoreInstanceState(state);
	}

}
