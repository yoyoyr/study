package com.app;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.annotation.ApplicationAsyncInit;
import com.assembly.BuildConfig;
import com.test.viewpagedemo.BaseApp;
import com.test.viewpagedemo.LoggerUtils;

public class ModuleApp extends Application implements BaseApp {


    static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    /**
     * 栈顶activity生命周期变化回调
     *
     * @param callback
     */
    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (!BuildConfig.isModule) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.init(this);
        }
    }

    @Override
    public void initSyn(Application app) {
        LoggerUtils.LOGD("syn");

    }

    @Override
    @ApplicationAsyncInit
    public void initAsyn(Application application) {
        LoggerUtils.LOGD("asyn");
    }
}
