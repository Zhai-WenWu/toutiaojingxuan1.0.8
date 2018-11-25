package com.deshang.ttjx.ui.mywidget.scroll_text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.deshang.ttjx.R;

import java.util.ArrayList;

/**
 * 文本自动垂直轮播
 * <p/>
 * Created by zhenguo on 3/4/15.
 */
public class AutoScrollTextView extends TextSwitcher implements
        ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 1001;
    private static final int FLAG_STOP_AUTO_SCROLL = 1002;

    /**
     * 文字居中
     */
    private int centerType = 0;
    /**
     * 轮播时间间隔
     */
    private int scrollDuration = 2000;
    /**
     * 动画时间
     */
    private int animDuration = 1500;

    /**
     * 文字Padding
     */
    private int mPadding = 0;
    /**
     * 文字大小
     */
    private float mTextSize = 14;
    /**
     * 文字颜色
     */
    private int textColor = Color.BLACK;

    private OnItemClickListener itemClickListener;
    private Context mContext;
    /**
     * 当前显示Item的ID
     */
    private int currentId = -1;
    private ArrayList<String> textList;
    private Handler handler;

    public AutoScrollTextView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.autoScrollHeight);
        mTextSize = a.getDimension(R.styleable.autoScrollHeight_textSize, 12);
        mPadding = (int) a.getDimension(R.styleable.autoScrollHeight_padding, 0);
        scrollDuration = a.getInteger(R.styleable.autoScrollHeight_scrollDuration, 2000);
        animDuration = a.getInteger(R.styleable.autoScrollHeight_animDuration, 1500);
        textColor = a.getColor(R.styleable.autoScrollHeight_textColor, Color.BLACK);
        centerType = a.getInteger(R.styleable.autoScrollHeight_centerType, 0);
        a.recycle();
        init();
    }

    private void init() {
        textList = new ArrayList<>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 0) {
                            currentId++;
                            setText(textList.get(currentId % textList.size()));
                        }
                        if (firstStart == 1){
                            firstStart = 0;
                            handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, 100);
                        } else {
                            handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, scrollDuration);
                        }
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };

        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, 100, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new LinearInterpolator());
//        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -100);
        out.setDuration(animDuration);
        out.setInterpolator(new LinearInterpolator());
//        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    @Override
    public void setText(CharSequence text) {
        final TextView t = (TextView) getNextView();
        if (type == 1) {
//            t.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.voice), null, null, null);
//            t.setCompoundDrawablePadding(3);
//            t.setGravity(Gravity.CENTER);
            t.setText(text);
        } else {
            t.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.gold_money), null, null, null);
            t.setCompoundDrawablePadding(3);
            t.setGravity(Gravity.CENTER_VERTICAL);
            t.setText(text);
        }
        showNext();
    }

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    private int firstStart = 0;

    /**
     * 开始轮播
     */
    public void startAutoScroll() {
//        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
        firstStart = 1;
        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, 10);
    }

    /**
     * 停止轮播
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }

    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        if (centerType == 1) {
            t.setGravity(Gravity.CENTER);
        } else {
            t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(textColor);
        t.setTextSize(mTextSize);

        t.setClickable(true);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(currentId % textList.size());
                }
            }
        });

        return t;
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {

        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

}