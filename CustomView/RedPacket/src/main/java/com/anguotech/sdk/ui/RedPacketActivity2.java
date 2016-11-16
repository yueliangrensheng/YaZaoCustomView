package com.anguotech.sdk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.anguotech.sdk.widget.R;
import com.anguotech.sdk.widget.RedPacketViewGroup;

/**
 * Created by shaopingzhai on 15/11/14.
 */
public class RedPacketActivity2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);

		final RedPacketViewGroup viewGroup=(RedPacketViewGroup)findViewById(R.id.ag_red_packet_root);
		View view = viewGroup.findViewById(R.id.line);
//		viewGroup.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				viewGroup.setLeftColor(viewGroup, 0x000000);
//			}
//		},0);
//
//
//
//		view.setBackgroundColor(Color.rgb(0,0,255));


	}
}
