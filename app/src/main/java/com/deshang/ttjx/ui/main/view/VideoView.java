package com.deshang.ttjx.ui.main.view;

import com.deshang.ttjx.ui.tab3.bean.VideoBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface VideoView extends MvpView{

    void getVideoList(VideoBean bean);

}
