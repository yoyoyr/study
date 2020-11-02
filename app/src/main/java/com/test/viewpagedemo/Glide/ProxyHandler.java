package com.test.viewpagedemo.Glide;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    Object object;

    public ProxyHandler(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        long start = System.currentTimeMillis();
        Object ret = method.invoke(object, objects);
        System.out.println("time =  " + (System.currentTimeMillis() - start));
        return null;
    }
}
