package com.test.viewpagedemo.Lifecycle;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.test.viewpagedemo.LoggerUtils;

/**
 * 需要引入：implementation "android.arch.lifecycle:common-java8:1.1.1"
 * 观察activity或者fragment的生命周期，减少代码。
 */
public class Observer implements DefaultLifecycleObserver {


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LoggerUtils.LOGV("当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

}
