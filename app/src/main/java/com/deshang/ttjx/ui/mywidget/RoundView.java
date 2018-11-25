package com.deshang.ttjx.ui.mywidget;

/**
 * Created by L on 2018/5/31.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;

/**
 * 自定义进度圆环
 */
public class RoundView extends View {
    /**
     * 圆环宽度,默认半径的1／5
     */
    private float mRingWidth = 0;
    /**
     * 圆环颜色,默认 #CBCBCB
     */
    private int mRingColor = 0;

    /**
     * 圆环半径,默认：Math.min(getHeight()/2,getWidth()/2)
     */
    private float mRadius = 0;
    /**
     * 底环画笔
     */
    private Paint mRingPaint;

    private OnSubmitListener listener;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        mRingWidth = typedArray.getDimension(R.styleable.RoundView_ring_width, 3);
        mRingColor = typedArray.getColor(R.styleable.RoundView_ring_color, Color.parseColor("#CBCBCB"));
        mRadius = typedArray.getDimension(R.styleable.RoundView_radius, 0);
        init();
    }

    private float angle = 0f;

    /**
     * 设置弧累加
     *
     * @param angles 每次加的度数
     */
    public void setArc(float angles) {
        if (angle >= 360) {
            LogUtils.d("Constants.IS_LOGIN:" + Constants.IS_LOGIN);
            if (Constants.IS_LOGIN == 1)
                return;
            if ("".equals(SharedPrefHelper.getInstance().getToken())) {
                Constants.IS_LOGIN = 1;
            } else {
                Constants.IS_LOGIN = 0;
            }
            //  请求金币接口
            if (null != listener) {
                listener.onSubmit();
            }
        } else {
            angle += angles;
        }
        Constants.ROUND_ANGLE = angle;
        invalidate();
    }

    public interface OnSubmitListener {
        void onSubmit();
    }

    public void setOnSubmitListener(OnSubmitListener listener) {
        this.listener = listener;
    }

    // 设置初始弧的度数
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * 初始化
     */
    private void init() {
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);// 抗锯齿效果
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mRingWidth);
        mRingPaint.setColor(mRingColor);// 背景
    }

    private RectF oval;

    @Override
    public void onDraw(Canvas canvas) {
        if (oval == null) {
            oval = new RectF(mRingWidth, mRingWidth, getWidth() - mRingWidth, getHeight() - mRingWidth);
            /*mRingPaint1 = new Paint();
            mRingPaint1.setAntiAlias(true);// 抗锯齿效果
            mRingPaint1.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
            mRingPaint1.setStyle(Paint.Style.STROKE);
            mRingPaint1.setStrokeWidth(mRingWidth);
            mRingPaint1.setColor(getResources().getColor(R.color.gray_deep));// 背景
            LogUtils.d("画圆");
            canvas.drawArc(oval, -90, 360, true, mRingPaint1);*/
        }
        // 底环
        canvas.drawArc(oval, -90, angle, false, mRingPaint);
    }
}
