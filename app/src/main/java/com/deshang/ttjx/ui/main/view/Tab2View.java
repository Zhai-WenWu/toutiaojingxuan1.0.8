package com.deshang.ttjx.ui.main.view;

import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface Tab2View extends MvpView{

    void getDataSucc(MakeMoneyBean bean);

    void statsUserClick();

}
