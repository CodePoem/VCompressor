package com.vdreamers.vcompressor.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.vdreamers.vcompressor.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;

/**
 * 位图工具类
 * <p>
 * date 2019/12/10 14:40:36
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BitmapUtils {

    /**
     * Bitmap尺寸压缩 邻近采样
     *
     * @param pathName  图片文件路径名
     * @param reqWidth  要求宽度
     * @param reqHeight 要求高度
     * @return 压缩完Bitmap
     */
    public static Bitmap nnrSizeCompress(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * Bitmap尺寸压缩 邻近采样
     *
     * @param res       资源
     * @param id        资源id
     * @param reqWidth  要求宽度
     * @param reqHeight 要求高度
     * @return 压缩完Bitmap
     */
    public static Bitmap nnrSizeCompress(Resources res, int id, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    /**
     * Bitmap尺寸压缩 邻近采样
     *
     * @param fd        图片文件路径名
     * @param reqWidth  要求宽度
     * @param reqHeight 要求高度
     * @return 压缩完Bitmap
     */
    public static Bitmap nnrSizeCompress(FileDescriptor fd, int reqWidth, int reqHeight) {
        return nnrSizeCompress(fd, null, reqWidth, reqHeight);
    }

    /**
     * Bitmap尺寸压缩 邻近采样
     *
     * @param fd         图片文件路径名
     * @param outPadding 填充矩形
     * @param reqWidth   要求宽度
     * @param reqHeight  要求高度
     * @return 压缩完Bitmap
     */
    public static Bitmap nnrSizeCompress(FileDescriptor fd, Rect outPadding, int reqWidth,
                                         int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, outPadding, options);
    }

    /**
     * 计算inSampleSize
     *
     * @param options   BitmapFactory.Option 选项
     * @param reqWidth  要求宽度
     * @param reqHeight 要求高度
     * @return 计算完的inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * Bitmap尺寸压缩 双线性采样
     *
     * @param bitmap    压缩前Bitmap
     * @param reqWidth  要求宽度
     * @param reqHeight 要求高度
     * @return 压缩完Bitmap
     */
    public static Bitmap brSizeCompress(Bitmap bitmap, int reqWidth, int reqHeight) {
        if ((bitmap.getWidth() <= reqWidth && bitmap.getHeight() <= reqHeight)) {
            return bitmap;
        }
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }

    /**
     * 质量压缩
     *
     * @param bitmap                压缩前bitmap
     * @param format                压缩格式
     * @param quality               压缩质量
     * @param byteArrayOutputStream 压缩完二进制数组输出流
     * @param maxSize               最大尺寸/体积
     */
    public static void qualityCompress(Bitmap bitmap, Bitmap.CompressFormat format, int quality,
                                       ByteArrayOutputStream byteArrayOutputStream, int maxSize) {
        bitmap.compress(format, quality, byteArrayOutputStream);
        if (maxSize > 0) {
            // 循环判断压缩后图片是否超过限制大小
            while (byteArrayOutputStream.size() / 1024 > maxSize) {
                // 清空byteArrayOutputStream
                byteArrayOutputStream.reset();
                if (quality < 0) {
                    break;
                }
                bitmap.compress(format, quality, byteArrayOutputStream);
                LogUtils.d("Compress : quantity:" + quality + " size:" + byteArrayOutputStream.size() / 1024);
                if (byteArrayOutputStream.size() / 1024 - maxSize < 100) {
                    // 开始接近最大值
                    quality -= 2;
                } else {
                    quality -= 5;
                }
            }
        }
    }

}
