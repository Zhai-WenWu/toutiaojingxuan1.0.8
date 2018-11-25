package com.deshang.ttjx.ui.tab1.view;

import com.deshang.ttjx.ui.tab1.bean.NewsListBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface NewsFragmentView extends MvpView{

    void getDataSucc(NewsListBean bean);

}
