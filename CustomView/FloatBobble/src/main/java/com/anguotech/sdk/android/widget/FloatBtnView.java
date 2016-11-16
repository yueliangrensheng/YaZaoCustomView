package com.anguotech.sdk.android.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.anguotech.sdk.android.R;

/**
 * 界面上的悬浮按钮，可以拖动，并有点击事件
 * Created by shaopingzhai on 15/11/4.
 */
public class FloatBtnView extends ImageView {
	private static final String TAG = FloatBtnView.class.getSimpleName();
	private static final long DELAYED_REFRESH_TIME_LONG = 5000;
	private static final long DELAYED_REFRESH_TIME_SHORT = 500;
	private static final int IMAGE_ALPHA = 120;
	private static final int IMAGE_ALPHA_ALL = 255;

	/**
	 * 悬浮view的宽
	 */
	private int height = 0;
	/**
	 * 悬浮view的高
	 */
	private int width = 0;
	/**
	 * 设备屏幕的宽
	 */
	private int screenHeight;
	/**
	 * 设备屏幕的高
	 */
	private int screenWidth;
	/**
	 * 状态栏高度
	 */
	private int statusBarHeight;

	/**
	 * 前置Img资源id Left
	 */
	private String fowardImgResIdHalfLeft = "";
	/**
	 * 前置Img资源id Right
	 */
	private String fowardImgResIdHalfRight = "";
	/**
	 * 前置Img资源id
	 */
	private String fowardImgResId = "";

	/**
	 * 后置Img资源id Left
	 */
	private String belowImgResIdHalfLeft = "";
	/**
	 * 后置Img资源id Right
	 */
	private String belowImgResIdHalfRight = "";
	/**
	 * 后置Img资源id
	 */
	private String belowImgResId = "";

	/**
	 * 背景资源id Left
	 */
	private String backgroundResIdHalfLeft = "";
	/**
	 * 背景资源id Right
	 */
	private String backgroundResIdHalfRight = "";
	/**
	 * 背景资源id
	 */
	private String backgroundResId = "";


	private Context mContext;
	/**
	 * 是否显示
	 */
	private boolean isShow = false;

	/**
	 * 点击事件
	 */
	private OnClickListener mOnClickListener;
	/**
	 * 是否需要更新View
	 */
	private boolean isUpdate = false;
	/**
	 * 布局参数
	 */
	private WindowManager.LayoutParams params = new WindowManager.LayoutParams();

	private WindowManager windowManager = null;

	private boolean isMove = false;
	private float DISTANCE_MOVE = 100f;
	private boolean isRight = false;//是否显示在右边

	private SharedPreferenceManaager preferenceManaager;

