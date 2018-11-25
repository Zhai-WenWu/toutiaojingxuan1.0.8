package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;


/**
 * 二维码收徒Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class QRApprenticeDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;
    private TextView cancel, sure;
    private ImageView qrImage;
    private RelativeLayout rl_image;
    private int type;

    public QRApprenticeDialog(@NonNull Context context, int type) {
        super(context, R.style.Dialog);
        this.context = context;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_qr_apprentice, null);
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
        qrImage = (ImageView) view.findViewById(R.id.image_qr);
        rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);

        qrImage.setImageBitmap(EncodingUtils.Create2DCode(SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/" + type + "/share_type/4",
                ProjectUtils.dp2px(context, 120), ProjectUtils.dp2px(context, 120), BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)));

        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
        setCancelable(false);
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
                    listener.onSureClick(rl_image);
                }
                break;
        }
    }

    private OnSureClickListener listener;

    public interface OnSureClickListener {
        void onSureClick(RelativeLayout view);
    }

    public void setOnSureClickListener(OnSureClickListener listener) {
        this.listener = listener;
    }

}
