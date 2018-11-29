package com.test.viewpagedemo.OkHttp;

import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggerIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        LoggerUtils.LOGD("--------------------------->request");
        LoggerUtils.LOGD("method = " + request.method() + ",url = " + request.url().toString());

        for (String name : request.headers().names()) {
            LoggerUtils.LOGD("name = " + name + ",value = " + request.headers().get(name));
        }

        if (request.body() != null) {
            LoggerUtils.LOGD("body = " + request.body().contentLength());
        }
        Response response = chain.proceed(request);

        LoggerUtils.LOGD("<---------------------------response");
        LoggerUtils.LOGD("response code = " + response.code() + ",protocol = " + response.protocol() + ",message = " + response.message());
        LoggerUtils.LOGD("response.request == resquest ? " + (request == response.request()));
        for (String name : response.headers().names()) {
            LoggerUtils.LOGD("name = " + name + ",value = " + response.headers().get(name));
        }

        if (request.body() != null) {
            LoggerUtils.LOGD("body = " + response.body().toString());
        }
        return response;
    }
}
