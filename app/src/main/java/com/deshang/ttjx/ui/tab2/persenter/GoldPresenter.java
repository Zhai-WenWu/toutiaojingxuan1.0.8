package com.deshang.ttjx.ui.tab2.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab2.bean.ChangeBean;
import com.deshang.ttjx.ui.tab2.bean.GoldBean;
import com.deshang.ttjx.ui.tab2.view.ChangeView;
import com.deshang.ttjx.ui.tab2.view.GoldView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class GoldPresenter extends MvpRxSimplePresenter<GoldView> {

    public void getGoldData(int page) {
        Observable request = RetrofitUtils.getInstance().getGoldListData(page);
        getNetWork(request, new RetrofitCallBack<GoldBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取金币数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(GoldBean bean) {
                getView().getGoldData(bean);
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
