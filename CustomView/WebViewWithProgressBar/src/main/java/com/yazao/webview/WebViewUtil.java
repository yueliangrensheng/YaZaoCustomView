package com.yazao.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zhaishaoping on 16/9/6.
 */
public class WebViewUtil {
    private static WebViewUtil ourInstance = new WebViewUtil();


    public static WebViewUtil getInstance() {
        return ourInstance;
    }

    private WebViewUtil() {
    }


    public void show(Activity context, WebView webView, String webUrl) {
        if (webView == null || webUrl == null || webUrl.length() == 0) {
            return;
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//如果访问的页面中有 Javascript,则 WebView 必须设置支持 Javascript。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存,直接从网上获取资源

        //加快HTML网页装载完成的速度
        if (Build.VERSION.SDK_INT >= 19){
            settings.setLoadsImagesAutomatically(true);
        }else {
            settings.setLoadsImagesAutomatically(false);
        }

        //取消浏览器记住密码功能
        if(Build.VERSION.SDK_INT<=18){
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
        }else{
            //do nothing
        }

        webView.clearCache(true);//清除缓存
        webView.requestFocus();//如果不设置,辣么在点击网页文本输入框中,不能弹出软键盘以及响应其他的一些事件.
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//取消滚动条


        webView.loadUrl(webUrl);

        // 如果希望点击链接继续在当前browser中响应,而不是新开Android的系统browser中响应该链接,必须覆盖 WebView的WebViewClient对象
        webView.setWebViewClient(webViewClient);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();//接受所有网站的证书
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    };

}
