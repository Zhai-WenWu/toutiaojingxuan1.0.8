package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalMoneyBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalTimeBean;
import com.deshang.ttjx.ui.tab4.view.WithdrawalView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class WithdrawalPresenter extends MvpRxSimplePresenter<WithdrawalView> {

    /**
     * @param change  提现金额
     * @param payType 提现方式 1微信
     */
    public void withdrawal(int change, int payType) {
        getView().showProgressDialog();
        Observable<BaseBean> request = RetrofitUtils.getInstance().withdrawal(String.valueOf(change), payType);
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

    public void getWithdrawalData() {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getWithdrawalMoneyData();
        getNetWork(request, new RetrofitCallBack<WithdrawalMoneyBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取提现数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(WithdrawalMoneyBean bean) {
                getView().getWithdrawalData(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 提交用户授权信息
    public void commitAuthorization(String uuid, String open_id, String img, String name) {
//        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().commitAuthorization(uuid, open_id, img, name, SharedPrefHelper.getInstance().getToken());
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("提交用户授权信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().commitAuthorization(bean);
            }

            @Override
            public void onComplete() {
//                getView().dismissProgressDialog();
            }
        });
    }

    // 获取用户当日是否是第一次提现
    public void getUserWithdrawalTime() {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getUserWithdrawalTime();
        getNetWork(request, new RetrofitCallBack<WithdrawalTimeBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取用户当日是否是第一次提现异常：" + e.toString());
            }

            @Override
            public void onSuccess(WithdrawalTimeBean bean) {
                getView().getUserWithdrawalTime(bean);
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
