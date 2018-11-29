package MulThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Interrupted {

    static ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);
    static int count = 0;
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(10));

    public static void main(String[] args) throws InterruptedException {

        TestInterrupted testInterrupted = new TestInterrupted();
        Thread thread1 = new Thread(testInterrupted);
        thread1.start();
        Thread.sleep(500 * 20);
        testInterrupted.cancel(thread1);
    }

    static class TestInterrupted implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    if (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(500);
                        System.out.println("do task" + count);
                        blockingQueue.put(count++);
                    }
                } catch (InterruptedException e) {
                    System.out.println("interrupt thread");
                    break;
                }

            }
        }

        public void cancel(Thread thread) {
            thread.interrupt();
        }
    }
}
