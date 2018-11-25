package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ScreenUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab4.activity.MyServiceActivity;

public class ShareDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        initView();
    }

    private int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    private ImageView imageView;
    private LinearLayout ll_qr, ll_bottom, ll_copy, ll_message, ll_qq;

    public void setVisible(int type) {
        if (SharedPrefHelper.getInstance().getOnLine() && type == 1) {
            imageView.setVisibility(View.VISIBLE);
        }
        /*if (type == 0) {
            // 当日首次分享
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.share_title_one));
        }*/
        ll_bottom.setVisibility(View.GONE);
        ll_message.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_share, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.cancel);
        LinearLayout circle = (LinearLayout) view.findViewById(R.id.ll_circle);
        LinearLayout weChat = (LinearLayout) view.findViewById(R.id.ll_we_chat);
        imageView = (ImageView) view.findViewById(R.id.img);
        ll_qr = (LinearLayout) view.findViewById(R.id.ll_qr);
        ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        ll_copy = (LinearLayout) view.findViewById(R.id.ll_copy);
        ll_message = (LinearLayout) view.findViewById(R.id.ll_message);
        ll_qq = (LinearLayout) view.findViewById(R.id.ll_qq);

        ll_qq.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_qr.setOnClickListener(this);
        ll_copy.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        circle.setOnClickListener(this);
        weChat.setOnClickListener(this);

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
            case R.id.ll_circle:
                if (listener != null) {
                    listener.onClickListener(1);
                }
                dismiss();
                break;
            case R.id.ll_we_chat:
                if (listener != null) {
                    listener.onClickListener(2);
                }
                dismiss();
                break;

            case R.id.ll_message:
                // 短信收徒
                if (listener != null) {
                    listener.onClickListener(3);
                }
                dismiss();
                break;

            case R.id.ll_qr:
                // 二维码收徒
                if (listener != null) {
                    listener.onClickListener(4);
                }
                dismiss();
                break;

            case R.id.ll_copy:
                // 复制链接
                if (listener != null) {
                    listener.onClickListener(5);
                }
                dismiss();
                break;

            case R.id.ll_qq:
                if (listener != null) {
                    listener.onClickListener(6);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(26);
    }

    private ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onClickListener(int type);
    }

}
