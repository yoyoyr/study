package com.hook;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.annotation.ApplicationAsyncInit;
import com.test.viewpagedemo.BaseApp;
import com.test.viewpagedemo.LoggerUtils;

public class App extends Application implements BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
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
}
