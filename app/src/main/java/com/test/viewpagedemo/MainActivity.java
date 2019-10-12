package com.test.viewpagedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hotfix.TinkerManager;
import com.study.point.BuildConfig;
import com.study.point.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static boolean flg = false;
    AlertDialog alertDialog;
    AlertDialog errorDialog;
    String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(new MainAdapter(this));

        alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setPositiveButton("资源初始化中", null)
                .create();
        errorDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setPositiveButton("错误", null)
                .create();

    }

    @OnClick(R.id.hotfix)
    void hotfix() {


//        加载热修复文件 /storage/emulated/0/Android/data/com.androidstudypoint/cache/patch_signed.apk
        mPath = getExternalCacheDir().getAbsolutePath() + File.separatorChar + "patch_signed.apk";
        LoggerUtils.LOGD("path = " + mPath);
        File patchFile = new File(mPath);
        if (patchFile.exists()) {
            TinkerManager.loadPatch(mPath);
            Toast.makeText(this, "File Exists,Please wait a moment ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File No Exists", Toast.LENGTH_SHORT).show();
        }
    }


    //aroute初始化时间接近2s，用异步方式解决
    @SuppressLint("CheckResult")
    @OnClick(R.id.arouter)
    void initModuleAsync() {

        long start = System.currentTimeMillis();
        LoggerUtils.LOGD("isModule = " + BuildConfig.isModule);
        if (BuildConfig.isModule) {
            List observables = new ArrayList<ObservableSource>();
            observables.add(Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Object> emitter) {
                    LoggerUtils.LOGD("work in " + Thread.currentThread().getName());
                    showDialog();
                    emitter.onNext(true);
                }
            }).subscribeOn(AndroidSchedulers.mainThread()));

            observables.add(Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<Object> emitter) {
                    LoggerUtils.LOGD("init arouter work in " + Thread.currentThread().getName());
                    ARouter.openLog();
                    ARouter.openDebug();
                    ARouter.init(MainActivity.this.getApplication());
                    emitter.onNext(true);
                }
            }).subscribeOn(Schedulers.io()));
//            for (final String clazz : AppConfig.initApps) {
//                Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                        LoggerUtils.LOGD("-------------init clazz " + clazz);
//                        try {
//                            ((BaseApp) Reflector.on(clazz)
//                                    .constructor()
//                                    .newInstance())
//                                    .initSyn(MainActivity.this.getApplication());
//                            LoggerUtils.LOGD("-------------end clazz " + clazz);
//                            emitter.onNext(true);
//                        } catch (Reflector.ReflectedException e) {
//                            e.printStackTrace();
//                            emitter.onNext(false);
//                        }
//                    }
//                }).subscribeOn(Schedulers.io());
//                observables.add(observable);
//            }
            Observable.zip(observables, new Function<Object[], Boolean>() {
                @Override
                public Boolean apply(@NonNull Object[] aBoolean) throws Exception {
                    boolean flag = true;
                    for (int i = 0; i < aBoolean.length; ++i) {
                        LoggerUtils.LOGD("result = " + (boolean) aBoolean[i]);
                        flag &= (boolean) aBoolean[i];
                    }
                    return flag;
                }
            }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if ((boolean) o) {
                                LoggerUtils.LOGD("----------------init finish");
                                dismissDialog();
                            } else {
                                LoggerUtils.LOGD("----------------init fail");
                                errorDialog();
                            }
                        }
                    });
        }
        LoggerUtils.LOGD("reflect time = " + (System.currentTimeMillis() - start));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        LoggerUtils.LOGD("---------app visiable");
        super.onWindowFocusChanged(hasFocus);
        if (!flg) {
            flg = true;
            Debug.stopMethodTracing();
        }

    }

    private void showDialog() {
        alertDialog.show();
    }

    private void dismissDialog() {
        alertDialog.dismiss();
    }

    private void errorDialog() {
        alertDialog.dismiss();
        errorDialog.show();
    }


//    hook
//    @NonNull
//    @Override
//    public AppCompatDelegate getDelegate() {
//        if (delegateImplV9 != null) {
//            LoggerUtils.LOGD("delegate aready build");
//            return (AppCompatDelegate) delegateImplV9;
//        }
//        try {
//            long time = System.currentTimeMillis();
//            delegateImplV9 = Reflector.on("android.support.v7.app.AppCompatDelegateImplV9")
//                    .constructor(Context.class, Window.class, AppCompatCallback.class)
//                    .newInstance(this, this.getWindow(), this);
//            Reflector.on("android.support.v7.app.AppCompatDelegateImplV9")
//                    .field("mSubDecorInstalled")
//                    .set(delegateImplV9, true);
//            LoggerUtils.LOGD("reflect coast time = " + (System.currentTimeMillis() - time)
//                    + ",mSubDecorInstalled = " + Reflector.with(delegateImplV9).field("mSubDecorInstalled").get());
//            return (AppCompatDelegate) delegateImplV9;
//        } catch (Reflector.ReflectedException e) {
//            LoggerUtils.LOGE("can not reflector!", e);
//        }
//        return super.getDelegate();
//    }

}
