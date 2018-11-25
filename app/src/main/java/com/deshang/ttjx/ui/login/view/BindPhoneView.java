package com.deshang.ttjx.ui.login.view;

import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.main.bean.BaseBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/9.
 */

public interface BindPhoneView extends MvpView {

    void sendMessageSucc(CodeBean bean);

    void loginSucc(BaseBean bean);

}
