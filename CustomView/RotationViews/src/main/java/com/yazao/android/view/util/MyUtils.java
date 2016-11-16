package com.yazao.android.view.util;

import android.content.Context;

/**
 * Created by shaopingzhai on 15/11/27.
 */
public class MyUtils {
	public static int dip2px(Context context, int dp) {

		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}
}
