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
 * 新版本弹出Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class NewAppUpDataDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public NewAppUpDataDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.dialog_new_app, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        ImageView know = (ImageView) view.findViewById(R.id.know);
        know.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.know:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        SharedPrefHelper.getInstance().setNewAppFirst(1);
        super.dismiss();
    }
}
