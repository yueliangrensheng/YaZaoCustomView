package com.yazao.webview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "";
    private static final String EXTRA_TITLE = "";

    private WebView mWebView;
    private String url = "http://www.gank.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mWebView = (WebView) findViewById(R.id.webview);

        WebViewUtil.getInstance().show(WebActivity.this, mWebView, url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //设置WebView 退回健
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView!=null && mWebView.canGoBack()){
                mWebView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }


}
