package com.yazao.android.flowlayout.widget;

public class Flow {

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
}
