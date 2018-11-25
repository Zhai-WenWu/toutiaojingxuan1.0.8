package com.deshang.ttjx.ui.main.view;

import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.NewsTypeBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;

import mvp.cn.common.MvpView;

/**
 * Created by hh on 2017/5/12.
 */

public interface Tab1View extends MvpView{

    void getNewsTypeSucc(NewsTypeBean bean);

    // 提交当前登录状态
    void commitLoginState(BaseBean bean);

    // 获取分享链接
    void getShareUrl(ShareUrlBean bean);

}
