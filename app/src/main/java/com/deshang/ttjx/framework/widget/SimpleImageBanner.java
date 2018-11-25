package com.deshang.ttjx.framework.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.bean.BannerItem;


public class SimpleImageBanner extends BaseIndicatorBanner<BannerItem, SimpleImageBanner> {
    private ColorDrawable colorDrawable;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerItem item = mDatas.get(position);
        tv.setText(item.title);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.activity_main, null);
//        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);
//        final BannerItem item = mDatas.get(position);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        String imgUrl = item.imgUrl;
//        if (!TextUtils.isEmpty(imgUrl)) {
//
//            Glide.with(mContext)
//                    .load(imgUrl)
//                    .placeholder(colorDrawable)
//                    .into(iv);
//        } else {
//            iv.setImageDrawable(colorDrawable);
//        }
        return inflate;
    }


}
