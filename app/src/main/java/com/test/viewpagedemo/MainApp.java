package com.test.viewpagedemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.moduth.blockcanary.BlockCanary;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.WebView.MyWebviewClient;

public class MainApp extends Application {

    public static Object object;


    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtils.LOGV("init block canary");
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }


}
