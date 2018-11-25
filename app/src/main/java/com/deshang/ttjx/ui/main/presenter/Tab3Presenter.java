package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.view.Tab3View;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;
import com.deshang.ttjx.ui.tab3.bean.Tab3Bean;
import com.deshang.ttjx.ui.tab4.bean.ApprenticeScrollMessageBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import mvp.cn.util.LogUtil;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class Tab3Presenter extends MvpRxSimplePresenter<Tab3View> {

    public void getApprenticeChange() {
        Observable request = RetrofitUtils.getInstance().getApprenticeChange();
        getNetWork(request, new RetrofitCallBack<Tab3Bean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtil.log("获取收徒盈利数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(Tab3Bean bean) {
                getView().getApprenticeChange(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void apprenticeClickNum(int share_type, int type) {
        Observable request = RetrofitUtils.getInstance().apprenticeClickNum(share_type, type);
        getNetWork(request, new RetrofitCallBack<ApprenticeScrollMessageBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("统计用户点击分享次数异常：" + e.toString());
            }

            @Override
            public void onSuccess(ApprenticeScrollMessageBean bean) {
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
