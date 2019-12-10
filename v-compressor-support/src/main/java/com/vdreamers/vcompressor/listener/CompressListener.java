package com.vdreamers.vcompressor.listener;

import java.io.File;
import java.util.List;

/**
 * 压缩监听器
 * <p>
 * date 2019/12/09 15:57:45
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public interface CompressListener {
    /**
     * 压缩开始
     */
    void onStart();

    /**
     * 压缩成功回调
     *
     * @param compressedFiles 压缩后的文件列表
     */
    void onSuccess(List<File> compressedFiles);

    /**
     * 压缩失败回调
     *
     * @param throwable 压缩失败异常
     */
    void onFailed(Throwable throwable);
}
