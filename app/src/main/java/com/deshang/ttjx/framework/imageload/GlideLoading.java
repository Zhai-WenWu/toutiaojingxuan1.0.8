package com.deshang.ttjx.framework.imageload;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.deshang.ttjx.R;

import java.io.File;

/**
 * Created by hh on 2017/6/13.
 */

public class GlideLoading {


    private static GlideLoading load;
    private static RequestOptions requestOptions;

    //
    private GlideLoading() {

    }

    public static GlideLoading getInstance() {
        if (load == null) {
            load = new GlideLoading();
        }
        if (requestOptions == null) {
            requestOptions = new RequestOptions();
        }
        return load;
    }

    public void loadImgFile(Context ct, File file, ImageView iv) {
        Glide.with(ct).load(file).into(iv);
    }

    public void loadImgUrlNyImgLoader(Context ct, String url, ImageView iv) {
        Glide.with(ct).load(url).into(iv);
    }

    public void loadImageForResources(Context ct, int resId, ImageView iv) {
        Glide.with(ct).load(resId).into(iv);
    }

    public void loadImageHeader(Context ct, String url, ImageView iv) {
        requestOptions.placeholder(R.mipmap.icon_header);
        requestOptions.error(R.mipmap.icon_header);
        Glide.with(ct)./*setDefaultRequestOptions(requestOptions).*/load(url).into(iv);
    }

    //加载圆形头像
    public void LoaderCircle(final Context ct, String url, final ImageView iv) {
        Glide.with(ct).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv);
//        Glide.with(ct).load(url).asBitmap().centerCrop().placeholder(drawable).into(new BitmapImageViewTarget(iv) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(ct.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                iv.setImageDrawable(circularBitmapDrawable);
//            }
//        });
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        Glide.with(context).load(imageUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView == null) {
                    return false;
                }
                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                return false;
            }
        }).into(imageView);
    }

}
