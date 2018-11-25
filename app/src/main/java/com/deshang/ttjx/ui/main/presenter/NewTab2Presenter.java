package com.deshang.ttjx.ui.main.presenter;

import android.view.View;

import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.view.NewTab2View;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyNewBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import mvp.cn.util.LogUtil;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class NewTab2Presenter extends MvpRxSimplePresenter<NewTab2View> {

    public void getBubbleData() {
        Observable request = RetrofitUtils.getInstance().walletBubbleData();
        getNetWork(request, new RetrofitCallBack<BubbleBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取可领取红钻数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BubbleBean bean) {
                getView().getBubbleData(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void receiveBubble(final int id, final View view, final String str) {
        Observable request = RetrofitUtils.getInstance().receiveBubble(String.valueOf(id));
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("领取红钻数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().receiveBubble(bean, view, id, str);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void receiveAllBubble(String id) {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().receiveAllBubble(id);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("一键领取红钻数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().receiveAllBubble(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    public void getTaskData() {
        Observable request = RetrofitUtils.getInstance().getNewTaskData();
        getNetWork(request, new RetrofitCallBack<MakeMoneyNewBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtil.log("获取赚钱任务数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(MakeMoneyNewBean bean) {
                getView().getTaskData(bean);
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
