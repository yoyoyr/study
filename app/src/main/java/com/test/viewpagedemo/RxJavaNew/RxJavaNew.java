package com.test.viewpagedemo.RxJavaNew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.study.point.R;
import com.test.viewpagedemo.LoggerUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.MaybeObserver;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaNew extends AppCompatActivity {

    Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_rf_new);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @OnClick(R.id.request48)
    void request48() {
        subscription.request(48);
    }

    @OnClick(R.id.backpressure)
    void backpressure() {
        //模拟不用背压
//        observableFast.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(observer);

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                LoggerUtils.LOGD("观察者可接收事件数量 = " + emitter.requested());
                boolean flag; //设置标记位控制

                // 被观察者一共需要发送500个事件
                for (int i = 0; i < 500; i++) {
                    flag = false;

                    // 若requested() == 0则不发送
                    while (emitter.requested() == 0) {
                        if (!flag) {
                            LoggerUtils.LOGD("不再发送");
                            flag = true;
                        }
                    }
                    // requested() ≠ 0 才发送
                    LoggerUtils.LOGD("发送了事件" + i + "，观察者可接收事件数量 = " + emitter.requested());
                    emitter.onNext(i);


                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        LoggerUtils.LOGD("onSubscribe");
                        subscription = s;
                        // 初始状态 = 不接收事件；通过点击按钮接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LoggerUtils.LOGD("接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LoggerUtils.LOGE("onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        LoggerUtils.LOGD("onComplete");
                    }
                });

//        flowableSyn.subscribe(subscriber);
    }

    @OnClick(R.id.selection)
    void selection() {

        //所有的都返回true，onSuccess才会接收到true
        Single all = Observable.just(1, 2, 3, 4, 5)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                });

        //观察者能够接收到5,6.能够接收到返回true，一旦条件不符合就停止
        Observable takeWhile = Observable.just(5, 6, 7, 8, 1)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        LoggerUtils.LOGD("test = " + integer);
                        return integer < 7;
                    }
                });

        //观察者能够接收到3,4,5,1,2.接收到返回false。一旦条件符合就不会停止
        Observable skipWhile = Observable.just(1, 2, 3, 4, 5, 1, 2)
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 3;
                    }
                });

        Single sequenceEqual = Observable.sequenceEqual(Observable.just(1, 2, 3), Observable.just(1, 2, 3));

        //接收到先接收的observable对象发送的数据
        List<ObservableSource<Integer>> ambs = new ArrayList<>();
        ambs.add(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS));
        ambs.add(Observable.just(4, 5, 6));
        Observable amb = Observable.amb(ambs);


//        all.subscribe(singleBoolean);
//        takeWhile.subscribe(observer);
//        skipWhile.subscribe(observer);
//        sequenceEqual.subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean o) throws Exception {
//                LoggerUtils.LOGD("equal ? " + o);
//            }
//        });
        amb.subscribe(observer);
    }

    @OnClick(R.id.fliter)
    void fliter() {
//        observable.filter(new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer i) throws Exception {
//                //返回双数
//                return i % 2 == 0;
//            }
//        }).subscribe(observer);

//        Observable.just(1, "yoyo", "yr", 1.2)
//                .ofType(String.class)
//                .subscribe(observer);

        Observable.just(1, 1, 2, 3, 3, 1, 2, 2, 4, 4)
//                .distinct()
                .distinctUntilChanged()
                .subscribe(observer);
    }

    @OnClick(R.id.function)
    void function() {
//        observable.subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        LoggerUtils.LOGD("do next on thread = " + Thread.currentThread().getName());
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .subscribe(observer);

        observable.doOnNext(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                LoggerUtils.LOGD("doOnNext i = " + o);
            }
        })
                .doAfterNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoggerUtils.LOGD("doAfterNext i = " + o);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.LOGE("doOnError");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        LoggerUtils.LOGD("dofinally");
                    }
                })
                .doOnEach(new Consumer<Notification>() {
                    @Override
                    public void accept(Notification notification) throws Exception {
                        if (notification.isOnNext()) {
                            LoggerUtils.LOGD("doOnEach i = " + notification.getValue());
                        } else if (notification.isOnError()) {
                            LoggerUtils.LOGE("doOnEach error");
                        } else {
                            LoggerUtils.LOGD("complite");
                        }
                    }
                })
