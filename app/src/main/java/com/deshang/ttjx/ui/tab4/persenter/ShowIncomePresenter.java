package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.view.ShowIncomeView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class ShowIncomePresenter extends MvpRxSimplePresenter<ShowIncomeView> {

    public void getShowIncome(final int type) {
        Observable<AccountInfoBean> request = RetrofitUtils.getInstance().getShowIncome();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取晒一晒异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().getShowIncome(bean, type);
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
