package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab4.bean.BallotBean;

import mvp.cn.common.MvpView;

/**
 * Created by 13364 on 2018/9/27.
 */

public interface BallotView extends MvpView {
    void getBallotDataSuccess(BallotBean bean);

}
