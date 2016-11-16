package com.anguotech.sdk.android.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.anguotech.sdk.android.R;
import com.anguotech.sdk.android.widget.FloatBtnView;
import com.anguotech.sdk.android.widget.FloatBtnViewManager;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

	FloatBtnView floatBtnView;

	Button show;

	private String[] PERMISSIONS_SYSTEM_ALERT_WINDOW = {
			Manifest.permission.SYSTEM_ALERT_WINDOW
	};
	private static final int REQUEST_SYSTEM_ALERT_WINDOW = 101;
	private View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//full all
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		rootView = findViewById(R.id.ll);

		show = (Button) findViewById(R.id.show);

		show.scrollBy(-20, 0);


		findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				generateFloatView(true);
			}
		});
		findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				generateFloatView(false);
			}
		});


	}

	private void generateFloatView(boolean isShow) {

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
			//没有权限

			// 请求权限
			requestSystemAlertWindowPerssion();
		} else {
			//有权限


			//展示小球
			showFloatView(isShow);

		}


	}

	private void requestSystemAlertWindowPerssion() {
		if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
			//提示用户 获取这个权限的 原因
			Snackbar.make(rootView, "SYSTEM_ALERT_WINDOW permission is needed to show the floatView preview.", Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, REQUEST_SYSTEM_ALERT_WINDOW);
				}
			}).show();
		}else{
			//不提示用户 获取权限的原因  直接去请求这个权限
			ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, REQUEST_SYSTEM_ALERT_WINDOW);
		}
	}

	private void showFloatView(boolean isShow) {
		if (isShow) {
			FloatBtnViewManager.getInstance(this).createView();

		} else {
			FloatBtnViewManager.getInstance(this).removeView();

		}
	}


	@Override
	protected void onPause() {
		super.onPause();
		if (floatBtnView != null) {
			floatBtnView.hide();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (floatBtnView != null) {
			floatBtnView.show();
		}

	}

	/**
	 *  权限授予后， 回调
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

		if (requestCode==REQUEST_SYSTEM_ALERT_WINDOW){

			if (grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
				//用户同意  授予权限
				Snackbar.make(rootView,"SYSTEM_ALERT_WINDOW permission has been granted",Snackbar.LENGTH_SHORT).show();
			}else{
				Snackbar.make(rootView,"SYSTEM_ALERT_WINDOW permission has been denyed",Snackbar.LENGTH_SHORT).show();
			}



		}else{
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}


	}
}
