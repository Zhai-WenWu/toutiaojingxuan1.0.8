package com.deshang.ttjx.ui.mywidget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by L on 2018/11/1.
 */
public class ExtendedWebView extends WebView {
    public ExtendedWebView(Context context) {
        super(context);
    }

    public ExtendedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean canScrollVertical(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        else return (direction < 0) ? (offset > 0) : (offset < range - 1);
    }
}