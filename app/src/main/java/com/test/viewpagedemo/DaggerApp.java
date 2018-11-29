package com.test.viewpagedemo;

import android.content.Context;
import android.os.Debug;
import android.support.multidex.MultiDex;

import com.annotation.ApplicationAsyncInit;
import com.igexin.sdk.PushManager;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.test.viewpagedemo.GreenDao.resource.DaoSession;
import com.test.viewpagedemo.mqttGetTui.DemoIntentService;
import com.test.viewpagedemo.mqttGetTui.DemoPushService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

public class DaggerApp {

    @Inject
    DaoSession daoSession;

    static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    //提供给外层RefWatcher，监控变量是否内存泄露
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    private static RefWatcher refWatcher;


    @ApplicationAsyncInit
    public void init(Context context) {
        //        startTract();
        //使应用支持分包
        MultiDex.install(context);
        initCompoment(context);
        initWalle(context);
//        initPush(context);
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

    private void initWalle(Context context) {
        LoggerUtils.LOGD("---------init walle");
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(context);
        if (channelInfo != null) {
            String channel = channelInfo.getChannel();
            Map<String, String> extraInfo = channelInfo.getExtraInfo();
            LoggerUtils.LOGD("channel = " + channel + ",extra info size = " + extraInfo.size());
        }
    }

    private void initPush(Context context) {
        //        890ms
        LoggerUtils.LOGD("---------init push");
        //开启个推服务
        PushManager.getInstance().initialize(context, DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(context, DemoIntentService.class);
        LoggerUtils.LOGD("---------push end");
    }

    private void initCompoment(Context context) {
        LoggerUtils.LOGD("---------init cpmponent");
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context, "test.db"))
                .build();
        appComponent.inject(this);

        LoggerUtils.LOGD("---------init leakcanary");
        refWatcher = LeakCanary.refWatcher(context).listenerServiceClass(DisplayLeakService.class)
                .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
                .buildAndInstall();
    }


}
