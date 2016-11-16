package com.yazao.view.animation.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yazao.view.animation.R;
import com.yazao.view.animation.widget.CameraTranslateView;

/**
 * Created by zhaishaoping on 16/8/24.
 */
public class CameraViewActivity extends Activity {

    private CameraTranslateView mCameraTranslateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        mCameraTranslateView = (CameraTranslateView) findViewById(R.id.cameraView);

    }
}
