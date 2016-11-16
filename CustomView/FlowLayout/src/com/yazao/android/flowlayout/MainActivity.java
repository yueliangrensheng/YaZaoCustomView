package com.yazao.android.flowlayout;

import com.yazao.android.flowlayout.widget.FlowLayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private FlowLayout flowlayout;

	private String[] mVals = new String[] { "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello", "Android",
			"Weclome", "Button ImageView", "TextView", "Helloworld", "Android", "Weclome Hello", "Button Text",
			"TextView" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flowlayout = (FlowLayout) findViewById(R.id.flowlayout);

		LayoutInflater inflater = LayoutInflater.from(this);
		for (int j = 0; j < mVals.length; j++) {

			TextView view = (TextView) inflater.inflate(R.layout.tv, flowlayout, false);
			view.setText(mVals[j]);
			flowlayout.addView(view);
		}
	}
}
