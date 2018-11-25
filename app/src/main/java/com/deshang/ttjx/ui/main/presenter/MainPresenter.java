package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.view.MainView;
import com.deshang.ttjx.ui.tab1.bean.IsSignBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;
import com.deshang.ttjx.ui.tab1.bean.SignBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class MainPresenter extends MvpRxSimplePresenter<MainView> {

    public void getData() {
    }

    // 是否可以领取红包雨
    public void canReceiveRedPacket() {
        Observable<BaseBean> request = RetrofitUtils.getInstance().canReceiveRedPacket();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("是否可以领取红包雨异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().canReceiveRedPacket(bean);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 获取推荐文章
    public void getRecommendData(final int type) {
        Observable<RecommendBean> request = RetrofitUtils.getInstance().getRecommendData();
        getNetWork(request, new RetrofitCallBack<RecommendBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取推荐文章异常：" + e.toString());
            }

            @Override
            public void onSuccess(RecommendBean bean) {
                getView().getRecommendSuccess(bean, type);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 领取红包雨
    public void receiveRedPacket() {
        getView().showProgressDialog();
        Observable<ReceiveRedPacketBean> request = RetrofitUtils.getInstance().receiveRedPacket();
        getNetWork(request, new RetrofitCallBack<ReceiveRedPacketBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("领取红包雨异常：" + e.toString());
            }

            @Override
            public void onSuccess(ReceiveRedPacketBean bean) {
                getView().receiveRedPacket(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 提交登录状态
    public void commitLoginState() {
        Observable<BaseBean> request = RetrofitUtils.getInstance().commitLoginState();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().commitLoginState(null);
                LogUtils.d("提交登录状态异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().commitLoginState(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 每天第一次登录提交时间
    public void commitLoginTime() {
        Observable<BaseBean> request = RetrofitUtils.getInstance().commitLoginTime();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("第一次登录提交时间异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().commitLoginTime(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 获取分享链接
    public void getShareUrl() {
        getView().showProgressDialog();
        Observable<ShareUrlBean> request = RetrofitUtils.getInstance().getShareUrl();
        getNetWork(request, new RetrofitCallBack<ShareUrlBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取要分享的新闻链接异常：" + e.toString());
            }

            @Override
            public void onSuccess(ShareUrlBean bean) {
                getView().getShareUrl(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 签到
    public void getSignData() {
        Observable<SignBean> request = RetrofitUtils.getInstance().getSignData();
        getNetWork(request, new RetrofitCallBack<SignBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("签到异常：" + e.toString());
            }

            @Override
            public void onSuccess(SignBean bean) {
                getView().getSignData(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 查询签到状态
    public void getIsSignData() {
        Observable<IsSignBean> request = RetrofitUtils.getInstance().getIsSignData();
        getNetWork(request, new RetrofitCallBack<IsSignBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("查询签到状态异常：" + e.toString());
            }

            @Override
            public void onSuccess(IsSignBean bean) {
                getView().getIsSignData(bean);
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
