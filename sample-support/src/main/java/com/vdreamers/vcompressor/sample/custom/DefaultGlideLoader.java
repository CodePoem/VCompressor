package com.vdreamers.vcompressor.sample.custom;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vdreamers.vmediaselector.core.entity.MediaEntity;
import com.vdreamers.vmediaselector.core.loader.IMediaLoader;

/**
 * 默认多媒体加载器-Glide
 * <p>
 * date 2019-12-11 00:59:30
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public class DefaultGlideLoader implements IMediaLoader {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull MediaEntity media, int width,
                                 int height) {
        Glide.with(img)
                .load(media.getUri())
                .placeholder(com.vdreamers.vmediaselector.R.drawable.v_selector_ui_impl_ic_broken_image)
                .override(width, height)
                .centerCrop()
                .into(img);
    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull MediaEntity media, int width,
                           int height) {
        Glide.with(img)
                .load(media.getUri())
                .placeholder(com.vdreamers.vmediaselector.R.drawable.v_selector_ui_impl_ic_broken_image)
                .override(width, height)
                .fitCenter()
                .into(img);
    }
}
