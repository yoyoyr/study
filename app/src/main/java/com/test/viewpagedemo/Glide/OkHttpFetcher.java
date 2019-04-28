package com.test.viewpagedemo.Glide;

import android.support.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpFetcher implements DataFetcher<InputStream> {

    OkHttpClient okHttpClient;
    GlideUrl glideUrl;
    boolean isCancel;
    InputStream inputStream;

    public OkHttpFetcher(OkHttpClient okHttpClient, GlideUrl glideUrl) {
        this.okHttpClient = okHttpClient;
        this.glideUrl = glideUrl;
        isCancel = false;
    }


    @Nullable
    @Override
    public InputStream loadData(Priority priority) throws Exception {

        LoggerUtils.LOGD("loadData");
        if (isCancel) {
            LoggerUtils.LOGW("cancel");
            return null;
        }

        Request.Builder requestBuild = new Request.Builder().url(glideUrl.toStringUrl());

        for (Map.Entry<String, String> headerEntry : glideUrl.getHeaders().entrySet()) {
            requestBuild.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }

        Response response = okHttpClient.newCall(requestBuild.build()).execute();

        if (response == null || !response.isSuccessful()) {
            LoggerUtils.LOGE("network error");
            return null;
        }

        return ContentLengthInputStream.obtain(response.body().byteStream(), response.body().contentLength());
    }

    @Override
    public void cleanup() {
        LoggerUtils.LOGD("cleanup");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        LoggerUtils.LOGD("getId");
        return glideUrl.getCacheKey();
    }

    @Override
    public void cancel() {
        LoggerUtils.LOGD("cancel");
        isCancel = true;
    }
}
