package com.deshang.ttjx.ui.tab3.view;

import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/9/26.
 */

public interface VideoLoadView extends MvpView {

    void getVideoList(VideoBean bean);

    void getVideoVote(VideoVoteBean bean);

}
