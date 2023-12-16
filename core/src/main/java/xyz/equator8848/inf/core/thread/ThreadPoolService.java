package xyz.equator8848.inf.core.thread;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import xyz.equator8848.inf.core.dynamic.properties.BaseConfig;
import xyz.equator8848.inf.core.util.spring.BeanManagerUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Equator
 * @Date: 2021/11/22 22:14
 **/

public final class ThreadPoolService {
    private static final ExecutorService INSTANCE = build();

    private ThreadPoolService() {

    }

    public static ExecutorService getInstance() {
        return INSTANCE;
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return INSTANCE.submit(task);
    }

    public static Future<?> submit(Runnable task) {
        return INSTANCE.submit(task);
    }

    private static ExecutorService build() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 32,
                10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ApplicationThreadFactory());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    private static class ApplicationThreadFactory implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            BaseConfig baseConfig = BeanManagerUtil.getBean(BaseConfig.class);
            return new Thread(r, String.format("%s-Thread-%s", baseConfig.getSystemName(), counter.getAndIncrement()));
        }
    }
}
