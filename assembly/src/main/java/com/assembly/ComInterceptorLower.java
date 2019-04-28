package com.assembly;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.test.viewpagedemo.LoggerUtils;

@Interceptor(priority = 3)
public class ComInterceptorLower implements IInterceptor {

    @Override
    public void process(@NonNull Postcard postcard, @NonNull InterceptorCallback callback) {
        LoggerUtils.LOGD("intercept path " + postcard.getPath());
        if (postcard.getPath().equals("/com/arouter")) {
            callback.onInterrupt(new NullPointerException("yoyo"));
            return;
        }
        callback.onContinue(postcard);

    }

    @Override
    public void init(Context context) {

    }
}
