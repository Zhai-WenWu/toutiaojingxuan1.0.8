package com.deshang.ttjx.ui.login.view;

import com.deshang.ttjx.ui.login.bean.NewLoginBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface NewLoginView extends MvpView {

    void loginSucc(NewLoginBean loginBean);

}
