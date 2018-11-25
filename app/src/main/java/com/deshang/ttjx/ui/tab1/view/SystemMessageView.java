package com.deshang.ttjx.ui.tab1.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.MessageBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface SystemMessageView extends MvpView{

    void getDataSucc(MessageBean bean);

    void clearMessageSucc(BaseBean bean);

    void deleteMessageSucc(BaseBean bean);

    void readMessageSucc(BaseBean bean);

}
