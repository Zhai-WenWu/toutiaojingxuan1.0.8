package com.deshang.ttjx.ui.tab2.persenter;

import android.view.View;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.view.WalletView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class WalletPresenter extends MvpRxSimplePresenter<WalletView> {

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

    public void receiveBubble(final int id, final View view) {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().receiveBubble(String.valueOf(id));
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("领取红钻数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().dismissProgressDialog();
                getView().receiveBubble(bean, view, id);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void receiveAllBubble(String id) {
        Observable request = RetrofitUtils.getInstance().receiveAllBubble(id);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("一键领取红钻数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().receiveAllBubble(bean);
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
