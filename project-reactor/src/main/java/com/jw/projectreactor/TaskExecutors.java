package com.jw.projectreactor;

import java.util.concurrent.*;

public class TaskExecutors {

    private ExecutorService executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());

    public void submit(Runnable task) {
        CountDownLatch count = new CountDownLatch(1);
        executor.submit(() -> {
            task.run();
            count.countDown();
        });
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static final class ExecutorsHolder {
        static final TaskExecutors executors = new TaskExecutors();
    }

    public static TaskExecutors of() {
        return ExecutorsHolder.executors;
    }

}
