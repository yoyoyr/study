package test.com.testretrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * get post的区别
 * http://www.techweb.com.cn/network/system/2016-10-11/2407736.shtml
 */

/**
 * 基础路径区别
 * baseUrl 文件式  gank.io/abc   目录是 gank.io/abc/
 * path  绝对路径 /path   相对路径 path
 * <p>
 * 文件式+绝对路径   gank.io/path   目录式+绝对路径  gank.io/path
 * 文件式+相对路径   gank.io/path   目录式+相对路径  gank.io/abc/path
 */
public interface LXRequest {

    /**
     * @param id
     * @param page
     * @param num
     * @return
     * @Query 参数生成在url
     * @Query 参数生成在请求体
     */
    @GET("/api/data/Android/10/{id}")
    public Call<GankResponse> getCall(@Path("id") int id, @Query("page") int page, @Query("num") int num);

    @GET("api/data/Android/10/1")
    public Call<GankResponse> getMapCall(@QueryMap Map<String, String> map);

    @GET("api/data/Android/{page}/{id}")
    public Call<GankResponse> getCallAbsPath(@Path("page") int pathPage, @Path("id") int id, @Query("page") int page, @Query("num") int num);

    @HTTP(method = "GET", path = "api/data/Android/{page}/{id}", hasBody = false)
    public Call<GankResponse> getCallHttp(@Path("page") int pathPage, @Path("id") int id, @Query("page") int page, @Query("num") int num);


    /**
     * @param page
     * @param id
     * @param userName
     * @param passWord
     * @return
     * @FormUrlEncoded 作用：表示发送form-encoded的数据
     * 每个键值对需要用@Filed来注解键名，随后的对象需要提供值。
     * @Multipart 作用：表示发送form-encoded的数据（适用于 有文件 上传的场景）
     * 每个键值对需要用@Part来注解键名，随后的对象需要提供值。
     */
//    @POST("api/data/Android/{page}/{id}")
    @Headers("Authorization: authorization")//添加报文头信息
    @HTTP(method = "POST", path = "api/data/Android/{page}/{id}", hasBody = true)
    @FormUrlEncoded
    public Call<GankResponse> postCall(@Path("page") int page, @Path("id") int id, @Field("userName") String userName, @Field("passWord") String passWord);

    @POST("api/data/Android/10/1")
    @FormUrlEncoded
    public Call<GankResponse> postCallFieldMap(@FieldMap Map<String, String> map);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);


}
