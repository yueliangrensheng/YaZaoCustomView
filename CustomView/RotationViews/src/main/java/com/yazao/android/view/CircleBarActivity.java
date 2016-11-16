package com.yazao.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.yazao.android.view.widget.CircleBarView;

/**
 * Created by shaopingzhai on 15/12/1.
 */
public class CircleBarActivity extends Activity {
	private CircleBarView circleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		circleBar = (CircleBarView) findViewById(R.id.circle);
		circleBar.setSweepAngle(120);
		circleBar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				circleBar.startCustomAnimation();
			}
		});
		new Handler().postDelayed(new Runnable() {
			public void run() {
				circleBar.setText("270");
			}
		}, 2000);

	}
}
