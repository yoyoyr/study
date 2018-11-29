package com.assembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.RouteBean;

import butterknife.ButterKnife;

@Route(path = RouteBean.modularize)
public class TestActivity extends AppCompatActivity {

    NavCallback navigationCallback = new NavCallback() {
        @Override
        public void onFound(Postcard postcard) {
            //路由成功的回调
            LoggerUtils.LOGD("onFound123");
        }

        @Override
        public void onLost(Postcard postcard) {
            //路由失败的回调
            LoggerUtils.LOGD("onLost");
        }

        @Override
        public void onArrival(Postcard postcard) {
            LoggerUtils.LOGD("onArrival");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aroute_activity_test);

        ButterKnife.bind(this);

//        findViewById(R.id.startActivityWithResult).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Postcard postcard = ARouter.getInstance().build(RouteBean.startActivityWithResult);
//                postcard.withInt("age", 1);
//                postcard.withString("name", "yoyo");
//                postcard.withString("clazz", "clazz1");
//                postcard.navigation();
//            }
//        });

        findViewById(R.id.startFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteBean.fragment).navigation();
            }
        });

        findViewById(R.id.Degrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/com/abc").navigation();
            }
        });

        findViewById(R.id.startFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = (Fragment) ARouter.getInstance().build("/com/fragment1").navigation();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content, fragment)
                        .commit();
            }
        });

        findViewById(R.id.startServiceByType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().navigation(HelloService.class)
                        .sayHello("mike");
            }
        });

        findViewById(R.id.startServiceByName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HelloService) ARouter.getInstance().build("/com/helloservice")
                        .navigation()).sayHello("yoyo");
            }
        });

        findViewById(R.id.interceptor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/com/arouter")
                        .navigation();
            }
        });

    }

//    @OnClick(R2.id.startActivityWithResult)
//    void withResult() {
//        Postcard postcard = ARouter.getInstance().build(RouteBean.startActivityWithResult);
//        postcard.withInt("age", 1);
//        postcard.withString("name", "yoyo");
//        postcard.withString("clazz", "clazz1");
//        postcard.navigation();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LoggerUtils.LOGD("requestCoder code = " + requestCode + ",result code = " + resultCode);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LoggerUtils.LOGD("has Focus = " + hasFocus);
    }

}

