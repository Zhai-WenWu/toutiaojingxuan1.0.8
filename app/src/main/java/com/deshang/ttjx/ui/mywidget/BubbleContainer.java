package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;

import com.deshang.ttjx.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yy on 2018/1/8.
 * 描述：浮动控件父容器，用于设定子控件初始位置
 */

public class BubbleContainer extends RelativeLayout {
    List<Integer> listX = new ArrayList<>();
    List<Integer> listY = new ArrayList<>();
    SparseArray<View> array = new SparseArray<>();

    public void setChildPosition(int posx, int posy, View view) {
        listX.add(posx);
        listY.add(posy);
        array.put(posx, view);
    }

    public void clearData() {
        listX.clear();
        listY.clear();
        array.clear();
    }

    public void removeData(int position) {
        if (listX.size() - 1 < position) {
            return;
        }
//        listX.remove(position);
//        listY.remove(position);
    }

    @Override
    public void removeView(View view) {
        /*for1:
        for (int i = 0; i < getChildCount(); i++) {
            if (view == array.get(i)) {
                listX.remove(i);
                listY.remove(i);
                array.remove(i);
                break for1;
            }
        }*/
        super.removeView(view);
    }

    public BubbleContainer(Context context) {
        super(context);
    }

    public BubbleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (child.getVisibility() != GONE) {
                child.layout(listX.get(i), listY.get(i), childWidth + listX.get(i), childHeight + listY.get(i));
            }
        }
    }
}