//                .onErrorReturn(new Function<Throwable, Integer>() {
//                    @Override
//                    public Integer apply(Throwable throwable) throws Exception {
//                        LoggerUtils.LOGE("onErrorReturn recv error.");
//                        return 6;
//                    }
//                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        LoggerUtils.LOGE("onErrorResumeNext recv error.");
                        return Observable.just(666);
                    }
                })
//                .onExceptionResumeNext(new ObservableSource() {
//                    @Override
//                    public void subscribe(Observer observer) {
//
//                    }
//                })
//                .retry(new Predicate<Throwable>() {
//                    @Override
//                    public boolean test(Throwable throwable) throws Exception {
//                        return false;
//                    }
//                })
//                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                        return null;
//                    }
//                })
//                .repeat(5)
                .subscribe(observer);
    }

    @OnClick(R.id.zip)
    void zip() {

        //按发送顺序合并。所有时间都会发送，但只会接收入参最少个数的事件。例如例子中只会接受到3个事件。
        Observable zip = Observable.zip(
                observable1,
                observable2,
                zipSendCir,
                new Function3<String, String, String, String>() {
                    @Override
                    public String apply(String arc, String rec, String cir) throws Exception {
                        LoggerUtils.LOGD("zip arc = " + arc + ",rec = " + rec + ",cir = " + cir + " thread = " + Thread.currentThread());
                        return arc + "-" + rec + "-" + cir;
                    }
                });

        zip.subscribe(observer);
    }

    @OnClick(R.id.concat)
    void concat() {
        Observable.concat(observable1, observable2).subscribe(observer);
    }

    @OnClick(R.id.merge)
    void merge() {
        Observable.merge(observable1, observable2).subscribe(observer);
    }

    @OnClick(R.id.concatmap)
    void concatMap() {
        Observable.just(1, 2)
                .concatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        if (integer == 1) {
                            return observable1;
                        } else {
                            return observable2;
                        }
                    }
                }).subscribe(observer);
    }

    @OnClick(R.id.flatmap)
    void flatmap() {
        Observable.just(1, 2)
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        if (integer == 1) {
                            return observable1;
                        } else {
                            return observable2;
                        }
                    }
                }).subscribe(observer);
    }

    @OnClick(R.id.map)
    void exchange() {
        Observable map = observable.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                return o * 2;
            }
        });

        //多线程的情况下，flatmap发出的事件是无序的.一个Observable.fromIterable(lists)对应一个InnerObserver，多线程情况下各自发送事件
        Observable flatMap = observable.flatMap(new Function<Integer, Observable>() {
            @Override
            public Observable apply(Integer o) throws Exception {
                LoggerUtils.LOGD("flat map " + o + ",thread =" + Thread.currentThread().getName());
                final List<String> lists = new ArrayList<>();
                for (int i = 0; i < 3; ++i) {
                    lists.add("apply event " + o + ",new " + i);
                }

//                return Observable.fromIterable(lists);
                return Observable.fromIterable(lists).delay(o == 3 ? 0 : 1, TimeUnit.SECONDS);
            }
        });

        //按接收到的数据发送数据。接收一个数据后，发送处理完数据，在接受下一个数据。同步
        //queue.offer(t);       t = queue.poll();   o = mapper.apply(t)
        Observable concatMap = observable.concatMap(new Function<Integer, Observable>() {
            @Override
            public Observable apply(Integer o) throws Exception {
                LoggerUtils.LOGD("apply i = " + o);
//                return Observable.just("apply " + o);
                return Observable.just("apply " + o).delay(10, TimeUnit.MILLISECONDS);
            }
        });

        Observable buffer = observable.buffer(5, 2);

        map.subscribe(observer);
//        flatMap.subscribe(observer);
//        concatMap.subscribe(observer);
//        buffer.subscribe(observer);
    }

    @OnClick(R.id.interval)
    void interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(observer);
    }

    @OnClick(R.id.create)
    void create() {
        Observable create = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LoggerUtils.LOGD("create send data");
                emitter.onNext(2);
            }
        });

        create.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(observer);

    }

    Observable error = Observable.error(new NullPointerException("yoyo"));

    //create返回一个被观察者对象ObservableOnSubscribe
    //ObservableEmitter 发射器负责通知观察者事件发生
    //观察者通过subscribe()方法注册
    Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            LoggerUtils.LOGD("subscribe on thread = " + Thread.currentThread().getName());
            for (int i = 0; i < 10; ++i) {
                LoggerUtils.LOGD("send " + i);
                emitter.onNext(i);
            }

