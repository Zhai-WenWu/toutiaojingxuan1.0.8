package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;
import com.deshang.ttjx.ui.tab4.view.QuestionView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class QuestionPresenter extends MvpRxSimplePresenter<QuestionView> {

    public void getQuestionData() {
        getView().showProgressDialog();
        Observable<QuestionListBean> request = RetrofitUtils.getInstance().getQuestionData();
        getNetWork(request, new RetrofitCallBack<QuestionListBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取常见问题异常：" + e.toString());
            }

            @Override
            public void onSuccess(QuestionListBean bean) {
                getView().getQuestionSucc(bean);
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
