package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;

/**
 * 卖出 Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class SaleTBDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private EditText edit;
    private String tbNum;
    private String rate;
    private TextView hint;

    /**
     * @param context
     * @param tbNum
     * @param rate
     */
    public SaleTBDialog(@NonNull Context context, String tbNum, String rate) {
        super(context, R.style.Dialog);
        this.context = context;
        this.tbNum = tbNum;
        this.rate = rate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.dialog_sale_tb, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        edit = (EditText) view.findViewById(R.id.edit);
        TextView tb_num = (TextView) view.findViewById(R.id.tb_num);
        tb_num.setText(tbNum);
        TextView money_one_tb = (TextView) view.findViewById(R.id.money_one_tb);
        money_one_tb.setText("1红钻 = " + rate + "元");
        TextView sale_all = (TextView) view.findViewById(R.id.sale_all);
        sale_all.setOnClickListener(this);
        ImageView sure = (ImageView) view.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        hint = (TextView) view.findViewById(R.id.hint);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (edit.getText().length() == 0) {
                    hint.setVisibility(View.VISIBLE);
                    hint.setText("提示：请输入卖出数量。");
                } else if (Double.valueOf(edit.getText().toString().trim()) == 0) {
                    hint.setVisibility(View.VISIBLE);
                    hint.setText("提示：卖出数量不能为0。");
                } else if (Double.valueOf(edit.getText().toString().trim()) > Double.valueOf(tbNum)) {
                    hint.setVisibility(View.VISIBLE);
                    hint.setText("提示：您可出售红钻数不足。");
                } else {
                    dismiss();
                    if (listener != null) {
                        listener.onSureClick(edit.getText().toString().trim());
                    }
                }
                break;

            case R.id.sale_all:
                double a = Double.valueOf(tbNum);
                edit.setText(String.valueOf((int) a));
                edit.setSelection(edit.getText().toString().trim().length());
                break;

            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public interface OnSureListener {
        void onSureClick(String tbNum);
    }

    private OnSureListener listener;

    public void setOnSureListener(OnSureListener listener) {
        this.listener = listener;
    }

}
