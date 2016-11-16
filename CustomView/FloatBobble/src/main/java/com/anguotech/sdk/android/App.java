package com.anguotech.sdk.android;

import android.app.Application;

import com.anguotech.sdk.android.util.CrashHandler;

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public static App getInstance() {
        return sInstance;
    }

}
