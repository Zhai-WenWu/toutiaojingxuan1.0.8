package com.deshang.ttjx.ui.tab3.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab3.bean.MyDiscipleBean;
import com.deshang.ttjx.ui.tab3.view.MyDiscipleView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class MyDisciplePresenter extends MvpRxSimplePresenter<MyDiscipleView> {

    public void getMyDisciple() {
        Observable request = RetrofitUtils.getInstance().getMyDisciple();
        getNetWork(request, new RetrofitCallBack<MyDiscipleBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取我的徒孙数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(MyDiscipleBean bean) {
                getView().getMyDisciple(bean);
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
