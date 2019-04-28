package MulThread;

import android.support.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestBlockQueue {

    @NonNull
    static ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(20);
    static int count;
    @NonNull
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(2));

    public static void main(String[] args) {
        produce();
        comsume();
    }

    private static void comsume() {
        Runnable callable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("comsume " + blockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.submit(callable);
    }

    private static void produce() {
        Runnable callable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        System.out.println("produce " + count);
                        blockingQueue.put(count++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.submit(callable);
    }
}
