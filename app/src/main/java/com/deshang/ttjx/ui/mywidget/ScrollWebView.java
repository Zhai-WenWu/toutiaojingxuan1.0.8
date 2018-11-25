package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.deshang.ttjx.framework.application.SoftApplication;


/**
 * Created by L on 2018/5/31.
 */

public class ScrollWebView extends WebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ScrollWebView(final Context context) {
        super(SoftApplication.getContext());
    }

    public ScrollWebView(final Context context, final AttributeSet attrs) {
        super(SoftApplication.getContext(), attrs);
    }

    public ScrollWebView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(SoftApplication.getContext(), attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }


    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy);
    }

}
