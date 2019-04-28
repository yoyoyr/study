package com.test.viewpagedemo.OkHttp;

import android.support.annotation.NonNull;

import com.test.viewpagedemo.LoggerUtils;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkhttpClient extends OkHttpClient {
    @Override
    public Call newCall(@NonNull Request request) {
        LoggerUtils.LOGD("add cache control");
        CacheControl cacheControl = new CacheControl.Builder()
                .noCache()
                .build();
        Request newRequest = request.newBuilder().cacheControl(cacheControl).build();
        return super.newCall(request);
    }
}
