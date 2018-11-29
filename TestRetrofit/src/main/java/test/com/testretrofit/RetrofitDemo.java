package test.com.testretrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https加密算法流程
 * 1.浏览器将自己支持的一套加密算法、HASH算法发送给网站。
 * 2.网站从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给浏览器。证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息。
 * 3.浏览器获得网站证书之后，开始验证证书的合法性，如果证书信任，则生成一串随机数字作为通讯过程中对称加密的秘钥。然后取出证书中的公钥，将这串数字以及HASH的结果进行加密，然后发给网站。
 * 4.网站接收浏览器发来的数据之后，通过私钥进行解密，然后HASH校验，如果一致，则使用浏览器发来的数字串使加密一段握手消息发给浏览器。
 * 5.浏览器解密，并HASH校验，没有问题，则握手结束。接下来的传输过程将由之前浏览器生成的随机密码并利用对称加密算法进行加密。
 */
public class RetrofitDemo {
    public static void main(String[] args) {

        String data = "9613";
//        initRetrofit();
//        LXRequest request = retrofit.create(LXRequest.class);
//        Call<GankResponse> call = request.getCall(1, 2, 3);
//        call.enqueue(callback);

//        Call<GankResponse> call1 = request.getCallAbsPath(10, 1, 5, 6);
//        call1.enqueue(callback);

//        Call<GankResponse> call = request.getCallHttp(10, 1, 7, 8);
//        call.enqueue(callback);

//        Call<GankResponse> call = request.postCall(10, 1, "yoyo", "123");
//        call.enqueue(callback);

//        Map<String, String> maps = new HashMap<>();
//        maps.put("user", "yoyo");
//        maps.put("passWord", "123456");
//        maps.put("id", "1");
//        Call<GankResponse> call = request.getMapCall(maps);
//        call.enqueue(callback);


    }

    static Retrofit retrofit;
    static Callback callback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {

        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };

    private static void initRetrofit() {
        //打印请求日志
        LogInterceptor loggingInterceptor = new LogInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)// 在此处添加拦截器即可，默认日志级别为BASIC
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


//设置证书
//    public OkHttpClient setCard() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            String certificateAlias = Integer.toString(0);
//            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(getAssets().open("daodianwang.cer")));//拷贝好的证书
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            final TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(null,trustManagerFactory.getTrustManagers(),new SecureRandom());
//            builder.sslSocketFactory(sslContext.getSocketFactory());
//            builder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String s, SSLSession sslSession) {
//                    return true;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return builder.build();
//    }
}
