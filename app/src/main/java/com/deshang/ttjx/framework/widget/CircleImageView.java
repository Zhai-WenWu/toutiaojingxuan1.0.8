package com.deshang.ttjx.framework.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CircleImageView extends AppCompatImageView {
    private ShapeDrawable drawable;
    private int radius;
    private BitmapShader shader;

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas cns) {
        if (radius == 0)
            radius = Math.min(getWidth(), getHeight());
        initDrawable();
        if (drawable != null) {
            drawable.setBounds((getWidth() >> 1) - (radius >> 1), (getHeight() >> 1) - (radius >> 1), (getWidth() >> 1) + (radius >> 1), (getHeight() >> 1) + (radius >> 1));
            drawable.getPaint().setShader(shader);
            drawable.draw(cns);
        } else
            super.onDraw(cns);
    }

    private void initDrawable() {
        if (getDrawable() != null && getDrawable() instanceof BitmapDrawable) {
            Bitmap bmp = Bitmap.createScaledBitmap(((BitmapDrawable) getDrawable()).getBitmap(), radius, radius, true);
            if (bmp != null) {
                shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                drawable = new ShapeDrawable(new OvalShape());
            }
        }

    }

}