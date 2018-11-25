package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;

/**
 * 提现弹窗
 */

public class WalletGXDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String tvsalehz;
    private String tvsalemoney;
    private TextView tishi;

    public WalletGXDialog(Context context, String tvsalehz, String tvsalemoney) {
        super(context, R.style.Dialog);
        this.context = context;
        this.tvsalehz = tvsalehz;
        this.tvsalemoney = tvsalemoney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.wallet_gx_dialog, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        //卖出金额
        TextView tv_sale_hz = (TextView) view.findViewById(R.id.tv_sale_hz);
        tv_sale_hz.setText(tvsalehz);
        //获得钱数
        TextView tv_sale_money = (TextView) view.findViewById(R.id.tv_sale_money);
        tv_sale_money.setText(tvsalemoney);

        ImageView close = (ImageView) view.findViewById(R.id.iv_close);
        close.setOnClickListener(this);

        ImageView sure = (ImageView) view.findViewById(R.id.iv_neg);
        sure.setOnClickListener(this);
        ImageView cancel = (ImageView) view.findViewById(R.id.iv_pos);
        cancel.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_neg:
                //去提现
                dismiss();
                if (listener != null) {
                    listener.onWithdrawalClick(0);
                }
                break;

            case R.id.iv_close:
                dismiss();
                break;

            case R.id.iv_pos:
                //赚更多
                dismiss();
                if (listener != null) {
                    listener.onWithdrawalClick(1);
                }
                break;
        }
    }

    public interface OnToWithdrawalListener {
        void onWithdrawalClick(int type);
    }

    private OnToWithdrawalListener listener;

    public void setOnSureListener(OnToWithdrawalListener listener) {
        this.listener = listener;
    }

}
