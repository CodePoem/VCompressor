package com.vdreamers.vcompressor.config;

import android.graphics.Bitmap;

/**
 * 压缩配置
 * <p>
 * date 2019/12/09 11:20:07
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CompressConfig {
    /**
     * 默认压缩参数-进行长宽的压缩
     */
    public static final boolean DEFAULT_COMPRESS_SCALED = true;
    /**
     * 默认压缩参数-最大大小
     */
    public static final int DEFAULT_COMPRESS_MAX_SIZE = 400;
    /**
     * 默认压缩参数-最大宽度
     */
    public static final int DEFAULT_COMPRESS_MAX_WIDTH = 800;
    /**
     * 默认压缩参数-最大高度
     */
    public static final int DEFAULT_COMPRESS_MAX_HEIGHT = 800;
    /**
     * 默认压缩参数-压缩质量
     */
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    /**
     * 默认压缩参数-是否强制指定后缀压缩
     */
    public static final boolean DEFAULT_FORCE_COMPRESS_FORMAT = false;
    /**
     * 默认压缩格式-JPG
     */
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    /**
     * 最大图片大小,默认400k
     */
    private int mCompressMaxSize = DEFAULT_COMPRESS_MAX_SIZE;
    /**
     * 是否进行长宽的压缩，默认进行
     */
    private boolean mScaled = DEFAULT_COMPRESS_SCALED;
    /**
     * 最大宽度，默认800
     */
    private int mCompressMaxWidth = DEFAULT_COMPRESS_MAX_WIDTH;
    /**
     * 最大高度，默认800
     */
    private int mCompressMaxHeight = DEFAULT_COMPRESS_MAX_HEIGHT;
    /**
     * 压缩质量，默认100
     */
    private int mQuality = DEFAULT_COMPRESS_QUALITY;
    /**
     * 是否是否强制指定后缀压缩，默认不强制
     */
    private boolean mForceFormat = DEFAULT_FORCE_COMPRESS_FORMAT;
    /**
     * 图片格式
     */
    private Bitmap.CompressFormat mFormat = DEFAULT_COMPRESS_FORMAT;

    private CompressConfig() {
    }

    public static CompressConfig init() {
        return new CompressConfig();
    }

    public CompressConfig setScaled(boolean scaled) {
        mScaled = scaled;
        return this;
    }

    public CompressConfig setCompressMaxWidth(int compressMaxWidth) {
        mCompressMaxWidth = compressMaxWidth;
        return this;
    }

    public CompressConfig setCompressMaxHeight(int compressMaxHeight) {
        mCompressMaxHeight = compressMaxHeight;
        return this;
    }

    public CompressConfig setCompressMaxSize(int compressMaxSize) {
        mCompressMaxSize = compressMaxSize;
        return this;
    }

    public CompressConfig setQuality(int quality) {
        mQuality = quality;
        return this;
    }

    public CompressConfig setForceFormat(boolean forceFormat) {
        mForceFormat = forceFormat;
        return this;
    }

    public CompressConfig setFormat(Bitmap.CompressFormat format) {
        mFormat = format;
        return this;
    }

    public int getCompressMaxSize() {
        return mCompressMaxSize;
    }

    public boolean isScaled() {
        return mScaled;
    }

    public int getCompressMaxWidth() {
        return mCompressMaxWidth;
    }

    public int getCompressMaxHeight() {
        return mCompressMaxHeight;
    }

    public int getQuality() {
        return mQuality;
    }

    public boolean isForceFormat() {
        return mForceFormat;
    }

    public Bitmap.CompressFormat getFormat() {
        return mFormat;
    }
}
