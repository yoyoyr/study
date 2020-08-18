package test.com.testretrofit;

import android.support.annotation.NonNull;

import com.newpostech.commonpos.CommonPosUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitDemo {
    static int count = 0;
    static int count2 = 0;

    //    private static String pkFilePath = "rsa_private_key2.pem";
//
//    private static String srcDir = "TMS-061803";
    //    private static String targetDir = "D:\\1panjianping\\UMS-test\\pkg\\sgn";
    private static String ROOT_PATH; // 当前路径

    public static void main(String[] args) {

        File file = new File("");
        ROOT_PATH = file.getAbsolutePath();
        String pkFilePath = ROOT_PATH + "/rsa_private_key2.pem";

        String srcDir = ROOT_PATH + "/TMS-061803";
        String targetDir = ROOT_PATH + "/result";
        System.out.println("Hello World!");
        String[] result = CommonPosUtils.sign(pkFilePath, srcDir, targetDir);
        System.out.println(result[0] + "," + result[1]);
    }


}
