package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.SettingBean;
import com.deshang.ttjx.ui.tab4.view.SettingView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class SettingPresenter extends MvpRxSimplePresenter<SettingView> {

    public void getSettingData() {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getSettingData();
        getNetWork(request, new RetrofitCallBack<SettingBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取设置数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(SettingBean bean) {
                getView().getSetSuccess(bean);
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
