package MulThread;

import java.util.concurrent.Callable;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestExecutor {

    static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            System.out.println("new thread");
            return new Thread();
        }
    };

    static ExecutorService executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 5, TimeUnit.SECONDS,
            new DelayQueue());
//    static ExecutorService executor = Executors.newCachedThreadPool();

    static Callable callable = new Callable() {
        @Override
        public String call() throws Exception {
            System.out.println("do task");
            Thread.sleep(5000);
            return "yoyo";
        }
    };

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("task return ");
        Future future = executor.submit(callable);

        System.out.println("task return " + future.get());
    }
}
