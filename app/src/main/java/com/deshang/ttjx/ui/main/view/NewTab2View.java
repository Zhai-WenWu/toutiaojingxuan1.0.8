package com.deshang.ttjx.ui.main.view;

import android.view.View;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyNewBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface NewTab2View extends MvpView {

    void getBubbleData(BubbleBean bean);

    void receiveBubble(BaseBean bean, View view, int index, String str);

    void receiveAllBubble(BaseBean bean);

    void getTaskData(MakeMoneyNewBean bean);

    void statsUserClick();

}
