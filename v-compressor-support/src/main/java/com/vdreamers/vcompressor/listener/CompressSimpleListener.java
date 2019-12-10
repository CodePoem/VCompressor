package com.vdreamers.vcompressor.listener;

import java.io.File;
import java.util.List;

/**
 * 简单压缩监听器
 * <p>
 * date 2019/12/09 15:57:57
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public abstract class CompressSimpleListener implements CompressListener {

    @Override
    public void onStart() {

    }

    @Override
    public abstract void onSuccess(List<File> compressedFiles);

    @Override
    public abstract void onFailed(Throwable throwable);
}
