package com.yazao.android.horizontal;

import java.util.ArrayList;
import java.util.List;

import com.yazao.android.horizontal.util.MyUtils;
import com.yazao.android.horizontal.widget.HorizontalScrollView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private HorizontalScrollView mHScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置窗口没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		LayoutInflater inflate = LayoutInflater.from(this);
		mHScrollView = (HorizontalScrollView) findViewById(R.id.hscrollview);

		int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
		for (int i = 0; i < 3; i++) {
			ViewGroup view = (ViewGroup) inflate.inflate(R.layout.content_layout, mHScrollView, false);
			view.getLayoutParams().width = screenWidth/3;
			TextView tvTitle = (TextView) view.findViewById(R.id.title);
			tvTitle.setText("page " + (i + 1));
			view.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
			createList(view);
			mHScrollView.addView(view);
		}

	}

	private void createList(ViewGroup view) {
		ListView listView = (ListView) view.findViewById(R.id.list);
		List<String> datas = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			datas.add("name " + i);
		}
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.content_list_item, R.id.name, datas);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getBaseContext(), "Click Item " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
