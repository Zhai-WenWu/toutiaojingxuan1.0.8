package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.deshang.ttjx.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by L on 2018/8/14.
 */

public class NormalToast {

    private WindowManager.LayoutParams mParams;
    private boolean mIsShow;
    private WindowManager mWdm;
    private View mToastView;
    private Timer mTimer;

    private NormalToast(Context context, String text) {
        mIsShow = false;//记录当前Toast的内容是否已经在显示
        mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //通过Toast实例获取当前android系统的默认Toast的View布局
//        mToastView = Toast.makeText(context, text, Toast.LENGTH_SHORT).getView();
        mToastView = LayoutInflater.from(context).inflate(R.layout.toast_top_text, null);
        TextView textView = (TextView) mToastView.findViewById(R.id.text);
        textView.setText(text);
        mTimer = new Timer();
        //设置布局参数
        setParams();
    }

    public static NormalToast makeText(Context context, String text) {
        return new NormalToast(context, text);
    }

    private void setParams() {
        //  Auto-generated method stub
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.MyToast_top;//设置进入退出动画效果
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = Gravity.TOP;
        mParams.y = 0;
    }

    public void show() {
        if (!mIsShow) {//如果Toast没有显示，则开始加载显示
            mIsShow = true;
            mWdm.addView(mToastView, mParams);//将其加载到windowManager上
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mWdm.removeView(mToastView);
                    mIsShow = false;
                }
            }, (long) (2000));
        }
    }
}
