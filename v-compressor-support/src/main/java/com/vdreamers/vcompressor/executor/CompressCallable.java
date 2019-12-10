package com.vdreamers.vcompressor.executor;

import com.vdreamers.vcompressor.compressor.ICompressor;
import com.vdreamers.vcompressor.image.StorageUtils;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * 压缩器线程
 * <p>
 * date 2019/12/11 03:57:50
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public class CompressCallable implements Callable<File> {

    private ICompressor mCompressor;
    private File mFile;

    public CompressCallable(ICompressor compressor, File file){
        mCompressor  = compressor;
        mFile = file;
    }

    @Override
    public File call() throws Exception {
        File result = null;
        if (mCompressor != null) {
            result = mCompressor.compress(mFile);
        }
        boolean resultVailid = StorageUtils.isFileValid(result);
        return resultVailid ? result : mFile;
    }
}
