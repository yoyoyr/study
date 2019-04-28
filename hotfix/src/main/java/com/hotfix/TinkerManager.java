package com.hotfix;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.test.viewpagedemo.LoggerUtils;

public class TinkerManager {

    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;

    private static CustomPatchListener mPatchListener;

    /**
     * 完成Tinker的初始化
     *
     * @param applicationLike
     */
    public static void installTinker(@NonNull ApplicationLike applicationLike, Context context) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }
        mPatchListener = new CustomPatchListener(context);

        Tinker tinker= TinkerInstaller.install(applicationLike,
                new DefaultLoadReporter(context),//加载过程中的事件监听
                new DefaultPatchReporter(context),//patch文件合成阶段事件监听
                mPatchListener,
                CustomResultService.class,
                new UpgradePatch()); //完成Tinker初始化

        isInstalled = true;
    }

    //完成Patch文件的加载
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            LoggerUtils.LOGD("install tinker");
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    //通过ApplicationLike获取Context
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}
