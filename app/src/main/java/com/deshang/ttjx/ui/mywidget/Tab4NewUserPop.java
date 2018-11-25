package com.deshang.ttjx.ui.mywidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.deshang.ttjx.R;

/**
 * Created by Administrator on 2018/10/2.
 */

public class Tab4NewUserPop extends PopupWindow {

    private Context context;

    public Tab4NewUserPop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.window_new_user_view_tab4, null);
//        view.setBackgroundColor(Color.TRANSPARENT);//给view视图设置背景颜色（灰色），不然看不到效果
        //实例化控件
        //ImageView go = (ImageView) view.findViewById(R.id.go);
        ImageView know = (ImageView) view.findViewById(R.id.know);
        this.setContentView(view);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
//        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);

       /* go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//点击登录后自动消失
                if (listener != null) {
                    listener.onClick();
                }
            }
        });*/
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//点击登录后自动消失
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
    }

    public interface OnClickListener {
        void onClick();
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
