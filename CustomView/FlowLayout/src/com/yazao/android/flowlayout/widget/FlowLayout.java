package com.yazao.android.flowlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View的分类：
 * 1.继承View重写onDraw方法
 * 采用这种方式需要自己支持wrap_content,并且padding也需要自己处理
 * 
 * 2.继承ViewGroup派生特殊的Layout
 * 采用这种方式需要合适的处理ViewGroup的onMeasure()、onLayout()这两个过程，
 * 并且同时需要处理子元素的测量和布局过程。
 * 
 * 3.继承特定的View(比如TextView)
 * 采用这种方式不需要自己支持wrap_content和padding等。
 * 
 * 4.继承特定的ViewGroup(比如LinearLayout)
 * 这种方式不需要自己处理ViewGroup的onMeasure()、onLayout()这两个过程。
 */
/**
 * 自定义View须知：
 * 1.让View支持wrap_content.
 * 因为直接继承View或者ViewGroup的控件，如果不再onMeasure中对wrap_content做
 * 特殊处理，那么当外界在布局中使用wrap_content时，就无法达到预期的效果。
 * 
 * 2.如果有必要，让你的View支持padding.
 * 因为直接继承View的控件，如果不再draw方法中处理padding，那么padding属性是无法
 * 其作用的。直接继承ViewGroup的控件，需要在onMeasure 和onLayout中考虑padding和
 * 子元素的margin对其造成的影响，否次将导致padding和子元素的margin失效。
 * 
 * 3.尽量不要再View中使用Handler，没必要。
 * 除非有明确的要使用Handler来发送消息。
 * 
 * 4.View中如果有线程或者动画，需要及时停止。 可以参考View.onDetachedFromWindow
 * 当包含此View的Activity退出或者当前View被remove时，View的onDetachedFromWindow方
 * 法会被调用，和此方法对应的是onAttachedToWindow.同时，当包含View的Activity启动时，
 * View的onAttachedToWindow方法会被调用。同时，当view变得不可见时也需要停止线程和动画。
 * 
 * 5.View带有滑动嵌套的情形时，需要处理好滑动冲突。
 */

/**
 * 
 * com.yazao.android.flowlayout.widget.FlowLayout
 * 
 * @description: 自定义View，当继承ViewGroup时，需要合理的处理ViewGroup的onMeasure、onLayout这两个过程，
 *               并同时处理子元素的测量和布局过程。
 *               在测量和布局的过程中，需要考虑其本身的padding和margin，还要考虑子元素的padding和margin。
 * @author yueliangrensheng create at 2015年10月16日下午5:38:08
 */
public class FlowLayout extends ViewGroup {
	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// 调用其他的构造方法 都会走到这里
		init();
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context) {
		this(context, null);
	}

	private void init() {
		// 初始化
	}

	/**
	 *  View的工作流程： measure、layout、draw。
	 *  measure：确定View的测量宽/高 
	 *  layout：确定View的最终宽/高和 四个顶点的位置 
	 *  draw：将View绘制到屏幕上
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec); // 不可省略
		/**
		 * SpecMode有三类：UNSPECIFIED、EXACTLY、AT_MOST
		 * UNSPECIFIED: 子控件想要多大就给多大，但是很少见。一般用于系统内部，表示一种测量状态。
		 * EXACTLY: 100dp、match_parent
		 * AT_MOST:wrap_content
		 * 
		 * 关于MeasureSpec：
		 * 对于普通的View，其MeasureSpec由父容器的MeasureSpec和自身的LayoutParams来共同决定。
		 * 对于DecorView，其MeasureSpec由窗口的大小和自身的LayoutParams来共同决定。
		 */

		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

		// 如果FlowLayoutde宽高都是：wrap_content，那么需要自己去计算其 默认的宽高。
		int widthDefault = 0;
		int heightDefault = 0;

		// 记录每一行的宽度和高度
		int lineWidth = 0;
		int lineHeight = 0;

		// 获取内部子元素的数量
		int cCount = getChildCount();

		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			// 测量子元素的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			// 得到LayoutParams
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

			// 子元素占据的宽度 和高度 (需要计算其 margin间距值)
			int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

			// 换行 (需要计算子元素的父类FlowLayout的 padding 间距值)
			if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
				// 对比得到 最大值
				widthDefault = Math.max(widthDefault, lineWidth);
				// 重置lineWidth
				lineWidth = childWidth;
				// 记录行高
				heightDefault += lineHeight;
				// 重置lineHeight
				lineHeight = childHeight;
			} else {
				// 不换行
				// 叠加行宽
				lineWidth += childWidth;
				// 得到当前行的最大高度
				lineHeight = Math.max(childHeight, lineHeight);
			}

			// 最后一个控件
			if (i == cCount - 1) {
				widthDefault = Math.max(widthDefault, lineWidth);
				heightDefault += lineHeight;
			}
		}

		Log.i("YaZao", "sizeWidth = " + sizeWidth);
		Log.i("YaZao", "sizeHeight = " + sizeHeight);

		// UNSPECIFIED 不考虑 ,但是要考虑 子元素的padding
		if (modeWidth == MeasureSpec.AT_MOST && modeHeight == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthDefault + getPaddingLeft() + getPaddingRight(),
					heightDefault + getPaddingTop() + getPaddingBottom());
		} else if (modeWidth == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthDefault + getPaddingLeft() + getPaddingRight(), sizeHeight);
		} else if (modeHeight == MeasureSpec.AT_MOST) {
			setMeasuredDimension(sizeWidth, heightDefault + getPaddingTop() + getPaddingBottom());
		}

		// 等价于：
