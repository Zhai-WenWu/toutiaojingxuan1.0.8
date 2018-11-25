package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/9/26.
 */

public interface VideoVoteLoadView extends MvpView {

    void getVideoList(MyVedioBean bean);

    void getVideoVote(VideoVoteBean bean);

}
