package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.deshang.ttjx.framework.utils.LogUtils;

/**
 * Created by L on 2018/8/9.
 */

public class WebViewScrollView extends ScrollView {

    public WebViewScrollView(Context context) {
        super(context);
    }

    public WebViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
//        LogUtils.d("y：" + y + " oldy：" + oldy + " 差值：" + (y - oldy));
        if (scrollViewListener != null && y > 0) {
            scrollViewListener.onScrollChanged(x, y, oldx, oldy);//x - oldx, y - oldy
        }
        View view = this.getChildAt(0);
        if (this.getHeight() + this.getScrollY() == view.getHeight()) {
            if (listener != null) {
                listener.scrollToBottom();
            }
        }
    }

    private ScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }

    private ScrollBottomListener listener = null;

    public void setScrollBottomListener(ScrollBottomListener listener) {
        this.listener = listener;
    }

    public interface ScrollBottomListener {
        void scrollToBottom();
    }
}
