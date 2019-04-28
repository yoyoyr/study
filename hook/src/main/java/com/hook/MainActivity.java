package com.hook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.Reflector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @NonNull
    Bean bean = new Bean();
    @NonNull
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook_activity_main);
        bean.setData(data);
        ((TextView) findViewById(R.id.tv)).setText(bean.getData().say() + "-" + bean.getData().speak());
    }

    public void hookFlect(View view) throws Reflector.ReflectedException {
        Reflector.on(Bean.class)
                .field("data")
                .set(bean, new DataProxy(data));
        LoggerUtils.LOGD("data = " + bean.data);

        ((TextView) findViewById(R.id.tv)).setText(bean.getData().say() + "-" + bean.getData().speak());
    }


    public void hookProxy(View view) {
        Inter proxyBean = (Inter) Proxy.newProxyInstance(Bean.class.getClassLoader(), new Class[]{Inter.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, @NonNull Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getData")) {
                    LoggerUtils.LOGD("proxy getData");
                    return new DataProxy(new Data());
                }
                return method.invoke(proxy, args);
            }
        });

        ((TextView) findViewById(R.id.tv)).setText(proxyBean.getData().say() + "-" + proxyBean.getData().speak());
    }
}
