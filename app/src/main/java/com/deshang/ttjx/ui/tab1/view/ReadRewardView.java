package com.deshang.ttjx.ui.tab1.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.IsFirstTimeShareOnDayBean;
import com.deshang.ttjx.ui.tab1.bean.ReadRewardBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendNewsBean;
import com.deshang.ttjx.ui.tab1.bean.ShareMessageBean;
import com.deshang.ttjx.ui.tab1.bean.VoteBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface ReadRewardView extends MvpView {

    void getDataSucc(ReadRewardBean bean);

    void activateRedPacketSucc(ReadRewardBean bean);

    void getShareData(ShareMessageBean bean, int type);

    void getRecommendNews(RecommendNewsBean bean);

    // 当日是否第一次分享
    void getIsFirstTimeShareOnDay(IsFirstTimeShareOnDayBean bean);

    void getVoteData(VoteBean bean);

    void voteData(BaseBean bean);
}
