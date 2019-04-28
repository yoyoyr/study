package com.test.viewpagedemo.Glide;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GlideActivity extends AppCompatActivity {

    Unbinder unbinder;

    @Nullable
    @BindView(R.id.load)
    Button button;

    @Nullable
    @BindView(R.id.imageView)
    ImageView imageView;

    @Nullable
    @BindView(R.id.layout)
    MyLinearLayout linearLayout;

//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

    List<String> urls;

    @NonNull
    String jpg = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    @NonNull
    String gif = "http://p1.pstatp.com/large/166200019850062839d3";

    RequestManager requestManager;
    ImageLoaderAdapter imageLoaderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_base1);

        unbinder = ButterKnife.bind(this);
        requestManager = Glide.with(this);
        urls = new ArrayList<>();
        imageLoaderAdapter = new ImageLoaderAdapter(urls);
//        recyclerView.setAdapter(imageLoaderAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick({R.id.circleImage})
    void circleImage() {
        Glide.with(this)
                .load(jpg)
//                .transform(new SelfBitmapTransformation(this))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleCrop(this))
                .skipMemoryCache(true)
                .into(imageView);
    }

    //图片等比缩放，知道填充满整个imageview。如果图片宽度比较小，则按照图片宽度填充imageview为止。高比较小，这同理
//    @OnClick({R.id.centerCrop})
//    void centerCrop() {
//        Glide.with(this)
//                .load(jpg)
//                .centerCrop()
//                .into(imageView);
//    }

    //图片等比缩放，如果图片宽度比较大，则按照图片宽度填充imageview为止。高比较大，这同理
    @OnClick({R.id.fitCenter})
    void fitCenter() {
        Glide.with(this)
                .load(jpg)
                .fitCenter()
                .into(imageView);
    }

    @OnClick({R.id.preLoad})
    void preLoad() {
        //最好指定source
        Target target = Glide.with(this)
                .load(jpg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();

        //参数为with,height必须在子线程执行.取出缓存的时候，需要指定缓存策略是DiskCacheStrategy.SOURCE
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> fileFutureTarget = Glide.with(GlideActivity.this)
                        .load(jpg)
                        .downloadOnly(200, 200);
                File file = null;
                try {
                    file = fileFutureTarget.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                LoggerUtils.LOGD("file path = " + file.getAbsolutePath());
            }
        }).start();
    }

    @OnClick({R.id.viewTarget})
    void viewTarget() {
        Glide.with(this)
                .load(gif)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        LoggerUtils.LOGE(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //返回false表示事件还没有处理完毕，会接着下发处理
                        return false;
                    }
                })
                .into(linearLayout.getViewTarget());
    }

    @OnClick(R.id.gif)
    void gif() {
        File gifFile = new File(getCacheDir().getAbsoluteFile() + "/big.gif");
        Glide.with(this)
                .load(gifFile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    @OnClick({R.id.simpleTarget})
    void simpleTarger() {
        Glide.with(this)
                .load(jpg)
                .into(mySimpleTarger);
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.load)
    public void loadUrl() {
        Glide.with(this)
                .load(jpg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    //GlideDrawable可用以动图和静态图片。如果指定了asBitmap，可以用BitmapDrawable
    @NonNull
    SimpleTarget<GlideDrawable> mySimpleTarger = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
            imageView.setImageDrawable(resource);
        }
    };

    @NonNull
    Target<Drawable> downloadFile = new Target<Drawable>() {
        Request request;

        @Override
        public void onLoadStarted(Drawable placeholder) {
            LoggerUtils.LOGD("onLoadStarted");
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {

            LoggerUtils.LOGD("onLoadFailed");
        }

        @Override
        public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {

        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

            LoggerUtils.LOGD("onLoadCleared");
        }

        /**
         * 如果没有调用override()设置宽高，这回调用该方法确定加载图片宽高
         *
         * @param cb
         */
        @Override
        public void getSize(SizeReadyCallback cb) {

            LoggerUtils.LOGD("getSize");
//            cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        }

        @Override
        public void setRequest(Request request) {

            LoggerUtils.LOGD("setRequest");
            this.request = request;
        }

        @Override
        public Request getRequest() {
            LoggerUtils.LOGD("getRequest");
            return request;
        }

        //一下三个函数为fragment或activity生命周期回调，无需处理
        @Override
        public void onStart() {
            LoggerUtils.LOGD("onStart");

        }

        @Override
        public void onStop() {
            LoggerUtils.LOGD("onStop");

        }

        @Override
        public void onDestroy() {
            LoggerUtils.LOGD("onDestroy");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        LoggerUtils.LOGD("destory");
        //通过这个方法清除内存缓存
        Glide.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LoggerUtils.LOGD("level = " + level);
    }
}
