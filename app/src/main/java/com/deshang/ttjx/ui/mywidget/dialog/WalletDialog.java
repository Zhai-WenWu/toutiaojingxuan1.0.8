package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;

/**
 * 提现弹窗
 */

public class WalletDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText edit;
    private String tvMoneyCount;
    private TextView tishi;
    private String minMoney;

    public WalletDialog(Context context, String tvMoneyCount, String minMoney) {
        super(context, R.style.Dialog);
        this.context = context;
        this.tvMoneyCount = tvMoneyCount;
        this.minMoney = minMoney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.view_walletdialog, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        edit = (EditText) view.findViewById(R.id.et_num);
        edit.setHint("输入提现金额，最低" + minMoney + "元");
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        edit.setText(s);
                        edit.setSelection(s.length());
                    }
                }

                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edit.setText(s);
                    edit.setSelection(2);
                }

                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edit.setText(s.subSequence(0, 1));
                        edit.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });

        //总金额
        TextView money_one_tb = (TextView) view.findViewById(R.id.tv_money_sy);
        money_one_tb.setText(tvMoneyCount);
        tishi = (TextView) view.findViewById(R.id.tishi);
        TextView sale_all = (TextView) view.findViewById(R.id.tixian_all);
        sale_all.setOnClickListener(this);
        ImageView sure = (ImageView) view.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (edit.getText().length() == 0) {
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("提示：请输入提现金额。");
                } else if (Double.valueOf(edit.getText().toString().trim()) < Double.valueOf(minMoney)) {
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("提示：最低提现金额为" + minMoney + "元。");
                } else if (Double.valueOf(edit.getText().toString().trim()) > Double.valueOf(tvMoneyCount)) {
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("提示：您的账户余额不足，无法提现。");
                } else {
                    dismiss();
                    if (listener != null) {
                        listener.onSureClick(edit.getText().toString().trim());
                    }
                }
                break;

            case R.id.tixian_all:
                if (Double.valueOf(tvMoneyCount) < Double.valueOf(minMoney)) {
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("提示：最低提现金额为" + minMoney + "元。");
                } else {
                    edit.setText(tvMoneyCount);
                }
                // edit.setSelection(tvMoneyCount.length());
                break;

            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public interface OnSureListener {
        void onSureClick(String withdrawalNum);
    }

    private OnSureListener listener;

    public void setOnSureListener(OnSureListener listener) {
        this.listener = listener;
    }
}
