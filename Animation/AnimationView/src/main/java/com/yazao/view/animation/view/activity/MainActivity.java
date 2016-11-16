package com.yazao.view.animation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yazao.view.animation.R;
import com.yazao.view.animation.view.adapter.CommonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  RecyclerView mRecyclerView;
    private CommonRecyclerViewAdapter mRecyclerViewAdpater;
    List<String> data =new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerViewAdpater = new CommonRecyclerViewAdapter(data);

        mRecyclerView.setAdapter(mRecyclerViewAdpater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewAdpater.setItemClickListener(new CommonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Position= "+position, Toast.LENGTH_SHORT).show();
                Intent intent;

                switch(position){
                    case 0:
                        intent=new Intent(MainActivity.this,CameraViewActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 构造数据
     */
    private void initData() {
        data.add("CameraView1");
        data.add("CameraView2");
        data.add("CameraView3");
    }
}