//		 setMeasuredDimension(
//		 MeasureSpec.AT_MOST == modeWidth ? widthDefault + getPaddingLeft() +
//		 getPaddingRight() : sizeWidth,
//		 MeasureSpec.AT_MOST == modeHeight ? heightDefault + getPaddingTop() +
//		 getPaddingBottom() : sizeHeight);

	}
	
	/**
	 * 存储所有的View
	 */
	private List<List<View>> mAllViews = new ArrayList<List<View>>();
	
	/**
	 * 记录每一行的高度
	 */
	private List<Integer> mLineHeight = new ArrayList<Integer>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 1.先统计下 GroupView的每个元素：
		
		mAllViews.clear();
		mLineHeight.clear();
		
		//当前ViewGroup的宽度
		int width = getWidth();
		
		//每行的宽 和高
		int lineWidth=0;
		int lineHeight=0;
		
		//记录每行的 子元素
		List<View> lineViews = new ArrayList<View>();
		
		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			
			int childWidth= child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			
			//换行
			if(childWidth+lp.leftMargin+lp.rightMargin +lineWidth>width -getPaddingLeft() -getPaddingRight()){
				//记录LineHeight
				mLineHeight.add(lineHeight);
				//记录当前行的views
				mAllViews.add(lineViews);
				
				//重置行宽和行高
				lineWidth=0;
				lineHeight=childHeight+lp.topMargin+lp.bottomMargin;
				//重置View集合
				lineViews =new ArrayList<View>();
			}
			lineWidth +=childWidth+lp.leftMargin+lp.rightMargin;
			lineHeight=Math.max(lineHeight, childHeight+lp.topMargin+lp.bottomMargin);
			
			lineViews.add(child);
		}
		
		//最后一行处理
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);
		
		
		// 2. 再对每个子元素进行 layout
		
		//设置子View的位置
		
		int left=getPaddingLeft();
		int top = getPaddingTop();
		
		//行数
		int lineNum = mAllViews.size();
		
		for (int i = 0; i < lineNum; i++) {// 遍历每一行
			//当前行的所有View
			lineViews = mAllViews.get(i);
			//当前行的高度
			lineHeight = mLineHeight.get(i);
			
			for (int j = 0; j < lineViews.size(); j++) {//对 每一行元素进行layout
				View child = lineViews.get(j);
				//判断view的状态 （如果是 gone状态，那么不会测量和布局、绘制的）
				if(child.getVisibility() ==View.GONE){
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				int lc=left+lp.leftMargin;
				int tc=top+lp.topMargin;
				int rc=lc+child.getMeasuredWidth();
				int bc=tc+child.getMeasuredHeight();
				//为 子View进行布局
				child.layout(lc, tc, rc, bc);
				//一行还未结束前： 更新left值
				left +=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
			}
			
			//每行遍历结束后，需要重置 left 和top
			left=getPaddingLeft();
			top+=lineHeight;
		}
	}

	/**
	 * 生成 FlowLayout对应的 LayoutParams.即：MarginLayoutParams
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
}
