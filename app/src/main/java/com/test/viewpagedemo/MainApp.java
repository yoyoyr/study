package com.test.viewpagedemo;

import android.annotation.SuppressLint;
import android.app.Application;

//import com.squareup.leakcanary.AndroidExcludedRefs;
//import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.test.viewpagedemo.LoggerUtils;

public class MainApp extends Application {


    //提供给外层RefWatcher，监控变量是否内存泄露
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    private static RefWatcher refWatcher;

    public static  Object object;


    @Override
    public void onCreate() {
        super.onCreate();

        LoggerUtils.LOGD("---------init leakcanary");

        refWatcher =  LeakCanary.install(this);
//         = LeakCanary.refWatcher(this).listenerServiceClass(DisplayLeakService.class)
//                .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
//                .buildAndInstall();
    }
}
