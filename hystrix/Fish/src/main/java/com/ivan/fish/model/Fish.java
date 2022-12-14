package com.ivan.fish.model;

import lombok.Data;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Fish {

    /**
     * 最大失败次数
     */
    private static final int MAX_FAIL_COUNT = 3;

    /**
     * 断路器的窗口时间
     */
    private static final int WINDOW_SLEEP_TIME = 5;

    /**
     * 断路器状态：默认关闭
     */
    private FishStatus status = FishStatus.CLOSE;

    /**
     * 当前失败次数
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    public Object lock = new Object();

    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            4,
            8,
            3,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    {
        threadPool.execute(()->{
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_SLEEP_TIME);
                    if (status.equals(FishStatus.CLOSE)) {
                        this.currentFailCount.set(0);
                    } else {
                        synchronized (lock) {
                            lock.wait();
                            System.out.println("测试调用成功，统计线程再次启用");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addFailCount() {
        int count = currentFailCount.incrementAndGet();
        if (count >= MAX_FAIL_COUNT) {
            this.setStatus(FishStatus.OPEN);
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(WINDOW_SLEEP_TIME);
                    currentFailCount.set(0);
                    this.setStatus(FishStatus.HALF_OPEN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
