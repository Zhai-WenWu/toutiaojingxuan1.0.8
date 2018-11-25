package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.ApprenticeScrollMessageBean;
import com.deshang.ttjx.ui.tab4.view.AccountInfoView;
import com.deshang.ttjx.ui.tab4.view.ApprenticeView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/6/13.
 */

public class ApprenticePresenter extends MvpRxSimplePresenter<ApprenticeView> {

    public void getScrollText() {
        Observable request = RetrofitUtils.getInstance().getApprenticeMessage();
        getNetWork(request, new RetrofitCallBack<ApprenticeScrollMessageBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取收徒页面滚动信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(ApprenticeScrollMessageBean bean) {
                getView().getApprenticeScrollData(bean);
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
