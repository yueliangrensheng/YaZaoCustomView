package com.anguotech.sdk.android.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anguotech.sdk.android.R;
import com.anguotech.sdk.android.widget.FloatBtnView;
import com.anguotech.sdk.android.widget.FloatBtnViewManager;
import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Checker;
import com.nobrain.android.permissions.Result;

/**
 * Created by shaopingzhai on 15/11/11.
 */
public class RunTimePermissionActivity extends Activity {

	FloatBtnView floatBtnView;

	Button show;

	private String[] PERMISSIONS_SYSTEM_ALERT_WINDOW = {
			Manifest.permission.READ_PHONE_STATE
	};
	private static final int REQUEST_SYSTEM_ALERT_WINDOW = 101;
	private View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		rootView = findViewById(R.id.ll);

		show = (Button) findViewById(R.id.show);

		show.scrollBy(-20, 0);


		findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				generateFloatView(true);
//				showFloatView(true);
			}
		});
		findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				generateFloatView(false);
//				showFloatView(false);
			}
		});


	}


	private void generateFloatView(final boolean isShow) {


		AndroidPermissions.check(this)
				.permissions(Manifest.permission.READ_PHONE_STATE)
				.hasPermissions(new Checker.Action0() {
					@Override
					public void call(String[] permissions) {
						//有权限


						//展示小球
						showFloatView(isShow);
					}
				})
				.noPermissions(new Checker.Action1() {
					@Override
					public void call(String[] permissions) {
						//没有权限

						// 请求权限
						ActivityCompat.requestPermissions(RunTimePermissionActivity.this
								, PERMISSIONS_SYSTEM_ALERT_WINDOW
								, REQUEST_SYSTEM_ALERT_WINDOW);

					}
				})
				.check();

	}

	private void showFloatView(boolean isShow) {
		if (isShow) {
			FloatBtnViewManager.getInstance(this).createView();

		} else {
			FloatBtnViewManager.getInstance(this).removeView();

		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

		AndroidPermissions.result()
				.addPermissions(REQUEST_SYSTEM_ALERT_WINDOW,PERMISSIONS_SYSTEM_ALERT_WINDOW)
				.putActions(REQUEST_SYSTEM_ALERT_WINDOW, new Result.Action0() {
					@Override
					public void call() {
						String msg = "Request Success : " + permissions[0];
						Toast.makeText(RunTimePermissionActivity.this,
								msg,
								Toast.LENGTH_SHORT).show();
					}
				}, new Result.Action1() {
					@Override
					public void call(String[] hasPermissions, String[] noPermissions) {
						String msg = "Request Fail : " + noPermissions[0];
						Toast.makeText(RunTimePermissionActivity.this,
								msg,
								Toast.LENGTH_SHORT).show();
					}
				})
				.result(REQUEST_SYSTEM_ALERT_WINDOW,PERMISSIONS_SYSTEM_ALERT_WINDOW,grantResults);


	}
}
