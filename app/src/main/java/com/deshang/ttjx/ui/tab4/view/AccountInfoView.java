package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/20.
 */

public interface AccountInfoView extends MvpView {

    void getAccountDataSuccess(AccountInfoBean bean);

}
