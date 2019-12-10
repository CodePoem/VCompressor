package com.vdreamers.vcompressor.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vdreamers.vcompressor.log.LogUtils;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

/**
 * 压缩线程池执行器工具类
 * <p>
 * date 2019/12/09 14:24:50
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings("unused")
public class CompressExecutorUtils {

    /**
     * 线程池执行器服务
     */
    private ExecutorService mExecutorService;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_THREAD_NUM = 4;

    private CompressExecutorUtils() {
    }

    private static class SingletonHolder {
        private static final CompressExecutorUtils INSTANCE = new CompressExecutorUtils();
    }

    public static CompressExecutorUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void runWorker(@NonNull Runnable runnable) {
        ensureWorkerHandlerNotNull();
        try {
            mExecutorService.execute(runnable);
        } catch (Exception e) {
            LogUtils.e("runnable stop running unexpected. " + e.getMessage());
        }
    }

    @Nullable
    public FutureTask<File> runWorker(@NonNull Callable<File> callable) {
        ensureWorkerHandlerNotNull();
        FutureTask<File> task = null;
        try {
            task = new FutureTask<>(callable);
            mExecutorService.submit(task);
            return task;
        } catch (Exception e) {
            LogUtils.e("callable stop running unexpected. " + e.getMessage());
        }
        return task;
    }

    public void runUI(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        Handler handler = ensureUiHandlerNotNull();
        try {
            handler.post(runnable);
        } catch (Exception e) {
            LogUtils.d("update UI task fail. " + e.getMessage());
        }
    }

    private void ensureWorkerHandlerNotNull() {
        if (mExecutorService == null) {
            mExecutorService = CompressExecutor.newExecutor(calculateBestThreadCount());
        }
    }

    private static int calculateBestThreadCount() {
        return Math.min(MAXIMUM_THREAD_NUM, Runtime.getRuntime().availableProcessors());
    }


    private Handler ensureUiHandlerNotNull() {
        return new Handler(Looper.getMainLooper());
    }
}
