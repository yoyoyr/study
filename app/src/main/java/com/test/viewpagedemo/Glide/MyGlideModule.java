package com.test.viewpagedemo.Glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.test.viewpagedemo.LoggerUtils;

import java.io.InputStream;

/**
 * 在构建glide的时候，会去解析manifest文件，读出其中的组件，并分别调用
 * 配置组件类的appluOptions()和registerComponents()方法
 */
public class MyGlideModule implements GlideModule {
    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    /**
     * setMemoryCache()
     * 用于配置Glide的内存缓存策略，默认配置是LruResourceCache。
     * <p>
     * setBitmapPool()
     * 用于配置Glide的Bitmap缓存池，默认配置是LruBitmapPool。
     * <p>
     * setDiskCache()
     * 用于配置Glide的硬盘缓存策略，默认配置是InternalCacheDiskCacheFactory。
     * <p>
     * setDiskCacheService()
     * 用于配置Glide读取缓存中图片的异步执行器，默认配置是FifoPriorityThreadPoolExecutor，也就是先入先出原则。
     * <p>
     * setResizeService()
     * 用于配置Glide读取非缓存中图片的异步执行器，默认配置也是FifoPriorityThreadPoolExecutor。
     * <p>
     * setDecodeFormat()
     * 用于配置Glide加载图片的解码模式，默认配置是RGB_565
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        LoggerUtils.LOGD("applyOptions");
        //更换glide解码图片的配置为ARGB_8888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //更换glide缓存文件的位置
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        LoggerUtils.LOGD("registerComponents");
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory());
    }
}
