package com.deshang.ttjx.framework.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by L on 2018/6/9.
 */

public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
