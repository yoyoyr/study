package com.assembly;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.test.viewpagedemo.LoggerUtils;

@Route(path = "/com/helloservice")
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        LoggerUtils.LOGD("say " + name);
    }

    @Override
    public void init(Context context) {

    }
}
