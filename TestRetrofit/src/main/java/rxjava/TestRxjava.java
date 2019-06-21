package rxjava;

import Gson.Main;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TestRxjava {
    static int count = 0;

    public static void main(String[] args) throws Exception {
        count = 0;
        Observable.just(1)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        count++;
                        if (count < 10) {
                            throw new Exception(count + "");
                        } else
                            System.out.println("send i = " + integer);
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return null;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("send i = " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("error = " + throwable.getMessage());
                    }
                });
    }
}
