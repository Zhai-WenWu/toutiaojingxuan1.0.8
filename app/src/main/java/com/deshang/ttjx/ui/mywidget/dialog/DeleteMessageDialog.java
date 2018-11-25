package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.deshang.ttjx.R;

/**
 * 提示文字弹出窗
 */

public class DeleteMessageDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private View view;

    private TextView cancel, sure;

    public DeleteMessageDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(context, R.layout.dialog_show_message, null);
        setContentView(view);
        initView(view);
        setCancelable(true);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        cancel = (TextView) view.findViewById(R.id.cancel);
        sure = (TextView) view.findViewById(R.id.sure);

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;

            case R.id.sure:
                dismiss();
                if (listener != null) {
                    listener.onClearClick();
                }
                break;
        }
    }

    private OnClearClickListener listener;

    public void setOnClearClickListener(OnClearClickListener listener) {
        this.listener = listener;
    }

    public interface OnClearClickListener {
        void onClearClick();
    }

}
