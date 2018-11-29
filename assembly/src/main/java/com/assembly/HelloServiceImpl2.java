package com.assembly;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.test.viewpagedemo.LoggerUtils;

@Route(path = "/com/helloservice2")
public class HelloServiceImpl2 implements HelloService {
    @Override
    public void sayHello(String name) {
        LoggerUtils.LOGD("say2 " + name);
    }

    @Override
    public void init(Context context) {

    }
}
