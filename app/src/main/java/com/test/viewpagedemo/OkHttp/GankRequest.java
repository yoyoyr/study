package com.test.viewpagedemo.OkHttp;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface GankRequest {
    String baseUrl = "https://gank.io/";

    @GET("api/random/data/Android/20")
    Observable<GankResponse> get();

    @Streaming
    @GET
    Observable<ResponseBody> downLoad(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadMul(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downWithRange(@Url String url, @Header("Range") String Range);

}
