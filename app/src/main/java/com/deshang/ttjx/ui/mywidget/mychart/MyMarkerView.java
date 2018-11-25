package com.deshang.ttjx.ui.mywidget.mychart;

import android.content.Context;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * 自定义MyMarkerView
 */
public class MyMarkerView extends MarkerView {

    private TextView tvTime;
//    private List<MeBean.ReturnBeanX.ReturnBean> data;

    public MyMarkerView(Context context, int layoutResource/*, List<MeBean.ReturnBeanX.ReturnBean> data*/) {
        super(context, layoutResource);
        tvTime = (TextView) findViewById(R.id.time);
//        this.data = data;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        int index = e.getXIndex();
//        tvTime.setText(data.get(index).getDay_cash_price());
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        if (Constants.width != 0) {
            if (xpos < Constants.width / 2) {
                return 0;
            } else if (xpos > Constants.width / 2) {
                return -getWidth();
            }
        }
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}