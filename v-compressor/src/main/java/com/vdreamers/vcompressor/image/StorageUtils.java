package com.vdreamers.vcompressor.image;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.Nullable;

import com.vdreamers.vcompressor.log.LogUtils;

import java.io.File;

/**
 * 存储相关工具类
 * <p>
 * date 2019/06/26 14:32:36
 *
 * @author <a href="mailto:danhuangpai@2dfire.com">蛋黄派</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class StorageUtils {

    public static final String TAG = StorageUtils.class.getSimpleName();

    private StorageUtils() {
        // cannot be instantiated
        throw new UnsupportedOperationException("StorageUtils cannot be instantiated");
    }

    public static boolean isFileValid(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0 && file.canRead();
    }

    /**
     * 获取内部存储目录
     *
     * @return file 内部存储目录
     */
    public static File getInternalDir() {
        return Environment.getDataDirectory();
    }

    /**
     * 获取内部存储目录普通文件目录
     *
     * @param context context 调用方上下文
     * @return file 内部存储目录普通文件目录
     */
    public static File getInternalFilesDir(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        return context.getFilesDir();
    }

    /**
     * 获取内部存储目录缓存目录
     *
     * @param context context 调用方上下文
     * @return file 内部存储目录缓存目录
     */
    public static File getInternalCacheDir(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        return context.getCacheDir();
    }

    /**
     * 删除内部存储文件
     *
     * @param context 调用方上下文
     * @param name    文件名
     * @return 删除是否成功
     */
    public static boolean deleteInternalFile(Context context, String name) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        return context.deleteFile(name);
    }

    /**
     * 获取内部存储目录所有文件列表
     *
     * @param context 调用方上下文
     * @return 文件名列表数组
     */
    public static String[] getInternalFileList(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        return context.fileList();
    }

    /**
     * 获取主外部存储文件目录
     *
     * @param context context 调用方上下文
     * @param type    类型
     * @return file 主外部存储文件目录
     */
    public static File getExternalFilesDir(Context context, @Nullable String type) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        if (!isSDCardEnable()) {
            return null;
        }
        return context.getExternalFilesDir(type);
    }

    /**
     * 获取主外部存储私有目录缓存目录
     *
     * @param context context 调用方上下文
     * @return file 主外部存储私有目录缓存目录
     */
    public static File getExternalCacheDir(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        if (!isSDCardEnable()) {
            return null;
        }
        return context.getExternalCacheDir();
    }

    /**
     * 判断SDCard是否可用
     *
     * @return SDCard是否可用 true：可用 false：不可用
     */
    public static boolean isSDCardEnable() {
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable());
    }

    /**
     * 获取系统存储路径
     *
     * @return file 系统存储路径
     */
    public static File getRootDir() {
        return Environment.getRootDirectory();
    }

    /**
     * 获取外部存储的总容量 单位byte
     *
     * @param context 调用方上下文
     * @return 外部存储的总容量 单位byte
     */
    public static long getExternalTotalSize(Context context) {
        if (isSDCardEnable()) {
            File file;
            file = getExternalFilesDir(context, null);
            if (file != null) {
                return file.getTotalSpace();
            }
        }
        return 0;
    }

    /**
     * 获取外部存储的剩余可用容量 单位byte
     *
     * @param context 调用方上下文
     * @return 外部存储的剩余可用容量 单位byte
     */
    public static long getExternalFreeSize(Context context) {
        if (isSDCardEnable()) {
            File file = getExternalFilesDir(context, null);
            if (file != null) {
                return file.getFreeSpace();
            }
        }
        return 0;
    }

    /**
     * 获得内部存储的总容量 单位byte
     *
     * @return 内部存储的总容量 单位byte
     */
    public static long getInternalTotalSize() {
        return getInternalDir().getTotalSpace();
    }

    /**
     * 内部存储的剩余可用容量 单位byte
     *
     * @return 内部存储的剩余可用容量 单位byte
     */
    public static long getInternalFreeSize() {
        return getInternalDir().getFreeSpace();
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath 指定路径
     * @return 指定路径所在空间的剩余可用容量字节数
     */
    public static long getFreeBytes(String filePath) {
        StatFs statFs = new StatFs(filePath);
        // 单个数据块的大小（byte）
        long blockSize;
        // 空闲的数据块的数量
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = (long) statFs.getBlockSize();
            availableBlocks = (long) statFs.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }

    /**
     * 获取指定路径所在空间的总容量字节数，单位byte
     *
     * @param filePath 指定路径
     * @return 指定路径所在空间的总容量字节数
     */
    public static long getTotalBytes(String filePath) {
        StatFs statFs = new StatFs(filePath);
        // 单个数据块的大小（byte）
        long blockSize;
        // 总数据块的数量
        long totalSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalSize = statFs.getBlockCountLong();
        } else {
            blockSize = (long) statFs.getBlockSize();
            totalSize = (long) statFs.getBlockCount();
        }
        return blockSize * totalSize;
    }

    /**
     * 获取app文件数据路径
     *
     * @param context 调用方上下文
     * @param dir     指定目录
     * @return app文件数据路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getFilesPath(Context context, String dir) {
        String directoryPath;
        if (isSDCardEnable()) {
            File file = context.getExternalFilesDir(dir);
            if (file != null) {
                // 外部存储可用
                directoryPath = file.getAbsolutePath() + File.separator + dir;
            } else {
                directoryPath = context.getFilesDir() + File.separator + dir;
            }
        } else {
            // 外部存储不可用
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {
            // 判断文件目录不存在则创建
            file.mkdirs();
        }
        LogUtils.d(TAG, directoryPath);
        return directoryPath;
    }

    /**
     * 获取app缓存路径
     *
     * @param context 调用方上下文
     * @return app缓存路径
     */
    public static String getCachePath(Context context) {
        return getCachePath(context, "");
    }

    /**
     * 获取app缓存路径
     *
     * @param context 调用方上下文
     * @param subDir 子目录
     * @return app缓存路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getCachePath(Context context, String subDir) {
        String directoryPath;
        if (context == null) {
            return "";
        }
        if (subDir == null) {
            subDir = "";
        }
        if (isSDCardEnable()) {
            File file = context.getExternalCacheDir();
            if (file != null) {
                // 外部存储可用
                directoryPath = file.getAbsolutePath() + File.separator + subDir;
            } else {
                directoryPath = context.getCacheDir() + File.separator + subDir;
            }
        } else {
            // 外部存储不可用
            directoryPath = context.getCacheDir() + File.separator + subDir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {
            // 判断文件目录不存在则创建
            file.mkdirs();
        }
        LogUtils.d(TAG, directoryPath);
        return directoryPath;
    }
}
