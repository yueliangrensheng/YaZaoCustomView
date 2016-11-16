package com.yazao.view.animation.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhaishaoping on 16/8/24.
 */
public class CameraTranslateView extends View {

    private Camera mCamera;
    private Paint mPaint;
    private Matrix matrix;


    public CameraTranslateView(Context context) {
        this(context,null);
    }

    public CameraTranslateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraTranslateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraTranslateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mCamera = new Camera();
        matrix =new Matrix();
        setBackgroundColor(Color.parseColor("#3f51b5"));
        mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ff4081"));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        mCamera.save();//保存状态
        mCamera.translate(10,50,-180);//沿着x,y,z轴平移:  观察物体右移(+x)10，上移(+y)50，向-z轴移180（即让物体接近camera，这样物体将会变大）
        mCamera.getMatrix(matrix);//获取转换效果后的Matrix对象
        mCamera.restore();//恢复保存的状态
        canvas.concat(matrix);


        canvas.drawCircle(60,60,60,mPaint);
    }
}
