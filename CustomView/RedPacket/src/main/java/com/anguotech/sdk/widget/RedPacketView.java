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
import android.widget.TextView;

public class RedPacketView extends TextView {

	/**
	 * 红包左边颜色的画笔
	 */
	private Paint mLeftPaint;
	private Rect mLeftRect;
	/**
	 * 左边的宽度
	 */
	private int LEFT_COLOR_WIDTH = (int) (getContext().getResources().getDisplayMetrics().density * 8 + 0.5f);
	/**
	 * 左边的颜色
	 */
	private int COLOR_LEFT = Color.GRAY;

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

	public RedPacketView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RedPacketView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RedPacketView(Context context) {
		this(context, null);
	}

	private void init() {
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

		mLeft = 0;
		mTop = 0;
		mRight = mLeft + LEFT_COLOR_WIDTH;
		mBottom = getMeasuredHeight();
		mLeftRect = new Rect(mLeft, mTop, mRight, mBottom);

		// 半圆的直径是 高度/5
		radius = (getMeasuredHeight()) / (2 * 5);

	}

	/**
	 *Canvas 在一般的情况下可以看作是一张画布，所有的绘图操作如drawBitmap, drawCircle都发生在这张画布上，这张画板还定义了一些属性比如Matrix，颜色等等。
	 *但是如果需要实现一些相对复杂的绘图操作，比如多层动画，地图（地图可以有多个地图层叠加而成，比如：政区层，道路层，兴趣点层）。

	 Canvas提供了图层（Layer）支持，缺省情况可以看作是只有一个图层Layer。如果需要按层次来绘图，Android的Canvas可以使用SaveLayerXXX, Restore 来创建一些中间层，对于这些Layer是按照“栈结构“来管理的：
	 创建一个新的Layer到“栈”中，可以使用saveLayer, savaLayerAlpha, 从“栈”中推出一个Layer，可以使用restore,restoreToCount。但Layer入栈时，后续的DrawXXX操作都发生在这个Layer上，而Layer退栈时，就会把本层绘制的图像“绘制”到上层或是Canvas上，在复制Layer到Canvas上时，可以指定Layer的透明度(Layer），这是在创建Layer时指定的：

	 public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags)
	 *
	 */

	@Override
	protected void onDraw(Canvas canvas) {
		// 0.绘制透明布局
		canvas.drawColor(Color.TRANSPARENT);

		// 在调用canvas.saveLayerAlpha 创建一个新图层之后，后续的canvas.drawCircle 都会发生的这个新图层上，
		// canvas.restore() 将这个新图层绘制的图像“复制”到Canvas的缺省图层上
		// 创建一个图层
		canvas.saveLayerAlpha(mLeft, mTop, getMeasuredWidth(), getMeasuredHeight(), 255, LAYERS_FLAGS);
		// 给图层绘制白色背景
		canvas.drawColor(Color.WHITE);
		// 1.绘制左边颜色带
		canvas.drawRect(mLeftRect, mLeftPaint);
		// 2.设置xfermode
		mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		// 3. 绘制左边的半圆
		canvas.drawCircle(mLeft, 5 * radius, radius, mCirclePaint);

		// 4.绘制右边的半圆
		canvas.drawCircle(getMeasuredWidth(), 5 * radius, radius, mCirclePaint);
		canvas.restore();
	}

	/**
	 * 设置左边的彩色带的颜色
	 *
	 * @param color
	 */
	public void setLeftColor(int color) {
		this.LEFT_COLOR_WIDTH = color;
		// 设置了color后，需要重新绘制view
		invalidateView();
	}

	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			// 在 UI线程里
			invalidate();
		} else {
			// 在 非UI线程里
			postInvalidate();
		}
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putInt(VALUE_LEFT_COLOR, LEFT_COLOR_WIDTH);
		return bundle;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {

		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			LEFT_COLOR_WIDTH = bundle.getInt(VALUE_LEFT_COLOR);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			return;
		}
		super.onRestoreInstanceState(state);
	}
}