	public FloatBtnView(Context context, boolean isUpate,
						String fowardImgResIdHalfLeft, String fowardImgResIdHalfRight, String fowardImgResId,
						String belowImgResIdHalfLeft, String belowImgResIdHalfRight, String belowImgResId,
						String backgroundResIdHalfLeft, String backgroundResIdHalfRight, String backgroundResId) {
		super(context);
		mContext = context.getApplicationContext();
		this.isUpdate = isUpate;


		this.fowardImgResIdHalfLeft = fowardImgResIdHalfLeft;
		this.fowardImgResIdHalfRight = fowardImgResIdHalfRight;
		this.fowardImgResId = fowardImgResId;

		this.belowImgResIdHalfLeft = belowImgResIdHalfLeft;
		this.belowImgResIdHalfRight = belowImgResIdHalfRight;
		this.belowImgResId = belowImgResId;

		this.backgroundResIdHalfLeft = backgroundResIdHalfLeft;
		this.backgroundResIdHalfRight = backgroundResIdHalfRight;
		this.backgroundResId = backgroundResId;
		windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {

		preferenceManaager = new SharedPreferenceManaager(getContext());

		//获取屏幕宽高
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

//        Log.i(TAG, "screenWidth= " + screenWidth + "screenHeight= " + screenHeight + "statusBarHeight= " + statusBarHeight);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
		} else {
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// TYPE_PHONE   TYPE_SYSTEM_ALERT  TYPE_TOAST
		}

		params.format = PixelFormat.RGBA_8888;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = (int) preferenceManaager.getX();
		params.y = (int) preferenceManaager.getY();
		x = params.x;
		y = params.y;

		//设置悬浮view的宽高
		params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

//		Log.i(TAG, "params.width= " + params.width + " params.height = " + params.height + "params.x= " + params.x + "params.y= " + params.y);

		if (params.x > DISTANCE_MOVE) {
			setImageSrcResource(fowardImgResIdHalfLeft, backgroundResIdHalfLeft);
		} else {
			setImageSrcResource(fowardImgResIdHalfRight, backgroundResIdHalfRight);
		}

		refreshFloatBtnViewImages(DELAYED_REFRESH_TIME_LONG);
		DISTANCE_MOVE = getContext().getResources().getDimension(R.dimen.ag_bobble_move_distance);

	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (x > DISTANCE_MOVE) {
				setImageSrcResource(fowardImgResIdHalfLeft, backgroundResIdHalfLeft);
			} else {
				setImageSrcResource(fowardImgResIdHalfRight, backgroundResIdHalfRight);
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				setImageAlpha(IMAGE_ALPHA);
			} else {
				setAlpha(IMAGE_ALPHA);
			}
			if (isUpdate) {
				getBackground().setAlpha(IMAGE_ALPHA);
			}

		}
	};

	/**
	 * 改变悬浮view的图片
	 */
	private void refreshFloatBtnViewImages(long delayedTime) {
		this.postDelayed(runnable, delayedTime);
	}

	/**
	 * 设置悬浮view 的图片状态
	 */
	private void setImageSrcResource(String imgResId, String backagegroundResId) {
		if (isUpdate) {
			setImageResource(getDrawable(mContext, backagegroundResId));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				setBackground(mContext.getResources().getDrawable(getDrawable(mContext, imgResId)));
			} else {
				setBackgroundResource(getDrawable(mContext, imgResId));
			}
		} else {
			setImageResource(getDrawable(mContext, imgResId));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
//        Log.i(TAG, "onDraw : width= " + width + ",height= " + height + "statusBarHeight= " + statusBarHeight);

		//获取状态栏高度
		Rect outRect = new Rect();
		getWindowVisibleDisplayFrame(outRect);
		statusBarHeight = outRect.top;

//        Log.i(TAG, "onDraw:  screenWidth= " + screenWidth + "screenHeight= " + screenHeight + "statusBarHeight= " + statusBarHeight);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.mOnClickListener = l;
	}

	float mStartX = 0;
	float mStartY = 0;
	float mTouchX = 0;
	float mTouchY = 0;
	float x = 0;
	float y = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

//		getLocationOnScreen(null);

		//获取相对屏幕的坐标，即以屏幕左上角为原点
		x = event.getRawX();
		y = event.getRawY() - statusBarHeight;

//		Log.i(TAG, "x==" + x);

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				this.removeCallbacks(runnable);

				//获取相对view的坐标，即以此view左上角为原点
				mTouchX = event.getX();
				mTouchY = event.getY();

				mStartX = event.getRawX();
				mStartY = event.getRawY();


//                Log.i(TAG, "Down mStartX= " + mStartX + " ,mStartY= " + mStartY + " ,mTouchX= " + mTouchX + " ,mTouchY=" + mTouchY);

				isMove = false;

				//恢复透明度
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					setImageAlpha(IMAGE_ALPHA_ALL);
				} else {
					setAlpha(IMAGE_ALPHA_ALL);
				}
				//背景透明度恢复
				if (isUpdate) {
					getBackground().setAlpha(IMAGE_ALPHA_ALL);
				}

