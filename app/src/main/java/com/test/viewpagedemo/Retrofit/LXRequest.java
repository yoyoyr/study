package com.test.viewpagedemo.Retrofit;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import okio.Source;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * get post的区别
 * http://www.techweb.com.cn/network/system/2016-10-11/2407736.shtml
 */
public interface LXRequest {

    @NonNull
    @GET("api/data/Android/10/{id}")
    Call<GankResponse> get(@Path("id") String id, @Query("name") String name);

    @NonNull
    @POST("api/data/Android/10/{id}")
    @FormUrlEncoded
    Call<GankResponse> post(@Path("id") int id, @Field("name") String name);

    @NonNull
    @POST("api/data/Android/10/{id}")
    @Streaming
    Observable<GankResponse> multipart(@Path("id") int id, @Body Source body);

    @NonNull
    @GET("s?ie=utf-8&")
    Observable<ResponseBody> baidu();
}
