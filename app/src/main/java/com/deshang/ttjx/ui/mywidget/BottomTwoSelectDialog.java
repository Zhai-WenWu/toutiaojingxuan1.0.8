package com.deshang.ttjx.ui.mywidget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.framework.utils.ScreenUtils;

import com.deshang.ttjx.R;

public class BottomTwoSelectDialog extends Dialog implements View.OnClickListener {

    private Context ct;
    private String one, two;

    public BottomTwoSelectDialog(Context context, int theme, String oneTitle, String twoTitle) {
        super(context, theme);
        this.ct = context;
        this.one = oneTitle;
        this.two = twoTitle;
        initView();
    }

    private void initView() {
        View view = View.inflate(ct, R.layout.bottom_pay_dialog, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        TextView tvOne = (TextView) view.findViewById(R.id.one);
        TextView tvTwo = (TextView) view.findViewById(R.id.two);

        tvOne.setText(one);
        tvTwo.setText(two);

        tvCancel.setOnClickListener(this);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);

        int width = ScreenUtils.getScreenWidth(ct);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, -2);
        view.setLayoutParams(params);
        setContentView(view, params);
        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(p);
        getWindow().setWindowAnimations(R.style.AnimationDialog);

    }

    private ClickListener listener2;

    public void setListener2(ClickListener listener2) {
        this.listener2 = listener2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.one:
                if (listener2 != null) {
                    listener2.onClickListener(false);
                }
                dismiss();
                break;
            case R.id.two:
                if (listener2 != null) {
                    listener2.onClickListener(true);
                }
                dismiss();
                break;
        }
    }

    public interface ClickListener {
        void onClickListener(boolean isPersonal);
    }
}
