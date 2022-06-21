package lagou_currency.threadpool;

import java.util.concurrent.*;

import static java.util.concurrent.ThreadPoolExecutor.*;

public class ThreadPoolDemo {


    /**
     * 自动创建几种常见线程池的方法
     * */
    public void waysOfCreateAutomatic(){
        Executors.newFixedThreadPool(10);
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(6);
        Executors.newSingleThreadScheduledExecutor();
        /**源码为ForkJoinPool*/
        Executors.newWorkStealingPool();
    }
    public void wayOfCreateByself(){
        RejectedExecutionHandler AbortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread("test-thread-pool%n");
            }
        }, AbortPolicy);
    }
}
