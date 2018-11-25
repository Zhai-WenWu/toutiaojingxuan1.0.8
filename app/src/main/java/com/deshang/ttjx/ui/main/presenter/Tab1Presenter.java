package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.view.Tab1View;
import com.deshang.ttjx.ui.tab1.bean.NewsTypeBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class Tab1Presenter extends MvpRxSimplePresenter<Tab1View> {

    // 获取新闻分类
    public void getTypeData() {
        Observable<NewsTypeBean> request = RetrofitUtils.getInstance().getNewsType();
        getNetWork(request, new RetrofitCallBack<NewsTypeBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取首页新闻分类异常：" + e.toString());
                throw new NullPointerException();
            }

            @Override
            public void onSuccess(NewsTypeBean bean) {
                getView().getNewsTypeSucc(bean);
            }

            @Override
            public void onComplete() {

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

    // 获取分享链接
    public void getShareUrl() {
        getView().showProgressDialog();
        Observable<ShareUrlBean> request = RetrofitUtils.getInstance().getShareUrl();
        getNetWork(request, new RetrofitCallBack<ShareUrlBean>() {

            @Override
            public void onPostFail(Throwable e) {
                getView().commitLoginState(null);
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

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }
}
