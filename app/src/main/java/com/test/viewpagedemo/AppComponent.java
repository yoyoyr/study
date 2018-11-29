package com.test.viewpagedemo;

import com.test.viewpagedemo.GreenDao.resource.DaoSession;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(DaggerApp application);

    DaoSession getDaoSession();
}
