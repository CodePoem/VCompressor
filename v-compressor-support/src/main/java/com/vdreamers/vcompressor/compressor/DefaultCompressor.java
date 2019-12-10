package com.vdreamers.vcompressor.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.vdreamers.vcompressor.config.CompressConfig;
import com.vdreamers.vcompressor.fotmat.CompressFormatUtils;
import com.vdreamers.vcompressor.image.BitmapUtils;
import com.vdreamers.vcompressor.image.ImageUtils;
import com.vdreamers.vcompressor.image.StorageUtils;
import com.vdreamers.vcompressor.log.LogUtils;
import com.vdreamers.vcompressor.mimetype.MimeTypeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;


/**
 * 默认压缩器
 * <p>
 * date 2019/12/09 16:37:33
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings("unused")
public class DefaultCompressor implements ICompressor {
    /**
     * 上下文弱引用
     */
    private WeakReference<Context> mContextWeakReference;
    /**
     * 压缩配置
     */
    private CompressConfig mCompressConfig;
    /**
     * 文件前缀
     */
    private static final String FILE_NAME_PRE = "compressed_";

    private DefaultCompressor() {
    }

    public DefaultCompressor(@NonNull Context context) {
        this(context, null);
    }

    public DefaultCompressor(@NonNull Context context, CompressConfig config) {
        this.mContextWeakReference = new WeakReference<>(context);
        mCompressConfig = config;
        if (mCompressConfig == null) {
            mCompressConfig = CompressConfig.init();
        }
    }

    @Override
    public File getCompressOutFile(String fileName, File file) {
        String compressedFileName;
        if (mCompressConfig.isForceFormat()) {
            compressedFileName =
                    fileName + "." + CompressFormatUtils.getSuffixName(mCompressConfig.getFormat());
            LogUtils.i("use compress format:" + CompressFormatUtils.getSuffixName(mCompressConfig.getFormat()));
        } else {
            compressedFileName =
                    fileName + "." + CompressFormatUtils.getSuffixName(file.getAbsolutePath());
            LogUtils.i("use compress format:" + CompressFormatUtils.getSuffixName(file.getAbsolutePath()));
        }
        return new File(StorageUtils.getCachePath(mContextWeakReference.get()) + compressedFileName);
    }

    @Override
    @Nullable
    public File compress(File file) throws IOException, IllegalArgumentException {
        if (!StorageUtils.isFileValid(file)) {
            return null;
        }

        String path = file.getAbsolutePath();

        // GIF 不压缩
        String mimeType = MimeTypeUtils.getMimeType(path);
        if (MimeTypeUtils.isGif(mimeType)) {
            return null;
        }

        Bitmap resultBitmap;

        // 尺寸压缩
        if (mCompressConfig.isScaled()) {
            // 邻近采样
            Bitmap nnrBitmap = BitmapUtils.nnrSizeCompress(path,
                    mCompressConfig.getCompressMaxWidth(), mCompressConfig.getCompressMaxHeight());
            // 双线性采样
            resultBitmap = BitmapUtils.brSizeCompress(nnrBitmap,
                    mCompressConfig.getCompressMaxWidth(), mCompressConfig.getCompressMaxHeight());
            if (nnrBitmap != resultBitmap) {
                nnrBitmap.recycle();
            }
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            resultBitmap = BitmapFactory.decodeFile(path, options);
        }

        // 某些相机拍照后会旋转图片,并将信息写入到图片中,我们要将其旋转回来
        int angle = ImageUtils.getExifOrientation(path);
        if (angle != 0) {
            LogUtils.d("rotate image from:" + angle);
            Bitmap rotateBitmap = ImageUtils.rotateImage(resultBitmap, angle);
            if (resultBitmap != rotateBitmap) {
                resultBitmap.recycle();
            }
            resultBitmap = rotateBitmap;
        }

        // 质量压缩
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitmapUtils.qualityCompress(resultBitmap, mCompressConfig.getFormat(),
                mCompressConfig.getQuality(), byteArrayOutputStream,
                mCompressConfig.getCompressMaxSize());

        // 压缩输出路径
        File outputFile = getCompressOutFile(FILE_NAME_PRE + System.currentTimeMillis(), file);
        if (outputFile.exists() && outputFile.isFile()) {
            outputFile.delete();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

        return outputFile;
    }
}
