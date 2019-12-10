package com.vdreamers.vcompressor.executor;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 压缩线程池执行器
 * <p>
 * date 2019/12/09 15:24:40
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CompressExecutor implements ExecutorService {

    /**
     * 线程池执行器服务代理
     */
    private final ExecutorService delegate;
    /**
     * 等待队列（有界队列ArrayBlockingQueue）最大数量
     */
    private static final int MAX_ARRAY_BLOCKING_QUEUE_SIZE = 1024;
    /**
     * 线程名格式
     */
    private static final String THREAD_NAME_FORMAT = "compressor_thread_%d";

    private CompressExecutor(ExecutorService delegate) {
        this.delegate = delegate;
    }

    public static CompressExecutor newExecutor(
            int threadCount) {
        return new CompressExecutor(
                new ThreadPoolExecutor(
                        threadCount,
                        threadCount,
                        0L,
                        TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<Runnable>(MAX_ARRAY_BLOCKING_QUEUE_SIZE),
                        new ThreadFactory() {
                            private final AtomicInteger mThreadId = new AtomicInteger(0);

                            @Override
                            public Thread newThread(@NonNull Runnable r) {
                                Thread t = new Thread(r);
                                t.setName(String.format(Locale.getDefault(), THREAD_NAME_FORMAT,
                                        this.mThreadId.getAndIncrement()));
                                return t;
                            }
                        }));
    }

    @Override
    public void shutdown() {
        delegate.shutdown();
    }

    @Override
    @NonNull
    public List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }

    @Override
    @NonNull
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return delegate.submit(task);
    }

    @Override
    @NonNull
    public <T> Future<T> submit(@NonNull Runnable task, T result) {
        return delegate.submit(task, result);
    }

    @Override
    @NonNull
    public Future<?> submit(@NonNull Runnable task) {
        return delegate.submit(task);
    }

    @Override
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate.invokeAll(tasks);
    }

    @Override
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks,
                                         long timeout,
                                         @NonNull TimeUnit unit) throws InterruptedException {
        return delegate.invokeAll(tasks, timeout, unit);
    }

    @Override
    @NonNull
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks) throws ExecutionException,
            InterruptedException {
        return delegate.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks, long timeout,
                           @NonNull TimeUnit unit) throws ExecutionException,
            InterruptedException, TimeoutException {
        return delegate.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        delegate.execute(command);
    }
}
