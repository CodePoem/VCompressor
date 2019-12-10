package com.vdreamers.vcompressor.mimetype;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.vdreamers.vcompressor.mimetype.MimeType;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * 媒体类型工具类
 * <p>
 * date 2019/12/09 11:35:40
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class MimeTypeUtils {

    /**
     * 根据媒体类型判断是否是jpg图片
     *
     * @param mimeType 媒体类型
     * @return 是否是jpg图片
     */
    public static boolean isJpg(@MimeType String mimeType) {
        return MimeType.IMAGE_JPEG.equals(mimeType) || MimeType.IMAGE_JPG.equals(mimeType);
    }

    /**
     * 根据媒体类型判断是否是png图片
     *
     * @param mimeType 媒体类型
     * @return 是否是png图片
     */
    public static boolean isPng(@MimeType String mimeType) {
        return MimeType.IMAGE_PNG.equals(mimeType);
    }

    /**
     * 根据媒体类型判断是否是gif图片
     *
     * @param mimeType 媒体类型
     * @return 是否是gif图片
     */
    public static boolean isGif(@MimeType String mimeType) {
        return MimeType.IMAGE_GIF.equals(mimeType);
    }

    /**
     * 根据媒体类型判断是否是bmp图片
     *
     * @param mimeType 媒体类型
     * @return 是否是bmp图片
     */
    public static boolean isBmp(@MimeType String mimeType) {
        return MimeType.IMAGE_BMP.equals(mimeType);
    }

    /**
     * 根据媒体类型判断是否是webp图片
     *
     * @param mimeType 媒体类型
     * @return 是否是webp图片
     */
    public static boolean isWebp(@MimeType String mimeType) {
        return MimeType.IMAGE_WEBP.equals(mimeType);
    }

    public static String getMimeType(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String mimeType = options.outMimeType;
        options.inJustDecodeBounds = false;
        return mimeType;
    }

    public static String getMimeType(Resources res, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        String mimeType = options.outMimeType;
        options.inJustDecodeBounds = false;
        return mimeType;
    }

    public static String getMimeType(InputStream is) {
        return getMimeType(is, null);
    }

    public static String getMimeType(InputStream is, Rect outPadding) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, outPadding, options);
        String mimeType = options.outMimeType;
        options.inJustDecodeBounds = false;
        return mimeType;
    }

    public static String getMimeType(FileDescriptor fd) {
        return getMimeType(fd, null);
    }

    public static String getMimeType(FileDescriptor fd, Rect outPadding) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, outPadding, options);
        String mimeType = options.outMimeType;
        options.inJustDecodeBounds = false;
        return mimeType;
    }
}
