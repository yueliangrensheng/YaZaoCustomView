package com.yazao.view.animation.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yazao.view.animation.R;

/**
 * Created by zhaishaoping on 16/8/24.
 */
public class CameraRotateView extends View {

    private Camera mCamera;
    private Paint mPaint;
    private Matrix matrix;

    public CameraRotateView(Context context) {
        this(context,null);
    }

    public CameraRotateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraRotateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mCamera.save();
        // camera.rotateX(60)的意思是绕X轴顺时针旋转60度。举例来说，如果物体中间线和X轴重合的话，绕X轴顺时针旋转60度就是指物体上半部分向里翻转，下半部分向外翻转
//        mCamera.rotateX(60);
        //camera.rotateY(60)的意思是绕Y轴顺时针旋转60度。举例来说，如果物体中间线和Y轴重合的话，绕Y轴顺时针旋转60度就是指物体左半部分向外翻转，右半部分向里翻转；
//        mCamera.rotateY(60);
        //camera.rotateZ(60)的意思是绕Z轴逆时针旋转60度。举例来说，如果物体中间线和Z轴重合的话，绕Z轴顺时针旋转60度就是物体上半部分向左翻转，下半部分向右翻转。
        mCamera.rotateZ(60);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        //下面这两行作用: 下面两行代码的意思是先将旋转中心移动到（0,0）点，因为Matrix总是用0,0点作为旋转点，旋转之后将视图放回原来的位置。
        matrix.preTranslate(-getWidth()/2,-getHeight()/2);
        matrix.postTranslate(getWidth()/2,getHeight()/2);




        BitmapFactory.Options option =new BitmapFactory.Options();
        option.inJustDecodeBounds=true;//bitmap不会加载到内存中
        BitmapFactory.decodeResource(getResources(), R.mipmap.dog,option);
        option.inSampleSize = calculateInSampleSize(option,getWidth()/2,getHeight()/2);
        option.inJustDecodeBounds=false;
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.dog,option),matrix,mPaint);


    }

    private int calculateInSampleSize(BitmapFactory.Options option, int reqWidth, int reqHeight) {
        int height= option.outHeight;
        int width =option.outWidth;
        int sampleSize=1;

        if (height>reqHeight || width >reqWidth){
            int halfHeight =height/2;
            int halfWidth =width/2;

            while((halfHeight/sampleSize)>reqHeight && (halfWidth/sampleSize)>reqWidth){
                sampleSize *=2;
            }
        }
        Log.i("YaZao","sampleSize= "+sampleSize);
        return sampleSize;
    }
}
