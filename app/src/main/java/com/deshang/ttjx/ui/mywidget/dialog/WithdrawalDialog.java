package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.utils.ScreenUtils;
import com.deshang.ttjx.framework.widget.CircleImageView;

public class WithdrawalDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ClickListener listener;
    private String userName, headerUrl;
    private int type;
    private TextView tvCancel;
    public WithdrawalDialog(Context context, String userName, String headerUrl, int type) {
        super(context, R.style.dialog_style);
        this.context = context;
        this.userName = userName;
        this.headerUrl = headerUrl;
        this.type = type;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_withdrawal, null);
        RelativeLayout ll_circle = (RelativeLayout) view.findViewById(R.id.ll_circle);
        CircleImageView header = (CircleImageView) view.findViewById(R.id.header);
        tvCancel = (TextView) view.findViewById(R.id.cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.sure);
        TextView nickName = (TextView) view.findViewById(R.id.user_name);
        TextView content = (TextView) view.findViewById(R.id.content);

        if (type == 1) {
            // 没有提现过
            ll_circle.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            GlideLoading.getInstance().loadImgUrlNyImgLoader(context, headerUrl, header);
            nickName.setText(userName);
            content.setText("即将提现至您的微信账号");
            tvSure.setText("确定");
        } else {
            nickName.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            content.setText("您尚未设置微信账户");
            tvSure.setText("去绑定");
        }

        tvCancel.setOnClickListener(this);
        tvSure.setOnClickListener(this);

        int width = ScreenUtils.getScreenWidth(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, -2);
        view.setLayoutParams(params);
        setContentView(view, params);
        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(p);
        getWindow().setWindowAnimations(R.style.AnimationDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;

            case R.id.sure:
                dismiss();
                if (listener != null) {
                    listener.onClickListener(type);
                }
                break;
        }
    }


    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onClickListener(int type);
    }
}
