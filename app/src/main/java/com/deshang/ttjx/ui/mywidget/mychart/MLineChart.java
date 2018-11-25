package com.deshang.ttjx.ui.mywidget.mychart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by 13364 on 2017/11/9.
 */

public class MLineChart extends LineChart {

    public MLineChart(Context context) {
        super(context);
    }

    public MLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
//        mXAxisRenderer = new MXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
