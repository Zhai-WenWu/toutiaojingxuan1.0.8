package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalAuditBean;
import com.deshang.ttjx.ui.tab4.view.WithdrawalAuditView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class WithdrawalAuditPresenter extends MvpRxSimplePresenter<WithdrawalAuditView> {

    public void getWithdrawalAuditData() {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getWithdrawalAuditData();
        getNetWork(request, new RetrofitCallBack<WithdrawalAuditBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取提现列表异常：" + e.toString());
            }

            @Override
            public void onSuccess(WithdrawalAuditBean bean) {
                getView().getWithdrawalData(bean);
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
