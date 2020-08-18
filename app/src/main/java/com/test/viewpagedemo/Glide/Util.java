package com.test.viewpagedemo.Glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.provider.DataLoadProviderRegistry;
import com.bumptech.glide.util.MultiClassKey;
import com.test.viewpagedemo.LoggerUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static void reduce(ClassLoader classLoader, final Context context) {
//        try {
//
//            Field dataLoadProviderRegistry = Glide.class.getDeclaredField("dataLoadProviderRegistry");
//            dataLoadProviderRegistry.setAccessible(true);
//            final DataLoadProviderRegistry registry = (DataLoadProviderRegistry) dataLoadProviderRegistry.get(Glide.get(context));
//            Field providersField = DataLoadProviderRegistry.class.getDeclaredField("providers");
//            providersField.setAccessible(true);
//            Map<MultiClassKey, DataLoadProvider<?, ?>> providers =
//                    (Map<MultiClassKey, DataLoadProvider<?, ?>>) providersField.get(registry);
//            Map<MultiClassKey, DataLoadProvider<?, ?>> providerProxys = new HashMap<>(providers.size());
//
//            Method getSourceDecoder = DataLoadProvider.class.getDeclaredMethod("getSourceDecoder");
//            getSourceDecoder.setAccessible(true);
//
//            for (MultiClassKey key : providers.keySet()) {
//                final DataLoadProvider dataLoadProvider = providers.get(key);
//                final ResourceDecoder resourceDecoder = dataLoadProvider.getSourceDecoder();
//
//                final ResourceDecoder resourceDecoderProxy = (ResourceDecoder) Proxy.newProxyInstance(classLoader, new Class[]{ResourceDecoder.class}, new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        LoggerUtils.LOGV("method = " + method.getName());
//                        if ("decode".equals(method.getName())) {
//                            LoggerUtils.LOGV("rgb 565");
//                            Object arg0 = args[0];
//                            InputStream inputStream = null;
//                            if (arg0 instanceof ImageVideoWrapper) {
//                                inputStream = ((ImageVideoWrapper) arg0).getStream();
//                            } else {
//                                inputStream = (InputStream) arg0;
//                            }
//
//                            Bitmap bitmap = Downsampler.AT_LEAST.decode(inputStream, Glide.get(context).getBitmapPool(), ((int) args[1]), ((int) args[2]), DecodeFormat.PREFER_ARGB_8888);
//                            LoggerUtils.LOGV("bitmap = " + bitmap.getByteCount());
//                            BitmapResource resource = BitmapResource.obtain(bitmap, Glide.get(context).getBitmapPool());
//                            LoggerUtils.LOGV("resource = " + resource.getSize());
//                            return resource;
////
//                        } else if ("getId".equals(method.getName())) {
//                            return "yoyo";
//                        }
//                        return method.invoke(resourceDecoder, args);
//                    }
//                });
//                DataLoadProvider provider = (DataLoadProvider<?, ?>) Proxy.newProxyInstance(classLoader, new Class[]{DataLoadProvider.class}, new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//                        LoggerUtils.LOGV("method = " + method.getName()+dataLoadProvider.toString());
//                        if ("getSourceDecoder".equals(method.getName())) {
//                            method.invoke(dataLoadProvider, args);
//                            return resourceDecoderProxy;
//                        }
//                        return method.invoke(dataLoadProvider, args);
//                    }
//                });
//
//                DataLoadProvider relt = providers.put(key, provider);
//            }
////            providersField.set(registry, providerProxys);
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        try {
            Field decodeFormat = Glide.class.getDeclaredField("decodeFormat");
            decodeFormat.setAccessible(true);
            decodeFormat.set(Glide.get(context),DecodeFormat.PREFER_RGB_565);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
