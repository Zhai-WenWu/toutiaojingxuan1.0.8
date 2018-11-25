package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/13.
 */

public interface UserInfoView extends MvpView {

    void getUserInfoSuccess(BaseResponse bean);

}
