package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.login.activity.NewLoginActivity;

/**
 * 未登录状态下 弹出Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class NotLoginDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private ImageView share_main, close;

    public NotLoginDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_not_login, null);
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
                UIManager.turnToAct(context, NewLoginActivity.class);
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(21);
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }
}
