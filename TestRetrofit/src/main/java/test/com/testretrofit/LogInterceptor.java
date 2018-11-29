package test.com.testretrofit;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {
    public static final String TAG = "LogInterceptor.java";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("SetCookie", "name:value");
        Request request = builder.build();
        StringBuilder requestBuilder = new StringBuilder("request{");
        String url = request.url().toString();
        requestBuilder.append("\n url = " + url);
        String method = request.method();
        requestBuilder.append("\n method = " + method);
        Headers headers = request.headers();

        if (headers.size() > 0) {
            requestBuilder.append("\n headers = [");
            for (int i = 0; i < headers.names().size(); i++) {
                requestBuilder.append(headers.name(i) + "-" + headers.value(i));
            }
            requestBuilder.append("]");
        }
        if (request.body() != null) {
            String body = request.body().toString();
            requestBuilder.append("body = " + body + "\n");
        }
        requestBuilder.append("\n}");
        System.out.println(requestBuilder.toString());

        Response response = chain.proceed(request);
        StringBuilder responseBuilder = new StringBuilder("response{");


        String message = response.message();
        responseBuilder.append("\nmessage = " + message);
        String result = response.isSuccessful() ? "success" : "fail";
        responseBuilder.append(",result = " + result);

        if (response.headers().size() > 0) {
            responseBuilder.append("\nheaders[");
            for (int i = 0; i < response.headers().size(); ++i) {
                responseBuilder.append("\n" + response.headers().name(i) + "-" + response.headers().value(i));
            }
            responseBuilder.append("]");
        }

//        if (response.isSuccessful() && response.body() != null) {
//            BufferedSource source = response.body().source();
//            source.request(Long.MAX_VALUE);
//            Buffer buffer = source.buffer();
//            String body = buffer.clone().readString(Charset.forName("UTF-8"));
//            responseBuilder.append("\nbody = " + body);
//            long contentLength = response.body().contentLength();
//            responseBuilder.append("content size = " + buffer.size());
//        }


        System.out.println(responseBuilder.toString());
        return response;
    }

}