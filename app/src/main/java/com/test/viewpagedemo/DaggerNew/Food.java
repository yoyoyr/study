package com.test.viewpagedemo.DaggerNew;

import com.test.viewpagedemo.LoggerUtils;

import javax.inject.Inject;

class Food {

    String name;

    @Inject
    public Food() {
        LoggerUtils.LOGD("Food");
        name = "yoyo";
    }
}
