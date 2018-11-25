package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * 不可滚动的RecyclerView的GridLayoutManager
 * Created by L on 2018/6/14.
 */

public class NoScrollLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = false;

    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
