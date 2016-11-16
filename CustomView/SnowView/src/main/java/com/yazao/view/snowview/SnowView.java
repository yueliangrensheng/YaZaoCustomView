package com.yazao.view.snowview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by shaopingzhai on 15/12/4.
 */
public class SnowView extends LinearLayout implements Runnable {

	//雪花图片
	Bitmap bitmap_snows[] = new Bitmap[5];
	Bitmap bitmap_bg;
	Thread thread;

	//图片是否移动
	public boolean IsRunning = true;

	private int left;
	private int top;

	private float screemWidth;
	private float screemHeiht;

	//花的位置
	private Coordinate[] snows = new Coordinate[80];

	private static Random random = new Random();

	//图片的移动速度
	private int dx = 1;
	private int dy = 3;

	private int sleepTime;

	private int offset = 0;

	private ArrayList<Snow> snowflake_xxl = new ArrayList<Snow>();
	private ArrayList<Snow> snowflake_xl = new ArrayList<Snow>();
	private ArrayList<Snow> snowflake_m = new ArrayList<Snow>();
	private ArrayList<Snow> snowflake_s = new ArrayList<Snow>();
	private ArrayList<Snow> snowflake_l = new ArrayList<Snow>();

	public SnowView(Context context) {
		this(context, null);
	}

	public SnowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}


	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SnowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}


	private void init() {
		LoadSnowImage();
		addRandomSnow();
	}

	/**
	 * 加载雪花图片到内存中
	 */
	public void LoadSnowImage() {
		Resources r = this.getContext().getResources();
		bitmap_snows[0] = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake_l);
		bitmap_snows[1] = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake_s);
		bitmap_snows[2] = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake_m);
		bitmap_snows[3] = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake_xl);
		bitmap_snows[4] = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake_xxl);
		bitmap_bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg14_day_snow);
	}

	public void addRandomSnow() {
		//初始化雪花
		for (int i = 0; i < 20; i++) {
			snowflake_xxl.add(new Snow(bitmap_snows[4], random.nextFloat() * screemWidth, random.nextFloat() * screemHeiht, 7f, 1 - random.nextFloat() * 2));
			snowflake_xl.add(new Snow(bitmap_snows[3], random.nextFloat() * screemWidth, random.nextFloat() * screemHeiht, 5f, 1 - random.nextFloat() * 2));
			snowflake_m.add(new Snow(bitmap_snows[2], random.nextFloat() * screemWidth, random.nextFloat() * screemHeiht, 3f, 1 - random.nextFloat() * 2));
			snowflake_s.add(new Snow(bitmap_snows[1], random.nextFloat() * screemWidth, random.nextFloat() * screemHeiht, 2f, 1 - random.nextFloat() * 2));
			snowflake_l.add(new Snow(bitmap_snows[0], random.nextFloat() * screemWidth, random.nextFloat() * screemHeiht, 2f, 1 - random.nextFloat() * 2));
		}
	}


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		IsRunning=true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		IsRunning = false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IsRunning = false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void run() {
		while (IsRunning) {

			Canvas canvas = new Canvas();

			synchronized (this) {
				try {
					if (canvas != null) {
						drawSnow(canvas);
						Snow snow;

						for (int i = 0; i < 20; i++) {
							snow = snowflake_xxl.get(i);
							SnowDown(snow);

							snow = snowflake_xl.get(i);
							SnowDown(snow);

							snow = snowflake_m.get(i);
							SnowDown(snow);

							snow = snowflake_s.get(i);
							SnowDown(snow);

							snow = snowflake_l.get(i);
							SnowDown(snow);
						}
						Thread.sleep(15);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

				}
			}
		}
	}

	public void drawSnow(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);

		RectF rectF = new RectF(0, 0, screemWidth, screemHeiht);
		canvas.drawBitmap(bitmap_bg, null, rectF, paint);

		Snow snow = null;
		for (int i = 0; i < 20; i++) {
			snow = snowflake_xxl.get(i);
			canvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
			snow = snowflake_xl.get(i);
			canvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
			snow = snowflake_m.get(i);
			canvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
			snow = snowflake_s.get(i);
			canvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
			snow = snowflake_l.get(i);
			canvas.drawBitmap(snow.bitmap, snow.x, snow.y, paint);
		}
	}


	//雪花下落
	private void SnowDown(Snow snow) {
		// 雪花的落出屏幕后又让它从顶上下落
		if (snow.x > screemWidth || snow.y > screemHeiht) {
			snow.y = 0;
			snow.x = random.nextFloat() * screemWidth;
		}
		snow.x += snow.offset; //下落飘的偏移量
		snow.y += snow.speed; //下落的速度
	}


	class Coordinate {
		int x;
		int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
