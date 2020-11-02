package com.test.viewpagedemo;

import android.content.Context;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.annotation.ApplicationAsyncInit;

import com.github.moduth.blockcanary.BlockCanary;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.test.viewpagedemo.GreenDao.resource.DaoSession;
//import com.test.viewpagedemo.mqttGetTui.DemoIntentService;
//import com.test.viewpagedemo.mqttGetTui.DemoPushService;

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

    @ApplicationAsyncInit
    public void init(@NonNull Context context) {
        startTract(context);
        //使应用支持分包
        MultiDex.install(context);
        initCompoment(context);
        initWalle(context);
        startTract(context);
        BlockCanary.install(context, new AppBlockCanaryContext()).start();
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

    private void initWalle(@NonNull Context context) {
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
//        PushManager.getInstance().initialize(context, DemoPushService.class);
//        PushManager.getInstance().registerPushIntentService(context, DemoIntentService.class);
        LoggerUtils.LOGD("---------push end");
    }

    private void initCompoment(@NonNull Context context) {
        LoggerUtils.LOGD("---------init cpmponent");
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context, "test.db"))
                .build();
        appComponent.inject(this);

        LoggerUtils.LOGD("---------init leakcanary");
    }


}
