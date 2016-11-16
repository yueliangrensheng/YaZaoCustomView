package com.yazao.android.wechat.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TagFragment extends Fragment {

	private String mTitle="WeChat";
	public static final String TITLE="title";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (getArguments()!=null) {
			mTitle= getArguments().getString(TITLE);
		}
		TextView tv =new TextView(getActivity());
		tv.setTextSize(30.0f);
		tv.setText(mTitle);
		tv.setBackgroundColor(Color.parseColor("#ffffff"));
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
}
