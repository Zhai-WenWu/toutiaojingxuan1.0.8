package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.view.Tab4View;
import com.deshang.ttjx.ui.tab4.bean.MeBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class Tab4Presenter extends MvpRxSimplePresenter<Tab4View> {

    public void getMeInfo() {
        Observable<MeBean> request = RetrofitUtils.getInstance().getMeInfo();
        getNetWork(request, new RetrofitCallBack<MeBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("我的Tab异常：" + e.toString());
            }

            @Override
            public void onSuccess(MeBean bean) {
                getView().getMeInfoSucc(bean);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getMeBonus() {
        getView().showProgressDialog();
        Observable<MeBean> request = RetrofitUtils.getInstance().getMeBouns();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("我的Tab异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().dismissProgressDialog();
                getView().getMeBonusSucc(bean);
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
