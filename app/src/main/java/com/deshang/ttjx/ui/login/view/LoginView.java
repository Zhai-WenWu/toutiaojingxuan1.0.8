package com.deshang.ttjx.ui.login.view;

import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.GeeTestBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/9.
 */

public interface LoginView extends MvpView {

    void sendMessageSucc(CodeBean bean);

    void loginSucc(NewLoginBean bean);

    void commitSimulator(BaseBean bean);

    void commitSimulatorPhone(BaseBean bean);

    void commitAuthorization(BaseBean bean);

    void commitGeeTest(GeeTestBean bean);

    void commitGeeTestLevel(BaseBean bean);
}
