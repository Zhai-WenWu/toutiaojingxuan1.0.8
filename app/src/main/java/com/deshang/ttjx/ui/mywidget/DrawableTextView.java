package com.deshang.ttjx.ui.mywidget;

/**
 * Created by L on 2018/7/7.
 */

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.drawable.Drawable;

import android.util.AttributeSet;

/**
 * ted by Administrator on 2017/2/28.
 */

public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    public DrawableTextView(Context context) {

        this(context, null);

    }

    public DrawableTextView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = getCompoundDrawables();
        // 得到drawableLeft设置的drawable对象

        Drawable leftDrawable = drawables[0];

        if (leftDrawable != null) {

            // 得到leftDrawable的宽度

            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();

            // 得到drawable与text之间的间距

            int drawablePadding = getCompoundDrawablePadding();

            // 得到文本的宽度

            int textWidth = (int) getPaint().measureText(getText().toString().trim());

            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;

            canvas.save();

            canvas.translate((getWidth() - bodyWidth) / 2, 0);

        }
        super.onDraw(canvas);
    }
}
