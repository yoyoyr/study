package com.test.viewpagedemo.Glide;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetroRequest {


    String URL = "https://gank.io/api/data/福利/100/1";
    String BASEURL = "https://gank.io/";

    @NonNull
    @GET("api/data/福利/100/1")
    Observable<GankBean> getUrls();

    @NonNull
    @GET
    Observable<ResponseBody> loadImage(@Url String url);

}
