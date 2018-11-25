package com.deshang.ttjx.ui.main.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.IsSignBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;
import com.deshang.ttjx.ui.tab1.bean.SignBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface MainView extends MvpView {

    void canReceiveRedPacket(BaseBean bean);

    // 推荐文章
    void getRecommendSuccess(RecommendBean bean, int type);

    // 领取红包雨
    void receiveRedPacket(ReceiveRedPacketBean bean);

    // 提交当前登录状态
    void commitLoginState(BaseBean bean);

    // 每天第一次登录提交时间
    void commitLoginTime(BaseBean bean);

    // 获取分享链接
    void getShareUrl(ShareUrlBean bean);

    // 获取签到信息
    void getSignData(SignBean bean);

    // 获取是否签到
    void getIsSignData(IsSignBean bean);
}
