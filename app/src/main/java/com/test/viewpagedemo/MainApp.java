package com.test.viewpagedemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.webkit.WebSettings;
import android.webkit.WebView;

//import com.squareup.leakcanary.AndroidExcludedRefs;
//import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.WebView.MyWebviewClient;

public class MainApp extends Application {


    //提供给外层RefWatcher，监控变量是否内存泄露
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    private static RefWatcher refWatcher;

    public static Object object;


    @Override
    public void onCreate() {
        super.onCreate();

    }


}
