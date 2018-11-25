package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.tab1.adapter.RecommendNewsListAdapter;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 领取红包雨Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class ReceiveOnlineRedPacketDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private int goldNumber;

    public ReceiveOnlineRedPacketDialog(@NonNull Context context, int goldNumber) {
        super(context, R.style.Dialog);
        this.context = context;
        this.goldNumber = goldNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_receive_online_red_packet, null);
        setContentView(view);
        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        TextView goldNum = (TextView) view.findViewById(R.id.tv_gold);
        goldNum.setText(String.valueOf(goldNumber));
        ImageView red_packet = (ImageView) view.findViewById(R.id.red_packet);
        red_packet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.red_packet:
                dismiss();
                UIManager.turnToAct(context, ApprenticeActivity.class);
                break;

        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(29);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