//            LoggerUtils.LOGD("send i = " + 1);
//            emitter.onNext(1);
//            LoggerUtils.LOGD("end send i = " + 1);
//            emitter.onError(new NullPointerException("yoyo"));
//            emitter.onComplete();
        }
    });

    //初始化一个观察者
    //Disposable 负责解注册
    //onXXX方法为被观察者回调
    Disposable disposable;
    Observer observer = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(Object o) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            LoggerUtils.LOGD("rev i = " + o + ",current thread = " + Thread.currentThread().getName());
        }

        @Override
        public void onError(Throwable e) {
            LoggerUtils.LOGE("rev error", e);
        }

        @Override
        public void onComplete() {
            LoggerUtils.LOGE("rev complete");

        }
    };


    Observable observableFast = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            for (int i = 0; true; ++i) {
                LoggerUtils.LOGD("send " + i);
                emitter.onNext(i);
            }
        }
    });

    Observable observableWhile = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            for (int i = 0; true; ++i) {
                LoggerUtils.LOGD("send " + i);
                emitter.onNext(i);
            }
        }
    });

    Flowable flowableSyn = Flowable.create(new FlowableOnSubscribe<Integer>() {
        @Override
        public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
            for (int i = 0; i < 6; ++i) {
                emitter.onNext(i);
                LoggerUtils.LOGD("send " + i + ",requested = " + emitter.requested());
            }
        }
    }, BackpressureStrategy.ERROR);

    Flowable flowableAsyn = Flowable.create(new FlowableOnSubscribe<Integer>() {
        @Override
        public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
            for (int i = 0; i < 6; ++i) {
                emitter.onNext(i);
                LoggerUtils.LOGD("send " + i + ",requested = " + emitter.requested());
            }
        }
    }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io());


    Subscriber subscriber = new Subscriber<Integer>() {
        @Override
        public void onSubscribe(Subscription s) {
            LoggerUtils.LOGD("subscribe ");
            s.request(5);
        }

        @Override
        public void onNext(Integer o) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            LoggerUtils.LOGD("recv " + o);
        }

        @Override
        public void onError(Throwable t) {
            LoggerUtils.LOGE(t);
        }

        @Override
        public void onComplete() {
            LoggerUtils.LOGE("onComplete");

        }
    };

    Observable startWithOb = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            LoggerUtils.LOGD("send item");
            emitter.onNext("item");
            LoggerUtils.LOGD("send count");
            emitter.onNext("count");
        }
    });

    Observable zipSendCir = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            LoggerUtils.LOGD("cir1");
            emitter.onNext("cir1");
            Thread.sleep(500);
            LoggerUtils.LOGD("cir2");
            emitter.onNext("cir2");
            Thread.sleep(500);
            LoggerUtils.LOGD("cir3");
            emitter.onNext("cir3");
            Thread.sleep(500);
            LoggerUtils.LOGD("cir4");
            emitter.onNext("cir4");
            Thread.sleep(500);
            LoggerUtils.LOGD("cir5");
            emitter.onNext("cir5");
            Thread.sleep(500);
        }
    }).subscribeOn(Schedulers.io());

    SingleObserver singleObserver = new SingleObserver<List<Integer>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<Integer> o) {
            LoggerUtils.LOGD("rev " + o.toString());
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    SingleObserver singleBoolean = new SingleObserver<Boolean>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Boolean aBoolean) {
            LoggerUtils.LOGD("boolean = " + aBoolean);
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    MaybeObserver maybeObserver = new MaybeObserver<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Integer i) {
            LoggerUtils.LOGD("i = " + i);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };


    Observable observable1 = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            LoggerUtils.LOGD("send arc1 thread = " + Thread.currentThread());
            emitter.onNext("arc1 ");
            LoggerUtils.LOGD("send arc2 thread = " + Thread.currentThread());
            emitter.onNext("arc2 ");
            LoggerUtils.LOGD("send arc3 thread = " + Thread.currentThread());
            emitter.onNext("arc3 ");
            emitter.onComplete();
        }
    }).subscribeOn(Schedulers.io());

    Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            LoggerUtils.LOGD("send rec1 thread = " + Thread.currentThread());
            emitter.onNext("rec1 ");
            LoggerUtils.LOGD("send rec2 thread = " + Thread.currentThread());
            emitter.onNext("rec2 ");
            LoggerUtils.LOGD("send rec3 thread = " + Thread.currentThread());
            emitter.onNext("rec3 ");
            emitter.onComplete();
//                LoggerUtils.LOGE("send error");
//                emitter.onError(new NullPointerException("null yoyo"));
        }
    }).subscribeOn(Schedulers.io());
}