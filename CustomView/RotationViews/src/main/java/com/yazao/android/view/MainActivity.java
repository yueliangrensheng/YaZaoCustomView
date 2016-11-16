package com.yazao.android.view;

import android.app.Activity;
import android.os.Bundle;

import com.yazao.android.view.widget.RotationView;

public class MainActivity extends Activity {
	private RotationView rotationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rotationView = (RotationView) findViewById(R.id.rotationView);

		rotationView.startAnim(rotationView);




//		final Button btn = (Button) findViewById(R.id.btn);
//		btn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				AnimationSet set=new AnimationSet(false);
//				Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation_test1);
//				AlphaAnimation alphaAnimation=new AlphaAnimation(0.3f,1.0f);
//				alphaAnimation.setDuration(500);
//				alphaAnimation.setFillAfter(false);
//				set.addAnimation(animation);
//				set.addAnimation(alphaAnimation);
//				set.setFillAfter(true);
//				btn.startAnimation(set);
//			}
//		});

	}


}
