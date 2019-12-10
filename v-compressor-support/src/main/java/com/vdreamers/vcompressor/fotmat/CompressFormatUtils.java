package com.vdreamers.vcompressor.fotmat;

import android.graphics.Bitmap;
import android.os.Build;

import java.io.File;
import java.lang.reflect.Field;


/**
 * 压缩格式工具类
 * <p>
 * date 2019/12/10 10:32:35
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CompressFormatUtils {

    /**
     * 根据图片文件名解析图片压缩格式
     *
     * @param fileName 图片文件名
     * @return 图片压缩格式
     */
    public static Bitmap.CompressFormat parseFormat(String fileName) {
        int dotPos = fileName.lastIndexOf(".");
        if (dotPos <= 0 || dotPos + 1 >= fileName.length()) {
            return Bitmap.CompressFormat.JPEG;
        }
        String ext = fileName.substring(dotPos + 1);
        if (CompressFormatSuffixConstants.JPG.equalsIgnoreCase(ext) || CompressFormatSuffixConstants.JPEG.equalsIgnoreCase(ext)) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (CompressFormatSuffixConstants.PNG.equalsIgnoreCase(ext)) {
            return Bitmap.CompressFormat.PNG;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (CompressFormatSuffixConstants.WEBP.equalsIgnoreCase(ext)) {
                return Bitmap.CompressFormat.WEBP;
            }
        }
        return Bitmap.CompressFormat.JPEG;
    }

    /**
     * 根据图片压缩格式获取图片压缩后缀名
     *
     * @param format 图片压缩格式
     * @return 图片压缩后缀名
     */
    public static String getSuffixName(Bitmap.CompressFormat format) {
        if (format == Bitmap.CompressFormat.PNG) {
            return CompressFormatSuffixConstants.PNG;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (format == Bitmap.CompressFormat.WEBP) {
                return CompressFormatSuffixConstants.WEBP;
            }
        }
        return CompressFormatSuffixConstants.JPG;
    }

    /**
     * 根据图片文件名获取图片压缩后缀名
     *
     * @param fileName 图片文件名
     * @return 图片压缩后缀名
     */
    public static String getSuffixName(String fileName) {
        Bitmap.CompressFormat format = parseFormat(fileName);
        return getSuffixName(format);
    }
}
