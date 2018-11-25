package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.UserPathUtils;

/**
 * 每日弹出Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class NewUserRewardDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private ImageView share_main, close;

    public NewUserRewardDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_new_user_reward, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        share_main = (ImageView) view.findViewById(R.id.red_top);
        share_main.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.red_top:
                dismiss();
                if (listener != null) {
                    listener.onClick();
                }
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(22);
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    private OnDismissClickListener dismissClickListener;

    public interface OnDismissClickListener {
        void onClick();
    }

    public void setOnDismissClickListener(OnDismissClickListener listener) {
        this.dismissClickListener = listener;
    }

    @Override
    public void dismiss() {
        if (dismissClickListener != null) {
            dismissClickListener.onClick();
        }
        SharedPrefHelper.getInstance().setNewUser(false);
        super.dismiss();
    }
}
