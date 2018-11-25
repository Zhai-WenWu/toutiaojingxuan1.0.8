package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab4.bean.ApprenticeScrollMessageBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/7/6.
 */

public interface ApprenticeView extends MvpView {

    void getApprenticeScrollData(ApprenticeScrollMessageBean bean);

}
