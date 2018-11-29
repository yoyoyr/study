package com.hotfix.test;

import android.content.Context;

import com.annotation.ApplicationAsyncInit;
import com.test.viewpagedemo.LoggerUtils;

public class TestApt {

    @ApplicationAsyncInit
    public boolean init(Context context){
        LoggerUtils.LOGD("init");
        return true;
    }
}
