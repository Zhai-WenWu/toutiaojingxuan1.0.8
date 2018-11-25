package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.SaleOrWithdrawalData;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/9/28.
 */

public interface SaleTBView extends MvpView {

    void saleTB(BaseBean bean);

    void getTBData(SaleOrWithdrawalData bean);

    void withdrawalSuccess(BaseBean bean);
}
