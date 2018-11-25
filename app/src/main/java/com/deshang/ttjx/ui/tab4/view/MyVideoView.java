package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;

import mvp.cn.common.MvpView;

/**
 * Created by 13364 on 2018/10/27.
 */

public interface MyVideoView extends MvpView {
    void getVideoDataSuccess(MyVedioBean bean);
}
