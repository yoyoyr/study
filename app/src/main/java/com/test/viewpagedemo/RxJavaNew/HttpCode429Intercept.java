package com.test.viewpagedemo.RxJavaNew;

import android.support.annotation.NonNull;

import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCode429Intercept implements Interceptor {

    //429返回后重试次数
    final static int RETRYCOUNT = 3;
    //重发默认时间
    final static int TIME = 10;

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.code() == 429) {
            for (int count = 0; count < RETRYCOUNT; ++count) {

                int time = TIME;
                try {
                    String retry = response.header("Retry-After");
                    if (retry != null && !"".equalsIgnoreCase(retry)) {
                        time = Integer.parseInt(retry);
                    }
                } catch (Exception e) {
                    LoggerUtils.LOGW(e.getMessage());
                    time = TIME;
                }
                time *= 1000;
                try {
                    LoggerUtils.LOGV("thread = " + Thread.currentThread().getName());
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    break;
                }
            }
        }
        return response;
    }
}
