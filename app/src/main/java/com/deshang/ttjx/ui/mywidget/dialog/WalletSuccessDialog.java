package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.ui.tab4.activity.ShowIncomeActivity;

/**
 * 提现弹窗
 */

public class WalletSuccessDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String tvsalemoney;

    public WalletSuccessDialog(Context context, String tvsalemoney) {
        super(context, R.style.Dialog);
        this.context = context;
        this.tvsalemoney = tvsalemoney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.wallet_sucess_dialog, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        //提现多少钱
        TextView tv_sale_hz = (TextView) view.findViewById(R.id.tv_tx);
        tv_sale_hz.setText(tvsalemoney);

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
                //晒收入
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


    public interface OnGetMoreClickListener {
        void onWithdrawalClick(int type);
    }

    private OnGetMoreClickListener listener;

    public void setOnGetMoreClickListener(OnGetMoreClickListener listener) {
        this.listener = listener;
    }
}
