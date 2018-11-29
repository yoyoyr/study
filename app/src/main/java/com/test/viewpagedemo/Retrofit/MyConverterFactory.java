package com.test.viewpagedemo.Retrofit;

import android.support.annotation.Nullable;

import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class MyConverterFactory extends Converter.Factory {

    GsonConverterFactory converterFactory;

    public static MyConverterFactory create() {
        return new MyConverterFactory();
    }

    private MyConverterFactory() {
        converterFactory = GsonConverterFactory.create();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        LoggerUtils.LOGD("type = " + type.toString());
        for (Annotation annotation :
                annotations) {
            LoggerUtils.LOGD("annotations = " + annotation.annotationType());
        }
        return converterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        LoggerUtils.LOGD("type = " + type.toString());
        for (Annotation annotation :
                parameterAnnotations) {
            LoggerUtils.LOGD("methodAnnotations = " + annotation.annotationType());
            if (annotation instanceof Body
                    && "interface okio.Source".equals(type.toString())) {
                LoggerUtils.LOGD("return RequestBodyConverter");
                return new RequestBodyConverter();
            }
        }
        return converterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    /**
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     * @Field,@Query等注解解析时调用
     */
    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }

    class RequestBodyConverter implements Converter<Source, RequestBody> {

        /**
         * @param value 传入函数的值
         * @return
         * @throws IOException
         */
        @Override
        public RequestBody convert(final Source value) throws IOException {

            RequestBody requestBody = new RequestBody() {
                @Nullable
                @Override
                public MediaType contentType() {
                    return MediaType.parse("application/octet-stream");
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    LoggerUtils.LOGD("write body");
                    byte[] bs = new byte[1024];
                    BufferedSink bufferedSink = Okio.buffer(sink);
                    BufferedSource bufferedSource = Okio.buffer(value);
                    int count;
                    while ((count = bufferedSource.read(bs)) != -1) {
                        bufferedSink.write(bs, 0, count);
                        bufferedSink.flush();
                    }
                }
            };

            return requestBody;
        }

    }
}
