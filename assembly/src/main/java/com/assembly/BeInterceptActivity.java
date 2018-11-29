package com.assembly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.viewpagedemo.LoggerUtils;

import butterknife.ButterKnife;

@Route(path = "/com/arouter")
public class BeInterceptActivity extends AppCompatActivity {

    @Autowired
    int age;

    @Autowired
    HelloService helloService;

    @Autowired
    String name;

    @Autowired
    String tag;

    @Autowired
    String clazz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aroute_activity);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        try {
            LoggerUtils.LOGD("age = " + age);
            LoggerUtils.LOGD("name = " + name);
            helloService.sayHello("yoyo");
            LoggerUtils.LOGD("tag = " + tag);
            LoggerUtils.LOGD("clazz = " + clazz);
        } catch (Exception e) {

        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }

}
