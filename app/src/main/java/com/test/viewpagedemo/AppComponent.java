package com.test.viewpagedemo;

import android.support.annotation.NonNull;

import com.test.viewpagedemo.GreenDao.resource.DaoSession;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(DaggerApp application);

    @NonNull
    DaoSession getDaoSession();
}
