package io.github.zunpiau.emitter.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class UidGeneratorTest {

    private static final int THREAD_COUNT = 64;
    private final UidGenerator generator = new UidGenerator();
    private final transient CountDownLatch exitLatch = new CountDownLatch(THREAD_COUNT);
    private final transient CountDownLatch generatorLatch = new CountDownLatch(THREAD_COUNT);

    @SuppressWarnings("StatementWithEmptyBody")
    @Test
    public void generate() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(THREAD_COUNT);
        ConcurrentMap<String, Object> map = new ConcurrentHashMap<>(128);
        Object placeholder = new Object();
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(() -> {
                generatorLatch.countDown();
                try {
                    generatorLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(generator.generate(), placeholder);
                exitLatch.countDown();
            });
        }
        exitLatch.await();
        Assert.assertEquals(THREAD_COUNT, map.size());
        executor.shutdown();
    }
}