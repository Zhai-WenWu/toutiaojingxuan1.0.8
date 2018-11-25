package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.tab1.adapter.RecommendNewsAdapter;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 退出登录Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class LogOutDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private TextView cancel, sure;

    public LogOutDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_log_out, null);
        setContentView(view);

        initView(view);
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
                    listener.onClick();
                }
                break;
        }
    }

    private OnSureClickListener listener;

    public interface OnSureClickListener {
        void onClick();
    }

    public void setOnSureClickListener(OnSureClickListener listener) {
        this.listener = listener;
    }

}
