package com.vdreamers.vcompressor.mimetype;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 媒体类型
 * <p>
 * date 2019/12/09 11:36:00
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
@StringDef({MimeType.IMAGE_JPEG, MimeType.IMAGE_PNG, MimeType.IMAGE_JPG, MimeType.IMAGE_GIF,
        MimeType.IMAGE_BMP, MimeType.IMAGE_WEBP})
@Retention(RetentionPolicy.SOURCE)
public @interface MimeType {
    /**
     * 媒体类型-jpeg
     */
    String IMAGE_JPEG = "image/jpeg";
    /**
     * 媒体类型-png
     */
    String IMAGE_PNG = "image/png";
    /**
     * 媒体类型-jpg
     */
    String IMAGE_JPG = "image/jpg";
    /**
     * 媒体类型-gif
     */
    String IMAGE_GIF = "image/gif";
    /**
     * 媒体类型-bmp
     */
    String IMAGE_BMP = "image/bmp";
    /**
     * 媒体类型-webp
     */
    String IMAGE_WEBP = "image/webp";
}