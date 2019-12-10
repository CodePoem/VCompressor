package com.vdreamers.vcompressor.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.vdreamers.vcompressor.log.LogUtils;

import java.io.IOException;


/**
 * 图片工具类
 * <p>
 * date 2019/12/10 17:41:47
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public class ImageUtils {

    /**
     * 获取图片文件Exif旋转方向
     *
     * @param filepath 图片文件路径
     * @return 图片文件Exif旋转方向角度
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            LogUtils.e("getExifOrientation stop running unexpected. " + e.getMessage());
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        degree = 0;
                        break;
                }

            }
        }
        return degree;
    }

    /**
     * 旋转Bitmap
     *
     * @param bitmap 旋转前Bitmap
     * @param angle 旋转角度
     * @return 旋转后Bitmap
     */
    public static Bitmap rotateImage(Bitmap bitmap, int angle) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
    }

}
