package com.hook;

import android.app.Application;
import android.content.Context;
import android.os.Debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.annotation.ApplicationAsyncInit;
import com.test.viewpagedemo.BaseApp;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;
import java.io.IOException;

public class App extends Application implements BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        startTract(this);
        if (BuildConfig.isModule) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.init(this);
        }
    }

    @Override
    @ApplicationAsyncInit
    public void initSyn(Application application) {
        LoggerUtils.LOGD("syn");
    }

    @Override
    public void initAsyn(Application application) {

    }


    private void startTract(Context context) {
        //启动app时间统计
        File file = new File(context.getCacheDir() + "/hello.trace");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Debug.startMethodTracing(context.getCacheDir() + "/hello.trace");
    }

}
