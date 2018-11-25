package com.deshang.ttjx.ui.tab2.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab1.view.NewsFragmentView;
import com.deshang.ttjx.ui.tab2.bean.ChangeBean;
import com.deshang.ttjx.ui.tab2.view.ChangeView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class ChangePresenter extends MvpRxSimplePresenter<ChangeView> {

    public void getChangeData(int page) {
        Observable request = RetrofitUtils.getInstance().getChangeListData(page);
        getNetWork(request, new RetrofitCallBack<ChangeBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取零钱数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(ChangeBean bean) {
                getView().getChangeData(bean);
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
