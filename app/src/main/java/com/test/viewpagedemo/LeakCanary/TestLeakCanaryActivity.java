package com.test.viewpagedemo.LeakCanary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.study.point.R;
import com.test.viewpagedemo.DaggerApp;
import com.test.viewpagedemo.GreenDao.User;
import com.test.viewpagedemo.LoggerUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 内存泄露的根本原因  短生命周期的对象被长生命周期的对象持有
 * 常见情况
 * 1.静态变量
 * 2.异步任务+非静态内部类（内部类会持有外部类的引用），常见常见有RxJava，handler执行异步任务
 * <p>
 * 原理:基于弱引用和引用队列判断
 * WeakReference创建时会传入一个 ReferenceQueue对象。当被 WeakReference被GC的时候,
 * 就会把该对象添加到 ReferenceQueue中。
 * 当 GC 过后对象一直不被加入 ReferenceQueue,它可能存在内存泄漏
 */
public class TestLeakCanaryActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.tv)
    TextView textView;

    @NonNull
    static User user = new User();

    //非静态内部类会持有外部类的this应用，Meaasge又会持有handler引用。
    //所以，当Meessage的生命周期比Activity长时，就会内存泄露。
    //解决方法
    // 1.静态内部类
    // 2.弱引用Activity
    // 3.清除相关消息   最好的方式。因为如果发送到消息队列是一个runnable对象，则
    //                  runnable最为内部类还是会引用到外部的ACtivity，用以上的两种方式还是会内存泄漏

    @NonNull
    MyHandler myHandler = new MyHandler();

    static class MyHandler extends Handler {
    }

    @NonNull
    Handler handler = new Handler();
    @NonNull
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LoggerUtils.LOGD("set text view " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            textView.setText("handle message");
        }
    };


    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leakcanary);

        ButterKnife.bind(this);
        DaggerApp.getRefWatcher().watch(this);
        DaggerApp.getRefWatcher().watch(user);

    }

    @OnClick({R.id.image})
    void image() {
        File file = new File(getCacheDir() + "/image.jpg");
        LoggerUtils.LOGD("file = " + file.getAbsolutePath());
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LoggerUtils.LOGD("requestCode = " + requestCode + ",resultCode = " + resultCode);
    }

    @OnClick({R.id.handler})
    void click() {
        LoggerUtils.LOGD("click");
//        myHandler.postDelayed(runnable, 1000 * 20);
        myHandler.post(runnable);
//        myHandler.sendEmptyMessage(1);


    }

    @OnClick({R.id.rxjava})
    void rxjava() {
//        使用RxJava，ObservableOnSubscribe和Consumer等匿名内部类还是会持有Activity引用
//        所以需要disposable在activity退出的时候关闭任务，避免内存泄露
        disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                try {
                    Thread.sleep(1000 * 20);
                } catch (InterruptedException e) {
                    LoggerUtils.LOGE(e);
                }
                LoggerUtils.LOGD("send 1");
                emitter.onNext(1);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        try {
                            LoggerUtils.LOGD("recv = " + integer);
                            textView.setText("recv 1");
                        } catch (Exception e) {
                            LoggerUtils.LOGE(e);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoggerUtils.LOGD("remove message");
//        disposable.dispose();
        myHandler.removeCallbacks(runnable);
    }
}
