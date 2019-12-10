package com.vdreamers.vcompressor;

import android.content.Context;

import com.vdreamers.vcompressor.config.CompressConfig;
import com.vdreamers.vcompressor.listener.CompressListener;
import com.vdreamers.vcompressor.compressor.DefaultCompressor;
import com.vdreamers.vcompressor.compressor.ICompressor;
import com.vdreamers.vcompressor.executor.CompressCallable;
import com.vdreamers.vcompressor.executor.CompressExecutorUtils;
import com.vdreamers.vcompressor.log.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 压缩工具类
 * <p>
 * date 2019/12/09 15:58:48
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CompressUtils {
    /**
     * 压缩配置
     */
    private CompressConfig mCompressConfig;
    /**
     * 压缩监听器
     */
    private CompressListener mCompressListener;

    private CompressUtils() {
    }

    public static CompressUtils of() {
        return new CompressUtils();
    }

    public CompressUtils setCompressConfig(CompressConfig compressConfig) {
        mCompressConfig = compressConfig;
        return this;
    }

    public CompressUtils setCompressListener(CompressListener compressListener) {
        mCompressListener = compressListener;
        return this;
    }

    public boolean compress(Context context, List<File> fileList) {
        return compress(new DefaultCompressor(context, mCompressConfig), fileList, mCompressListener);
    }

    public boolean compress(ICompressor compressor, final List<File> fileList
            , final CompressListener compressListener) {
        // 检查参数
        if (compressor == null || fileList == null) {
            return false;
        }

        CompressExecutorUtils.getInstance().runUI(new Runnable() {
            @Override
            public void run() {
                if (compressListener != null) {
                    compressListener.onStart();
                }
            }
        });

        int size = fileList.size();
        // 构建线程池运行
        List<FutureTask<File>> taskList = new ArrayList<>(size);
        for (File file : fileList) {
            FutureTask<File> task =
                    CompressExecutorUtils.getInstance().runWorker(new CompressCallable(compressor
                            , file));
            taskList.add(task);
        }

        // 获取线程池运行结果
        final List<File> resultFileList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            try {
                FutureTask<File> task = taskList.get(i);
                if (task != null) {
                    File file = task.get();
                    if (file == null) {
                        // 压缩结果为null 将原File添加进结果列表
                        resultFileList.add(fileList.get(i));
                    } else {
                        resultFileList.add(file);
                    }
                }
            } catch (ExecutionException | CancellationException | InterruptedException e) {
                LogUtils.e("futureTask stop running unexpected. " + e.getMessage());
                CompressExecutorUtils.getInstance().runUI(new Runnable() {
                    @Override
                    public void run() {
                        if (compressListener != null) {
                            compressListener.onFailed(e);
                        }
                    }
                });
                return false;
            }
        }
        CompressExecutorUtils.getInstance().runUI(new Runnable() {
            @Override
            public void run() {
                if (compressListener != null) {
                    compressListener.onSuccess(resultFileList);
                }
            }
        });
        return true;
    }

}
