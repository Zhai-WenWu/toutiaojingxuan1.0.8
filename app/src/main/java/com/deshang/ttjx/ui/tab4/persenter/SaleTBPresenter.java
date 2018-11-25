package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.SaleOrWithdrawalData;
import com.deshang.ttjx.ui.tab4.view.SaleTBView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/9/28.
 */

public class SaleTBPresenter extends MvpRxSimplePresenter<SaleTBView> {

    public void saleTB(String gold) {
        getView().showProgressDialog();
        Observable<AccountInfoBean> request = RetrofitUtils.getInstance().saleTB(gold);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("出售红钻异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().dismissProgressDialog();
                getView().saleTB(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void getTBData() {
        getView().showProgressDialog();
        Observable<SaleOrWithdrawalData> request = RetrofitUtils.getInstance().getTBData();
        getNetWork(request, new RetrofitCallBack<SaleOrWithdrawalData>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("获取可以出售的红钻异常：" + e.toString());
            }

            @Override
            public void onSuccess(SaleOrWithdrawalData bean) {
                getView().dismissProgressDialog();
                getView().getTBData(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * @param change  提现金额
     * @param payType 提现方式 1微信
     */
    public void withdrawal(String change, int payType) {
        getView().showProgressDialog();
        Observable<BaseBean> request = RetrofitUtils.getInstance().withdrawal(change, payType);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("提现异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().withdrawalSuccess(bean);
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
