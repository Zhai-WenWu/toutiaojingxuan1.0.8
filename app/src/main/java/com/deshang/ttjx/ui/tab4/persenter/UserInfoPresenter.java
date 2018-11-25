package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;
import com.deshang.ttjx.ui.tab4.view.QuestionView;
import com.deshang.ttjx.ui.tab4.view.UserInfoView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class UserInfoPresenter extends MvpRxSimplePresenter<UserInfoView> {

    public void getUserInfo() {
        getView().showProgressDialog();
        Observable<BaseResponse> request = RetrofitUtils.getInstance().getQuestionData();
        getNetWork(request, new RetrofitCallBack<BaseResponse>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取用户信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseResponse bean) {
                getView().getUserInfoSuccess(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

}
