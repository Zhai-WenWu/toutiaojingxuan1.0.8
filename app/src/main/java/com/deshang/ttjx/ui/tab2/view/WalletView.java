package com.deshang.ttjx.ui.tab2.view;

import android.view.View;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/7/5.
 */

public interface WalletView extends MvpView {

    void getBubbleData(BubbleBean bean);

    void receiveBubble(BaseBean bean, View view, int index);

    void receiveAllBubble(BaseBean bean);

}
