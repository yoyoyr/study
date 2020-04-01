package com.test.viewpagedemo.AOP;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aop.CheckPermission;
import com.aop.ClickButton;
import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

public class AOPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LoggerUtils.LOGD("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);

        findViewById(R.id.start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
                try {
                    doSomething();
                } catch (Exception e) {
                    LoggerUtils.LOGE(e);
                }
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout("yoyo");
                param(1, "yoyo");
            }
        });
    }

    @CheckPermission(permission = "com.ums.upos.api.permission.SET_LOGRECORDER")
    public void doSomething() {
        LoggerUtils.LOGD("has permission do something");
    }

    @NonNull
    public String param(int i, String s) {
        LoggerUtils.LOGD("param");
        return s + i;
    }

    public void logout(String data) {
        LoggerUtils.LOGD("logout");
//        return "hello ";
    }

    @ClickButton
    private void clickButton() {
        LoggerUtils.LOGD("click button");
    }


}

