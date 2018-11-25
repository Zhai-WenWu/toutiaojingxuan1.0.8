package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;

/**
 * 新用户恭喜
 */

public class NewUserGXDialog extends Dialog implements View.OnClickListener {
    private Context context;

    public NewUserGXDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.user_gx_dialog, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {

        ImageView lq = (ImageView) view.findViewById(R.id.lq);
        lq.setOnClickListener(this);
        ImageView close = (ImageView) view.findViewById(R.id.iv_close);
        close.setOnClickListener(this);

        setCancelable(false);
    }

    @Override
                public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.lq:
                        //领取
                        dismiss();
                        if (listener != null) {
                            listener.onWithdrawalClick();
                        }
                        break;

                    case R.id.iv_close:
                        dismiss();
                        break;
        }
    }


    public interface OnGetMoreClickListener {
        void onWithdrawalClick();
    }

    private OnGetMoreClickListener listener;

    public void setOnGetMoreClickListener(OnGetMoreClickListener listener) {
        this.listener = listener;
    }

    public interface OnCloseClickListener {
        void onCloseClick();
    }

    private OnCloseClickListener closeListener;

    public void setOnCloseClickListener(OnCloseClickListener listener) {
        this.closeListener = listener;
    }

}
