package com.deshang.ttjx.ui.tab4.view;

import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;

import mvp.cn.common.MvpView;

/**
 * Created by L on 2018/6/13.
 */

public interface QuestionView extends MvpView {

    void getQuestionSucc(QuestionListBean bean);

}
