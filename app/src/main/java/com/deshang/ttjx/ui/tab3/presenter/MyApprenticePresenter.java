package com.deshang.ttjx.ui.tab3.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab2.bean.ChangeBean;
import com.deshang.ttjx.ui.tab2.view.ChangeView;
import com.deshang.ttjx.ui.tab3.bean.MyApprenticeBean;
import com.deshang.ttjx.ui.tab3.view.MyApprenticeView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class MyApprenticePresenter extends MvpRxSimplePresenter<MyApprenticeView> {

    public void getMyApprentice() {
        Observable request = RetrofitUtils.getInstance().getMyApprentice();
        getNetWork(request, new RetrofitCallBack<MyApprenticeBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取我的徒弟数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(MyApprenticeBean bean) {
                getView().getMyApprentice(bean);
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
