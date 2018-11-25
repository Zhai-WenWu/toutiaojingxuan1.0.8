package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.ui.main.view.Tab2View;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import mvp.cn.util.LogUtil;
import rx.Observable;

/**
 *
 * Created by L on 2017/5/12.
 */

public class Tab2Presenter extends MvpRxSimplePresenter<Tab2View> {

    public void getData() {
        Observable request = RetrofitUtils.getInstance().getTaskData();
        getNetWork(request, new RetrofitCallBack<MakeMoneyBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtil.log("获取赚钱任务数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(MakeMoneyBean bean) {
                getView().getDataSucc(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void statsUserClick() {
        Observable request = RetrofitUtils.getInstance().statsUserClick();
        getNetWork(request, new RetrofitCallBack<BaseResponse>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtil.log("提交用户异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseResponse bean) {
                getView().statsUserClick();
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
