package com.test.viewpagedemo.Retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;
import com.test.viewpagedemo.Retrofit.gson.Book;
import com.test.viewpagedemo.Retrofit.gson.BookSerializer;
import com.test.viewpagedemo.Retrofit.gson.BookTypeAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 打印网络请求日志
 * 1.compile 'com.squareup.okhttp3:logging-interceptor:3.3.1'
 */
public class RetrofitAndRxJava extends AppCompatActivity {

    //    OkHttpClient okHttpClient;
//    Retrofit retrofit;
    //打印请求日志
    @NonNull
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            LoggerUtils.LOGD("NET MESSAGE=========" + message);
        }
    });
    @NonNull
    String baseUrl = "http://gank.io";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_rf);
        ButterKnife.bind(this);

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @NonNull
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onStart() {
        super.onStart();
        LoggerUtils.LOGD("time");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoggerUtils.LOGD("Runnable time");
            }
        }, 5000);
    }

    @OnClick(R.id.gson)
    void gson() {
        String data = "        {\n" +
                "            \"title\": \"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\n" +
                "                \"isbn-10\": \"032133678X\",\n" +
                "                \"isbn-13\": \"978-0321336781\",\n" +
                "                \"authors\": [\n" +
                "            \"Joshua Bloch\",\n" +
                "                    \"Neal Gafter\"\n" +
                "            ]\n" +
                "        }";

        LoggerUtils.LOGD("---------------------------------------api");
        Gson gson = new Gson();
        Book book = gson.fromJson(data, Book.class);
        LoggerUtils.LOGD("book = " + book.toString());
        LoggerUtils.LOGD("book to json = " + gson.toJson(book));


        LoggerUtils.LOGD("---------------------------------------serializer");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Book.class, new BookSerializer());
        Gson myGson = gsonBuilder.create();
        Book b = myGson.fromJson(data, Book.class);
        String result = myGson.toJson(b);
        LoggerUtils.LOGD("b = " + b.toString());
        LoggerUtils.LOGD("serialize result = " + result);
        LoggerUtils.LOGD("serialize result = " + myGson.fromJson(result, Book.class));


        LoggerUtils.LOGD("---------------------------------------TypeAdapter");
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Book.class, new BookTypeAdapter());
        Gson gsAdapter = gb.create();
        Book bAdapter = gsAdapter.fromJson(data, Book.class);
        result = gsAdapter.toJson(bAdapter);
        LoggerUtils.LOGD("bAdapter = " + bAdapter.toString());
        LoggerUtils.LOGD("adapter result = " + result);
        LoggerUtils.LOGD("serialize result = " + gsAdapter.fromJson(result, Book.class));
    }

    @OnClick(R.id.stream)
    void stream() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MyConverterFactory.create())
                .build();

        LXRequest lxRequest = retrofit.create(LXRequest.class);

        File file = new File(getCacheDir().getAbsolutePath() + "/note5.txt");
        BufferedSource source = null;
        try {
            source = Okio.buffer(Okio.source(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Disposable disposable = lxRequest.multipart(1, source)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<GankResponse>() {
                    @Override
                    public void accept(@NonNull GankResponse gankResponse) throws Exception {
                        LoggerUtils.LOGD("body = " + gankResponse.toString());
                    }
                });
    }

    @OnClick(R.id.post)
    void post() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        LXRequest request = retrofit.create(LXRequest.class);
        Call<GankResponse> call = request.post(1, "yoyo");
        call.enqueue(new Callback<GankResponse>() {
            @Override
            public void onResponse(Call<GankResponse> call, @NonNull Response<GankResponse> response) {
                LoggerUtils.LOGD("message = " + response.message()
                        + ",thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<GankResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.get)
    void get() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MyConverterFactory.create())
                .client(client)
                .build();

        final LXRequest request = retrofit.create(LXRequest.class);
        Call<GankResponse> call = request.get("1", "yoyo");
        call.enqueue(new Callback<GankResponse>() {
            @Override
            public void onResponse(Call<GankResponse> call, @NonNull Response<GankResponse> response) {
                LoggerUtils.LOGD("message = " + response.message());
                LoggerUtils.LOGD("body = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<GankResponse> call, Throwable t) {

            }
        });
    }

}
