package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalMoneyBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalTimeBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/13.
 */

public interface WithdrawalView extends MvpView {

    void getWithdrawalData(WithdrawalMoneyBean bean);

    void withdrawalSuccess(BaseBean bean);

    void commitAuthorization(BaseBean bean);

    void getUserWithdrawalTime(WithdrawalTimeBean bean);

}
