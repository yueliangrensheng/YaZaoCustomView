package com.anguotech.sdk.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.anguotech.sdk.widget.R;
import com.anguotech.sdk.widget.RedPacketViewGroup;

/**
 * Created by shaopingzhai on 15/11/14.
 */
public class RedPacketActivity extends Activity {
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		btn = (Button) findViewById(R.id.btn);
		btn.setBackgroundColor(0xff0000);
		ListView listview = (ListView) findViewById(R.id.listview);

		ListAdapter adapter = new MyAdapter();
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RedPacketViewGroup views = (RedPacketViewGroup) view;
				btn.setText("position= " + position +"color= "+Color.red(views.getCOLOR_LEFT()));
				Toast.makeText(RedPacketActivity.this, "position= " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			RedPacketViewGroup inflate = (RedPacketViewGroup) LayoutInflater.from(getBaseContext()).inflate(R.layout.main2, null, true);
			View line = inflate.findViewById(R.id.line);
			if (position % 3 == 0) {
				inflate.setLeftColor(inflate, Color.BLUE);
				line.setBackgroundColor(Color.BLUE);
//				line.setBackgroundColor(0xD9D9D9);

			} else if (position % 3 == 1) {
				inflate.setLeftColor(inflate, Color.BLACK);
//				line.setBackgroundColor(0xE56d34);
				line.setBackgroundColor(Color.BLACK);

			} else if (position % 3 == 2) {
				inflate.setLeftColor(inflate, Color.GREEN);
//				line.setBackgroundColor(0xE5B335);
				line.setBackgroundColor(Color.GREEN);
			}
			return inflate;
		}
	}
}
