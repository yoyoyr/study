package com.test.viewpagedemo.GreenDao;

import com.test.viewpagedemo.AppComponent;

import dagger.Component;

@Component(modules = GreenDaoModule.class, dependencies = AppComponent.class)
public interface GreenDaoComponent {

    void inject(GreenDaoActivity activity);
}
