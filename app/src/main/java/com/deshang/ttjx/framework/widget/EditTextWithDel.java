package com.deshang.ttjx.framework.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.deshang.ttjx.R;
import mvp.cn.util.DensityUtil;


/**
 * 类描述：待清除按钮的edittext
 * 作者：LiZhe On 2017/1/16 0016
 */
public class EditTextWithDel extends EditText {
    private final static String TAG = "EditTextWithDel";
    private Drawable imgAble;
    private Context mContext;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        imgAble = mContext.getResources().getDrawable(R.mipmap.ic_clret_delete);
        imgAble.setBounds(new Rect(0,0, DensityUtil.dip2px(mContext,20f),DensityUtil.dip2px(mContext,20f)));
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (StringUtil.isNotNull(s.toString()))
//                    setCompoundDrawables(null, null, imgAble, null);
//                else
//                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        });
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY)){
                setText("");
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
