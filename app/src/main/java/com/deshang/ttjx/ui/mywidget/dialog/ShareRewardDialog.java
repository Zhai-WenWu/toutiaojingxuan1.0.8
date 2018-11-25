package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;

/**
 * 分享有金币Dialog
 * Created by L on 2018年6月29日15:26:03
 */

public class ShareRewardDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private ImageView share, close;

    public ShareRewardDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_share_gold, null);
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
        share = (ImageView) view.findViewById(R.id.red_top);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.red_top:
                dismiss();
                UIManager.turnToAct(context, ApprenticeActivity.class);
                break;
        }
    }
}
