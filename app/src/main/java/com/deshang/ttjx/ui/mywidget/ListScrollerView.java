package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by zyf73 on 2017/3/9.
 */

public class ListScrollerView extends ListView implements Pullable {

    /**
     * item view
     */
    private View itemView;


    /**
     * scroller  滑动view
     */
    private Scroller scroller;

    /**
     * 当前滑动的 position   itemview
     */
    private int slidePosition;

    /**
     * 右侧 长度
     */
    private int rightLength = 0;

    /**
     * 按下时候的X，Y
     */
    private int downX = 0;
    private int downY = 0;

    /**
     * 侧滑是否完成
     */
    private boolean isSlided = false;

    /**
     * 是否可以滑动
     */
    private boolean canMove = false;

    /**
     * 滑动的最小距离
     */
    private int mTouchSlop;


    public ListScrollerView(Context context) {
        super(context);
    }

    public ListScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int lastX = (int) ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isSlided) {
                    scrollBack();
                    return false;
                }
                if (!scroller.isFinished()) {
                    //滑动还没有结束
                    return false;
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                slidePosition = pointToPosition(downX, downY);
                if (slidePosition == AdapterView.INVALID_POSITION) {
                    //无效的position
                    return super.onTouchEvent(ev);
                }
                if (isDelete && (slidePosition == 0 || slidePosition == 1)) {
                    itemView = null;
                    return true;
                }

                //获取当前滑动的 itemView
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                this.rightLength = -itemView.getPaddingRight();
                break;
            case MotionEvent.ACTION_MOVE:
                //条件  当前滑动状态为true  滑动itemview 是有效的
                // x轴滑动距离大于最低滑动  y轴滑动距离小于最低滑动
                if (!canMove
                        && slidePosition != AdapterView.INVALID_POSITION
                        && Math.abs(ev.getX() - downX) > mTouchSlop
                        && Math.abs(ev.getY() - downY) < mTouchSlop) {
                    int offsetX = downX - lastX;

                    //滑动距离大于0 之后设置
                    if (offsetX > 0) {
                        canMove = true;
                    } else {
                        canMove = false;
                    }

                    //* 侧滑时ListView的OnItemClickListener事件的屏蔽 */
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent
                            .setAction(MotionEvent.ACTION_CANCEL
                                    | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                }

                if (itemView != null && canMove) {
                    //关闭 listview 的上下滚动事件
                    requestDisallowInterceptTouchEvent(true);

                    int deltaX = downX - lastX;

                    if (deltaX > 0) {
                        itemView.scrollTo(deltaX, 0);
                    } else {
                        itemView.scrollTo(0, 0);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (canMove) {
                    canMove = false;
                    scrollByDistanceX();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 根据手指 listview 距离判断是滚动到原始位置 还是 展开位置
     */
    private void scrollByDistanceX() {
        if (itemView != null && itemView.getScrollX() > 0) {
            if (itemView.getScrollX() >= rightLength / 2) {
                //向右滑动  展开
                scrollLeft();
            } else {
                //向左滑动  关闭
                scrollBack();
            }
        } else {
            //向左滑动  关闭
            scrollBack();
        }
    }

    /**
     * 向左滑动 。
     */
    private void scrollLeft() {
        if (itemView != null) {
            isSlided = true;
            final int dalia = rightLength - itemView.getScrollX();
            // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
            scroller.startScroll(itemView.getScrollX(), 0, dalia, 0, Math.abs(dalia));
            postInvalidate();
        }

    }

    private void scrollBack() {
        if (itemView != null) {
            isSlided = false;
            scroller.startScroll(itemView.getScrollX(), 0, -itemView.getScrollX(), 0, Math.abs(itemView.getScrollX()));
            postInvalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset() && itemView != null) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean canPullDown() {

        if (!scroller.isFinished() || canMove) {
            //滑动还没有结束
            return false;
        }
        try {
            if (getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (getFirstVisiblePosition() == 0
                    && getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (!scroller.isFinished() || canMove) {
            //滑动还没有结束
            return false;
        }
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }

    /**
     * 复原
     */
    public void slideBack() {
        this.scrollBack();
    }

    boolean isDelete = false;

    /**
     * 是否删除 第一个 第二个
     * 八字算命 使用
     *
     * @param isDelete
     */
    public void setIsDeletes(boolean isDelete) {
        this.isDelete = isDelete;
    }

}
