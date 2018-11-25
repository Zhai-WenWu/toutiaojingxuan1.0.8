package com.deshang.ttjx.ui.mywidget.mychart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 */
public class MXAxisRenderer extends XAxisRenderer {
    //这里为了方便把X轴标签放到这里了，其实可以通过其他方式能拿到要设置的X轴标签就可以
    private String[] xLable = {"字符串1", "字符串2", "字符串3", "字符串4", "字符串5"};

    public MXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    //重写drawLabels
   /* @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        //把源码代码复制过来
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();

        float[] positions = new float[mXAxis.mEntryCount * 2];

        for (int i = 0; i < positions.length; i += 2) {

            // only fill x values
            if (centeringEnabled) {
                positions[i] = mXAxis.mCenteredEntries[i / 2];
            } else {
                positions[i] = mXAxis.mEntries[i / 2];
            }
        }

        mTrans.pointValuesToPixel(positions);

        for (int i = 0; i < positions.length; i += 2) {

            float x = positions[i];

            if (mViewPortHandler.isInBoundsX(x)) {

                //修改源码 这里添加要设置的X轴的label
                String label = xLable[i / 2];
                // String label = mXAxis.getValueFormatter().getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);

                if (mXAxis.isAvoidFirstLastClippingEnabled()) {
                    // avoid clipping of the last  mXAxis.mEntryCount - 1为x轴坐标的标签数
//                    if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);

                        if (width > mViewPortHandler.offsetRight() * 2
                                && x + width > mViewPortHandler.getChartWidth())
                            x -= width / 2;
                        // avoid clipping of the first
//                    } else if (i == 0) {

//                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);
//                        x += width / 2;
//                    }
                }
                drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees);
            }
        }

    }*/
}
