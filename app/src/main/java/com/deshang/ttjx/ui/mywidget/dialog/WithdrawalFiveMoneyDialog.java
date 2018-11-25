package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.utils.UserPathUtils;

/**
 * 每日弹出Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class WithdrawalFiveMoneyDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public WithdrawalFiveMoneyDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.dialog_withdrawal_five, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        ImageView img_share = (ImageView) view.findViewById(R.id.img_share);
        img_share.setOnClickListener(this);
        ImageView withdrawal = (ImageView) view.findViewById(R.id.withdrawal);
        withdrawal.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.img_share:
                dismiss();
                if (listener != null) {
                    listener.onClick(1);
                }
                break;

            case R.id.withdrawal:
                dismiss();
                if (listener != null) {
                    listener.onClick(2);
                }
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(34);
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(int type);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
