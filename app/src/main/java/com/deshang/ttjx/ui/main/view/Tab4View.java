package com.deshang.ttjx.ui.main.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.MeBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface Tab4View extends MvpView{

    void getMeInfoSucc(MeBean bean);
    void getMeBonusSucc(BaseBean bean);

}
