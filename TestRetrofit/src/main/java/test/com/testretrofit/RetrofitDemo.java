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

        System.out.println(merge());
//        File file = new File("");
//        ROOT_PATH = file.getAbsolutePath();
//        String pkFilePath = ROOT_PATH + "/rsa_private_key2.pem";
//
//        String srcDir = ROOT_PATH + "/TMS-061803";
//        String targetDir = ROOT_PATH + "/result";
//        System.out.println("Hello World!");
//        String[] result = CommonPosUtils.sign(pkFilePath, srcDir, targetDir);
//        System.out.println(result[0] + "," + result[1]);
    }

    //    nums1 = [1,2,3,0,0,0], m = 3
//    nums2 = [2,5,6],       n = 3
    public static boolean merge() {
        String datas = "A man, a plan, a canal: Panama".toLowerCase().replaceAll(" ","").replaceAll("[^a-zA-Z0-9]", "");
        System.out.println("datas = "+datas);
        int point1 = 0;
        int point2 = datas.length()-1;
        while (point1 < point2) {
            if (datas.charAt(point1++) != datas.charAt(point2--))
                return false;
        }
        return true;
    }
}
