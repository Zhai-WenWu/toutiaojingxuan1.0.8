package com.deshang.ttjx.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.framework.imageload.GlideLoading;

import com.deshang.ttjx.R;
import mvp.cn.util.StringUtil;


public class MySettingView extends RelativeLayout {

    private Context ct;
    private String tv_left;
    private Drawable iv_left;
    private String tv_right;
    private Drawable iv_right;
    private ImageView s_iv_left;
    private TextView s_tv_left;
    private ImageView s_iv_right;
    private TextView s_tv_right;

    public MySettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ct = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        isInEditMode();
        View view = View.inflate(ct, R.layout.f_setting_view, this);
        s_iv_left = (ImageView) view.findViewById(R.id.s_iv_left);
        s_tv_left = (TextView) view.findViewById(R.id.s_tv_left);
        s_iv_right = (ImageView) view.findViewById(R.id.s_iv_right);
        s_tv_right = (TextView) view.findViewById(R.id.s_tv_right);

        TypedArray typedArray = ct.obtainStyledAttributes(attrs, R.styleable.setting);
        tv_left = typedArray.getString(R.styleable.setting_tv_left);
        iv_left = typedArray.getDrawable(R.styleable.setting_iv_left);
        tv_right = typedArray.getString(R.styleable.setting_tv_right);
        iv_right = typedArray.getDrawable(R.styleable.setting_iv_right);

        setResource();
    }

    public void setResource() {
        if (!StringUtil.isNullOrEmpty(tv_left)) {
            s_tv_left.setVisibility(View.VISIBLE);
            s_tv_left.setText(tv_left);
        } else {
            s_tv_left.setVisibility(View.GONE);
        }
        if (!StringUtil.isNullOrEmpty(tv_right)) {
            s_tv_right.setVisibility(View.VISIBLE);
            s_tv_right.setText(tv_right);
        } else {
            s_tv_right.setVisibility(View.GONE);
        }
        if (iv_left != null) {
            s_iv_left.setVisibility(View.VISIBLE);
            s_iv_left.setImageDrawable(iv_left);
        } else {
            s_iv_left.setVisibility(View.GONE);
        }
        if (iv_right != null) {
            s_iv_right.setVisibility(View.VISIBLE);
            s_iv_right.setImageDrawable(iv_right);
        } else {
            s_iv_right.setVisibility(View.GONE);
        }

    }

    public void setResInit(int tv_left, int iv_left, int tv_right, int iv_right, int colorResId) {
        this.tv_left = getResources().getString(tv_left);
        this.tv_right = getResources().getString(tv_right);
        this.iv_left = getResources().getDrawable(iv_left);
        this.iv_right = getResources().getDrawable(iv_right);
        setResource();

        setTextColor(colorResId);
    }

    public void setRightText(Object right) {
        if (right instanceof String) {
            tv_right = (String) right;
        } else if (right instanceof Integer) {
            tv_right = getResources().getString((int) right);
        }

        setResource();
    }

    public String getRightText() {
        return s_tv_right.getText().toString().trim();
    }

    public void setLeftText(Object left) {
        if (left instanceof String) {
            tv_left = (String) left;
        } else if (left instanceof Integer) {
            tv_left = getResources().getString((int) left);
        }

        setResource();
    }

    public void loadLeftImg(String url) {
        GlideLoading.getInstance().loadImgUrlNyImgLoader(ct,url, s_iv_left);
    }

    public void setLeftIvWidth(int width) {
        LayoutParams params = (LayoutParams) s_iv_left.getLayoutParams();
        params.height = width;
        params.width = width;
        s_iv_left.setLayoutParams(params);
    }

    public void setTextColor(int colorResId) {
        s_tv_left.setTextColor(getResources().getColor(colorResId));
        s_tv_right.setTextColor(getResources().getColor(colorResId));
    }

    public void setRightNum(String right) {
        tv_right = right;
        RelativeLayout.LayoutParams params = (LayoutParams) s_tv_right.getLayoutParams();
        params.rightMargin = 100;
        s_tv_right.setLayoutParams(params);
        s_tv_right.setTextColor(getResources().getColor(R.color.white));
//        s_tv_right.setBackgroundResource(R.mipmap.iv_red_notice);
        setResource();
    }

    public void hideRight() {
        tv_right = null;
        setResource();
    }
}
