package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/20.
 */

public interface ShowIncomeView extends MvpView {
    void getShowIncome(BaseBean bean, int type);
}