package com.yazao.android.view;

import com.yazao.android.view.circle.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * com.yazao.android.view.CircleView
 * 
 * @description:
 * @author yueliangrensheng create at 2015年10月19日下午5:53:03
 */
public class CircleView extends View {

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int mColor = Color.RED;

	@SuppressLint("NewApi")
	public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}
	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}
	public CircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public CircleView(Context context) {
		this(context, null);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		/**
		 * 自定义属性：
		 * 1.在values目录下创建自定义属性的xml。比如：attr.xml 或者attr_circle.xml 等 
		 * 以attr_ 开头的文件名。
		 * 2.在自定义view的 构造方法中解析并处理 自定义属性的值。
		 */
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
		int color = a.getColor(R.styleable.CircleView_circle_color, mColor);
		a.recycle();
		
		mPaint.setColor(color);
	}
	/**
	 * Android自定义属性，format详解

		1. reference：参考某一资源ID。
		
		    （1）属性定义：
		
		            <declare-styleable name = "名称">
		
		                   <attr name = "background" format = "reference" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		             <ImageView
		
		                     android:layout_width = "42dip"
		                     android:layout_height = "42dip"
		                     android:background = "@drawable/图片ID"
		
		                     />
		
		2. color：颜色值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "名称">
		
		                   <attr name = "textColor" format = "color" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <TextView
		
		                     android:layout_width = "42dip"
		                     android:layout_height = "42dip"
		                     android:textColor = "#00FF00"
		
		                     />
		
		3. boolean：布尔值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "名称">
		
		                   <attr name = "focusable" format = "boolean" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <Button
		
		                    android:layout_width = "42dip"
		                    android:layout_height = "42dip"
		
		                    android:focusable = "true"
		
		                    />
		
		4. dimension：尺寸值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "名称">
		
		                   <attr name = "layout_width" format = "dimension" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <Button
		
		                    android:layout_width = "42dip"
		                    android:layout_height = "42dip"
		
		                    />
		
		5. float：浮点值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "AlphaAnimation">
		
		                   <attr name = "fromAlpha" format = "float" />
		                   <attr name = "toAlpha" format = "float" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <alpha
		                   android:fromAlpha = "1.0"
		                   android:toAlpha = "0.7"
		
		                   />
		
		6. integer：整型值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "AnimatedRotateDrawable">
		
		                   <attr name = "visible" />
		                   <attr name = "frameDuration" format="integer" />
		                   <attr name = "framesCount" format="integer" />
		                   <attr name = "pivotX" />
		                   <attr name = "pivotY" />
		                   <attr name = "drawable" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <animated-rotate
		
		                   xmlns:android = "http://schemas.android.com/apk/res/android"  
		                   android:drawable = "@drawable/图片ID"  
		                   android:pivotX = "50%"  
		                   android:pivotY = "50%"  
		                   android:framesCount = "12"  
		                   android:frameDuration = "100"
		
		                   />
		
		7. string：字符串。
		
		    （1）属性定义：
		
		            <declare-styleable name = "MapView">
		                   <attr name = "apiKey" format = "string" />
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <com.google.android.maps.MapView
		                    android:layout_width = "fill_parent"
		                    android:layout_height = "fill_parent"
		                    android:apiKey = "0jOkQ80oD1JL9C6HAja99uGXCRiS2CGjKO_bc_g"
		
		                    />
		
		8. fraction：百分数。
		
		    （1）属性定义：
		
		            <declare-styleable name="RotateDrawable">
		                   <attr name = "visible" />
		                   <attr name = "fromDegrees" format = "float" />
		                   <attr name = "toDegrees" format = "float" />
		                   <attr name = "pivotX" format = "fraction" />
		                   <attr name = "pivotY" format = "fraction" />
		                   <attr name = "drawable" />
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <rotate
		
		                   xmlns:android = "http://schemas.android.com/apk/res/android" 
		　　             android:interpolator = "@anim/动画ID"
		
		                   android:fromDegrees = "0" 
		　　             android:toDegrees = "360"
		
		                   android:pivotX = "200%"
		
		                   android:pivotY = "300%" 
		　　             android:duration = "5000"
		
		                   android:repeatMode = "restart"
		
		                   android:repeatCount = "infinite"
		
		                   />
		
		9. enum：枚举值。
		
		    （1）属性定义：
		
		            <declare-styleable name="名称">
		                   <attr name="orientation">
		                          <enum name="horizontal" value="0" />
		                          <enum name="vertical" value="1" />
		                   </attr>            
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		            <LinearLayout
		
		                    xmlns:android = "http://schemas.android.com/apk/res/android"
		                    android:orientation = "vertical"
		                    android:layout_width = "fill_parent"
		                    android:layout_height = "fill_parent"
		                    >
		            </LinearLayout>
		
		10. flag：位或运算。
		
		     （1）属性定义：
		
		             <declare-styleable name="名称">
		                    <attr name="windowSoftInputMode">
		                            <flag name = "stateUnspecified" value = "0" />
		                            <flag name = "stateUnchanged" value = "1" />
		                            <flag name = "stateHidden" value = "2" />
		                            <flag name = "stateAlwaysHidden" value = "3" />
		                            <flag name = "stateVisible" value = "4" />
		                            <flag name = "stateAlwaysVisible" value = "5" />
		                            <flag name = "adjustUnspecified" value = "0x00" />
		                            <flag name = "adjustResize" value = "0x10" />
		                            <flag name = "adjustPan" value = "0x20" />
		                            <flag name = "adjustNothing" value = "0x30" />
		                     </attr>         
		
		             </declare-styleable>
		
		     （2）属性使用：
		
		            <activity
		
		                   android:name = ".StyleAndThemeActivity"
		                   android:label = "@string/app_name"
		                   android:windowSoftInputMode = "stateUnspecified | stateUnchanged　|　stateHidden">
		                   <intent-filter>
		                          <action android:name = "android.intent.action.MAIN" />
		                          <category android:name = "android.intent.category.LAUNCHER" />
		                   </intent-filter>
		             </activity>
		
		     注意：
		
		     属性定义时可以指定多种类型值。
		
		    （1）属性定义：
		
		            <declare-styleable name = "名称">
		
		                   <attr name = "background" format = "reference|color" />
		
		            </declare-styleable>
		
		    （2）属性使用：
		
		             <ImageView
		
		                     android:layout_width = "42dip"
		                     android:layout_height = "42dip"
		                     android:background = "@drawable/图片ID|#00FF00"
		
		                     />
	 * 
	 * 
	 */
	
	
	/**
	 * 继承View需要重写onDraw方法
	 * 在实现过程中，必须要考虑到wrap_content模式和padding。
	 * 
	 * 下面代码是实现了一个 绘制圆形的自定义View。
	 * 1.如果在 xml布局中 对CircleView控件 设置 margin属性，那么这个margin属性是生效的。
	 * 这是因为： margin属性是由父容器控制的。所以不需要在自定义View中来专门处理margin值。
	 * 2.如果在 xml布局中 对CircleView控件 设置 padding属性，那么这个padding属性是失效的。
	 * 这个时候就需要在自定义View中来处理padding值。
	 * 3.对于 CircleView控件的宽高，若是 wrap_content时，需要 在测量阶段 来设置 默认的值。
	 */

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		// 如果是 wrap_content时，设置其 默认值: 400px。
		if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(400, 400);
		} else if (widthMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(400, heightSize);
		} else if (heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSize, 400);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();
		
		int width = getWidth()-paddingLeft-paddingRight;
		int height = getHeight()-paddingTop-paddingBottom;

		float radius = Math.min(width, height)/2;
		canvas.drawCircle(paddingLeft +width/2, paddingTop +height/2, radius , mPaint);

	}

}
