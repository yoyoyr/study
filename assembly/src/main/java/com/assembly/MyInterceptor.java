package com.assembly;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.viewpagedemo.LoggerUtils;

@Interceptor(priority = 1)
public class MyInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LoggerUtils.LOGD("intercept path " + postcard.getPath());
        if (postcard.getPath().equals("/com/arouter")) {
            callback.onInterrupt(null);
            ARouter.getInstance().build("/com/intercept").navigation();
            return;
        }
        callback.onContinue(postcard);

    }

    @Override
    public void init(Context context) {

    }
}
