package com.anguotech.sdk.android.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class FloatBtnViewManager {
	private FloatBtnView floatView = null;
	private boolean isDisplay = false;
	private static Context mContext;
	/** 是否有新消息 */
	private boolean ismegs = true;

	private FloatBtnViewManager() {
	}

	private static FloatBtnViewManager instance = new FloatBtnViewManager();

	public static FloatBtnViewManager getInstance(Context context) {
		mContext = context;
		return instance;
	}

	public void removeView() {
		if (!isDisplay)
			return;
		if (floatView!=null) {
			floatView.hide();
			isDisplay = false;
		}
	}

	public void createView() {

		if (isDisplay)
			return;
		createViews();

	}

	private void createViews() {
		String fowardImgResId = "ag_float_image_white";
		String fowardImgResIdHalfLeft = "ag_float_image_white_half_left";
		String fowardImgResIdHalfRight = "ag_float_image_white_half_right";


		String belowImgResId = "ag_float_image";
		String belowImgResIdHalfLeft = "ag_float_image_half_left";
		String belowImgResIdHalfRight = "ag_float_image_half_right";


		String backgroundResId = "ag_float_image_redpoint";
		String backgroundResIdHalfLeft = "ag_float_image_redpoint_half_left";
		String backgroundResIdHalfRight = "ag_float_image_redpoint_half_right";
		
		if (floatView==null) {
			floatView =new FloatBtnView(mContext, ismegs, fowardImgResIdHalfLeft, fowardImgResIdHalfRight, fowardImgResId, belowImgResIdHalfLeft, belowImgResIdHalfRight, belowImgResId, backgroundResIdHalfLeft, backgroundResIdHalfRight, backgroundResId);
		}
		floatView.setUpdate(ismegs);
		floatView.show();
		floatView.setOnClickListener(floatViewClick);
		isDisplay = true;
	}

	/**
	 * 点击小球跳转个人中心界面
	 */
	private OnClickListener floatViewClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			isDisplay=false;

			Toast.makeText(mContext, "您没有登陆，请重新登录！", Toast.LENGTH_SHORT).show();
		}
	};
}
