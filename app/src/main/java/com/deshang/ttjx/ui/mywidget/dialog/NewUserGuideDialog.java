package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;

/**
 * 新人专属 任务
 */

public class NewUserGuideDialog extends Dialog implements View.OnClickListener {
    private Context context;


    public NewUserGuideDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.new_user_dialog, null);

        setContentView(view);
        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        ImageView sure = (ImageView) view.findViewById(R.id.iv_task);
        sure.setOnClickListener(this);

        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_task:
                //跳转详情页
                dismiss();
                if (listener != null) {
                    listener.onClick();
                }
                break;
        }
    }

    private NewUserGuideDialog.OnClickListener listener;

    public interface OnClickListener {
        void onClick();
    }

    public void setOnClickListener(NewUserGuideDialog.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void dismiss() {
        SharedPrefHelper.getInstance().setNewUser(false);
        super.dismiss();
    }
}