//				//判断是在左边还是在右边
//				boolean orientation =getOrientation();
//				if (orientation){//left
//					setImageSrcResource(belowImgResIdHalfLeft, backgroundResIdHalfLeft);
//				}else{//right
//					setImageSrcResource(belowImgResIdHalfRight, backgroundResIdHalfRight);
//				}
				setImageSrcResource(belowImgResId, backgroundResId);
				break;
			case MotionEvent.ACTION_MOVE:
				updateViewPosition();
				if ((x > DISTANCE_MOVE && (screenWidth - x) > DISTANCE_MOVE) || Math.abs(mStartY - y) > DISTANCE_MOVE) {//移动
					isMove = true;
					setImageSrcResource(belowImgResId, backgroundResId);
				}
				break;
			case MotionEvent.ACTION_UP:

				if (x <= screenWidth / 2) {
					x = 0;
					isRight = false;
				} else {
					x = screenWidth;
					isRight = true;
				}

				if (isMove) {
					isMove = false;

					this.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (x > 0) {//right
								setImageSrcResource(belowImgResIdHalfLeft, backgroundResIdHalfLeft);
							} else {
								//left
								setImageSrcResource(belowImgResIdHalfRight, backgroundResIdHalfRight);
							}
						}
					}, 200);

					updateViewPosition();

					preferenceManaager.setX(x);
					preferenceManaager.setY(y);

				} else {

//					if (getOrientation()){//left
//						setImageSrcResource(belowImgResIdHalfLeft, backgroundResIdHalfLeft);
//					}else{//right
//						setImageSrcResource(belowImgResIdHalfRight, backgroundResIdHalfRight);
//					}
					if (mOnClickListener != null) {
						mOnClickListener.onClick(this);
						hide();
					}
				}

				mTouchY = mTouchX = 0;
				refreshFloatBtnViewImages(DELAYED_REFRESH_TIME_LONG);
				break;
		}

		return true;
	}

	/**
	 * @return 如果在左边 则返回true，否则返回false
	 */
	private boolean getOrientation() {
		if (!isRight) {
			return true;
		}
		return false;
	}

	private void updateViewPosition() {
		params.x = (int) (x - mTouchX);
		params.y = (int) (y - mTouchY);
		windowManager.updateViewLayout(this, params);
	}


	public void show() {
		if (!isShow) {
			if (windowManager != null) {
				windowManager.addView(this, params);
				isShow = true;
			}
		}
	}

	public void hide() {
		if (isShow) {
			if (windowManager != null) {
				windowManager.removeView(this);
				isShow = false;
			}
		}
	}

	private int getDrawable(Context activity, String name) {
		if (activity == null) {
			return 0;
		}
		return activity.getResources().getIdentifier(name, "drawable", activity.getPackageName());
	}

	class SharedPreferenceManaager {
		private static final String PREFERENCE_MANAGER = "prefer_floating";
		private static final String PREFERENCE_X = "X";
		private static final String PREFERENCE_Y = "Y";
		private Context mContext;
		private SharedPreferences preferences;

		public SharedPreferenceManaager(Context context) {
			this.mContext = context;
			generateSharedPreference();
		}

		private SharedPreferences generateSharedPreference() {
			if (preferences == null) {
				preferences = this.mContext.getSharedPreferences(PREFERENCE_MANAGER, Context.MODE_PRIVATE);
			}
			return preferences;
		}

		public float getX() {
			return generateSharedPreference().getFloat(PREFERENCE_X, 0f);
		}

		public float getY() {
			return generateSharedPreference().getFloat(PREFERENCE_Y, 0f);
		}

		public void setX(float valueX) {
			generateSharedPreference().edit().putFloat(PREFERENCE_X, valueX).commit();
		}

		public void setY(float valueY) {
			generateSharedPreference().edit().putFloat(PREFERENCE_Y, valueY).commit();
		}

	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
		refreshFloatBtnViewImages(DELAYED_REFRESH_TIME_LONG);
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		screenHeight = outMetrics.heightPixels;
		int oldX = params.x;
		int oldY = params.y;
		switch (newConfig.orientation) {
			case Configuration.ORIENTATION_LANDSCAPE:// 横屏
				if (!getOrientation()) {//右边
					params.x = screenWidth;
					params.y = oldY;
				} else {
					params.x = oldX;
					params.y = oldY;
				}
				break;
			case Configuration.ORIENTATION_PORTRAIT:// 竖屏
				if (!getOrientation()) {
					params.x = screenWidth;
					params.y = oldY;
				} else {
					params.x = oldX;
					params.y = oldY;
				}
				break;
		}
		windowManager.updateViewLayout(this, params);

	}

}
