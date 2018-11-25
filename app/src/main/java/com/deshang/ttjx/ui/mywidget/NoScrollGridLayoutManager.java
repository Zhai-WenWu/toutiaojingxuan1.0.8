package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * 不可滚动的RecyclerView的GridLayoutManager
 * Created by L on 2018/6/14.
 */

public class NoScrollGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
