package com.yazao.android.horizontal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class HorizontalScrollView extends ViewGroup {

	@SuppressLint("NewApi")
	public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public HorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HorizontalScrollView(Context context) {
		this(context, null);
	}

	private void init() {

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int defaultWidth = 0;
		int defaultHeight = 0;
		// 子元素的 个数
		int childCount = getChildCount();
//		measureChildren(widthMeasureSpec, heightMeasureSpec); //代替 遍历所有的子元素的动作。
		//遍历所有的子元素
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() ==View.GONE) {
				continue;
			}
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			
			int childWidth= child.getMeasuredWidth() +lp.leftMargin+lp.rightMargin;
			int childHeight = child.getMeasuredHeight() +lp.topMargin+lp.bottomMargin;
			
			defaultHeight= Math.max(defaultHeight, childHeight);
			defaultWidth +=childWidth;
			Log.i("YaZao", "childWidth= " + child.getMeasuredWidth());
			Log.i("YaZao", "leftMargin = " + lp.leftMargin+", rightmargin == "+lp.rightMargin);
			Log.i("YaZao", "leftPadding = " +child.getPaddingLeft()+", rightPadding == "+child.getPaddingRight() +", topPadding = "+child.getPaddingTop() +", bottomPadding= "+child.getPaddingBottom());
		}
		Log.i("YaZao", "defaultWidth = " + defaultWidth);
		Log.i("YaZao", "defaultHeight = " + defaultHeight);

		

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		
		/**
		 * 首先判断是否有子元素，如果没有子元素就直接把自己的宽度和高度设置为0；
		 * 然后就是判断宽和高是不是采用了wrap_content，如果：
		 * 1.宽度采用了wrap_content，那么 HorizontalScrollView的宽度就是所有子元素
		 * 的宽度只和；
		 * 2.高度采用了wrap_content,那么HorizontalScrollView的高度就是第一个子元素
		 * 的高度。
		 */
		if (childCount == 0) {
			setMeasuredDimension(0, 0);//TODO 不足之处在于： 当没有子元素时，应该根据layoutParams来设置自己的宽度和高度。
		} else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(defaultWidth, defaultHeight);
		} else if (widthMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(defaultWidth, heightSize);
		} else if (heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSize, defaultHeight);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int childCount = getChildCount();
		//
		int left =getPaddingLeft();
		int top =getPaddingTop();
		
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			if(child.getVisibility() ==View.GONE){
				continue;
			}
			int lc =left+lp.leftMargin;
			int tc =top+lp.topMargin;
			int rc=lc+child.getMeasuredWidth();
			int bc=tc+child.getMeasuredHeight();
			child.layout(lc, tc, rc, bc);
			left+=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
		}
		
	}
	
//	@Override
//	protected LayoutParams generateDefaultLayoutParams() {
//		return super.generateDefaultLayoutParams();
//	}
//	
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
//	@Override
//	protected LayoutParams generateLayoutParams(LayoutParams p) {
//		return super.generateLayoutParams(p);
//	}

}
