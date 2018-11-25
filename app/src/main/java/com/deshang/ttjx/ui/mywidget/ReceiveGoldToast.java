package com.deshang.ttjx.ui.mywidget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.utils.UserPathUtils;

import java.lang.reflect.Field;

/**
 * Created by L on 2018/6/8.
 */

public class ReceiveGoldToast extends Toast {

    private Context context;
    private String text;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ReceiveGoldToast(Context context, String text) {
        super(context);
        this.context = context;
        this.text = text;
    }

    public static ReceiveGoldToast makeToast(Context context, String text, boolean normal) {
        ReceiveGoldToast toast = new ReceiveGoldToast(context, text);
        if (normal) { // 正常弹窗
            View view = LayoutInflater.from(context).inflate(R.layout.toast_normal, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(text);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, -40);
        } else { // 领取金币弹窗
            View view = LayoutInflater.from(context).inflate(R.layout.toast_receive_gold, null);
            TextView goldNum = (TextView) view.findViewById(R.id.gold_num);
            goldNum.setText(text);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, -80);
        }
        return toast;
    }

    public static ReceiveGoldToast makeToast(Context context, String text) {
        return makeToast(context, text, true);
    }

    public static ReceiveGoldToast makeToast(Context context, int resId) {
        return makeToast(context, context.getResources().getText(resId).toString(), true);
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        super.setGravity(gravity, xOffset, yOffset);
    }

    private static Toast toast;

    /**
     * 设置自定义View和Animation
     *
     * @param text 弹窗文字
     */
    public static void showGoldToast(Context context, String text) {
        UserPathUtils.commitUserPath(35);
        if (toast == null) {
            toast = new Toast(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_receive_gold, null);
        TextView goldNum = (TextView) view.findViewById(R.id.gold_num);
        goldNum.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, -80);
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            Class tnClass = mTNObject.getClass();
            Field paramsField = tnClass.getDeclaredField("mParams");
            // 由于WindowManager.LayoutParams mParams的权限是private
            paramsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) paramsField.get(mTNObject);
            layoutParams.windowAnimations = R.style.MyToast;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        toast.show();
    }

    /**
     * 设置自定义View和Animation
     *
     * @param text 弹窗文字
     */
    public static void showTopToast(Context context, String text) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_top_text, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.TOP, 0, 0);
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object mTNObject = mTNField.get(toast);
            Class tnClass = mTNObject.getClass();
            Field paramsField = tnClass.getDeclaredField("mParams");
            // 由于WindowManager.LayoutParams mParams的权限是private
            paramsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) paramsField.get(mTNObject);
            layoutParams.windowAnimations = R.style.MyToast_top;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        toast.show();
    }

}
