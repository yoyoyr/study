package com.test.viewpagedemo.OkHttp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class OkHttpActivity extends AppCompatActivity {


    private static final String CAPK = "30820122300d06092a864886f70d01010105000382010f003082010a02820101009959a77d441ac8cc35ff5e4e117bd72460d13f689367d2564598c7b0f68869e8c11e80c9f8b964ffea75a05021fdccde7cbca9845fa95093260d1ac1187c3d8b5b59a4d0cef8f7189978dae067256a7108090a254d900220a2036f1bb4beaa3e90ee67ebb79ff74d5721f78bdc655eba041f67d97c476581fe362a91f14959df6e5e9d3040bf134ced84350afcba313c27273ad2b1557f0edd36fda9de52d010acaf4a6668ade927f00d29ef47bc91fd08ff67795d1f435e8fbdbaec612ba13b4bd081d4e4bf967c4d243f1add59e2858603c60cfe69d9f74cd88ba9bcce062c8095d37925620319d6ef08bfc3271a9fd549a0a07684bc43abe4a99411b1758d0203010001";
    Unbinder unbinder;

    @NonNull
    static String url = "http://gank.io/api/data/Android/10/1";

    @NonNull
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            LoggerUtils.LOGD(message);
        }
    });


    @Nullable
    MediaType stream = MediaType.parse("text/x-markdown; charset=utf-8");
    @Nullable
    MediaType string = MediaType.parse("text/x-markdown; charset = utf-8");


    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        unbinder = ButterKnife.bind(this);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @OnClick({R.id.cache})
    void cache() {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .cache(new Cache(OkHttpActivity.this.getCacheDir(), 1024 * 1024 * 10))
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(GankRequest.baseUrl)
                .build();

        final GankRequest request = retrofit.create(GankRequest.class);
        final String url = "https://ww1.sinaimg.cn/large/0073sXn7gy1fwyf8dcpt0g308w0fse83";

        request.downloadMul(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseBody, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull ResponseBody body) throws Exception {
                        FileUtils.writeFile2Disk(body, FileUtils.createFile(OkHttpActivity.this, "tmp", "gif"));
                        return 1;
                    }
                })
                .subscribe();
    }

    //多线程下载
    @SuppressLint("CheckResult")
    @OnClick({R.id.mutildownLoad})
    void mutilDownLoad() {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(GankRequest.baseUrl)
                .build();

        final GankRequest request = retrofit.create(GankRequest.class);
        final String url = "https://ww1.sinaimg.cn/large/0073sXn7gy1fwyf8dcpt0g308w0fse83";


        request.downloadMul(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull ResponseBody response) throws Exception {
                        long contentLen = response.contentLength();
                        LoggerUtils.LOGD("content length = " + contentLen);

                        Observable file1 = request.downWithRange(url, "bytes=0-" + contentLen / 3)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .map(new Function<ResponseBody, String>() {
                                    @NonNull
                                    @Override
                                    public String apply(@NonNull ResponseBody response) throws Exception {
                                        return FileUtils.writeFile2Disk(response, FileUtils.createFile(OkHttpActivity.this, "tmp1", "tmp"));
                                    }
                                });

                        Observable file2 = request.downWithRange(url, "bytes = " + (contentLen / 3 + 1) + " - " + (contentLen / 3 * 2))
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .map(new Function<ResponseBody, String>() {
                                    @NonNull
                                    @Override
                                    public String apply(@NonNull ResponseBody response) throws Exception {
                                        return FileUtils.writeFile2Disk(response, FileUtils.createFile(OkHttpActivity.this, "tmp2", "tmp"));
                                    }
                                });

                        Observable file3 = request.downWithRange(url, "bytes = " + (contentLen / 3 * 2 + 1) + " - " + contentLen)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .map(new Function<ResponseBody, String>() {
                                    @NonNull
                                    @Override
                                    public String apply(@NonNull ResponseBody response) throws Exception {
                                        return FileUtils.writeFile2Disk(response, FileUtils.createFile(OkHttpActivity.this, "tmp3", "tmp"));
                                    }
                                });

                        return Observable.zip(file1, file2, file3,
                                new Function3<String, String, String, String>() {
                                    @NonNull
                                    @Override
                                    public String apply(String file1, String file2, String file3) throws Exception {
                                        return FileUtils.mergeFiles(FileUtils.createFile(OkHttpActivity.this, "downloadMerge", "gif").getAbsolutePath(),
                                                new String[]{file1, file2, file3});
                                    }
                                });
                    }
                })
                .onErrorReturn(new Function<Throwable, String>() {
                    @NonNull
                    @Override
                    public String apply(Throwable throwable) throws Exception {
                        LoggerUtils.LOGE(throwable);
                        return "666";
                    }
                })
                .subscribe();
    }

    /**
     * 混合表单和文件
     */
    @OnClick({R.id.multipartBody})
    void mulitpartBody() {

        RequestBody requestBody = null;
        try {
//            File file = new File(getCacheDir().getAbsolutePath() + "/Destop32.zip");
            File file = new File(getCacheDir().getAbsolutePath() + "/note5.txt");
            BufferedSource source = Okio.buffer(Okio.source(file));
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", "yoyo")
                    .addFormDataPart("zip", "test.zip",
                            new MulitRequestBody(MediaType.parse("application/octet-stream"),
                                    file))
//                    .addFormDataPart("zip", "test.zip",
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    source.readByteArray()))
                    .build();
        } catch (Exception e) {
            LoggerUtils.LOGE(e);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        call = client.newCall(request);
        call.enqueue(callback);

    }

    @OnClick(R.id.postForm)
    void postForm() {

        FormBody form = new FormBody.Builder()
                .add("form", "text")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("header", "yoyo")
                .post(form)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    @OnClick(R.id.http1)
    void clickHttp1() {

        Request.Builder requestBuilder = new Request.Builder()
                .addHeader("name", "yoyo");
        requestBuilder.url(url);

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        client.newCall(requestBuilder.build()).enqueue(callback);

    }

    class MulitRequestBody extends RequestBody {

        MediaType mediaType;
        File content;

        public MulitRequestBody(MediaType contentType, File content) {
            this.mediaType = contentType;
            this.content = content;
        }

        @Nullable
        @Override
        public MediaType contentType() {
            return mediaType;
        }

        @Override
        public void writeTo(@NonNull BufferedSink sink) throws IOException {
            LoggerUtils.LOGD("write-------");
            BufferedSource bufferedSource = Okio.buffer(Okio.source(content));
            BufferedSink bufferedSink = Okio.buffer(sink);
            try {
//              一个segment的大小
                byte[] tmp = new byte[1024];
                int count;
                while ((count = bufferedSource.read(tmp)) != -1) {
                    bufferedSink.write(tmp, 0, count);
                    bufferedSink.flush();
                    bufferedSource.buffer().flush();
                    LoggerUtils.LOGD("content = " + new String(tmp));
//                    LoggerUtils.LOGD("sink size = " + bufferedSink.buffer().size());
//                    LoggerUtils.LOGD("bufferedSource size = " + bufferedSource.buffer().size());
                }

            } finally {
                Util.closeQuietly(bufferedSource);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        call.cancel();
    }

    Call call;

    @NonNull
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            LoggerUtils.LOGE("fail", e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            LoggerUtils.LOGD("message = " + response.message() + ",thread = " + Thread.currentThread().getName());
            if (response.isSuccessful()) {
                LoggerUtils.LOGD("response body =" + response.body().string());
            }
        }
    };


    //    ------------------------------------------------------------添加证书
    public SSLSocketFactory createSSLSocketFactory() {

        return new OkHttpClient().sslSocketFactory();
//        try {
//            List<byte[]> CERTIFICATES_DATA = new ArrayList<>();
//            InputStream inputStream = getAssets().open("FiddlerRoot.cer");
//            addCapk(inputStream, CERTIFICATES_DATA);
//            // 添加证书
//            List<InputStream> certificates = new ArrayList<>();
//            if (!CERTIFICATES_DATA.isEmpty()) {
//                for (byte[] bytes : CERTIFICATES_DATA) {
//                    certificates.add(new ByteArrayInputStream(bytes));
//                }
//            }
//            return getSocketFactory(certificates);
//        } catch (IOException ioe) {
//            LoggerUtils.LOGE(ioe);
//            return null;
//        }
    }


    /**
     * 添加证书
     *
     * @param certificates
     */
    private SSLSocketFactory getSocketFactory(@NonNull List<InputStream> certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            try {
                for (int i = 0, size = certificates.size(); i < size; ) {
                    InputStream certificate = certificates.get(i);
                    String certificateAlias = Integer.toString(i++);
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory
                            .generateCertificate(certificate));
                    if (certificate != null)
                        certificate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addCapk(@Nullable InputStream inputStream, @NonNull List<byte[]> CERTIFICATES_DATA) {
        try {
            if (inputStream != null) {
                int ava = 0;// 数据当次可读长度
                int len = 0;// 数据总长度
                ArrayList<byte[]> data = new ArrayList<>();
                while ((ava = inputStream.available()) > 0) {
                    byte[] buffer = new byte[ava];
                    inputStream.read(buffer);
                    data.add(buffer);
                    len += ava;
                }

                byte[] buff = new byte[len];
                int dstPos = 0;
                for (byte[] bytes : data) {
                    int length = bytes.length;
                    System.arraycopy(bytes, 0, buff, dstPos, length);
                    dstPos += length;
                }
                CERTIFICATES_DATA.add(buff);
            }
        } catch (Exception e) {
            LoggerUtils.LOGE(e);
        }
    }
}
