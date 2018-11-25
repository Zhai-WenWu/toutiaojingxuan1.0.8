package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;
import com.deshang.ttjx.ui.tab4.view.AccountInfoView;
import com.deshang.ttjx.ui.tab4.view.QuestionView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class AccountInfoPresenter extends MvpRxSimplePresenter<AccountInfoView> {

    public void getAccountData(int page) {
        Observable<AccountInfoBean> request = RetrofitUtils.getInstance().accountData(page);
        getNetWork(request, new RetrofitCallBack<AccountInfoBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取账户信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(AccountInfoBean bean) {
                getView().getAccountDataSuccess(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

}
