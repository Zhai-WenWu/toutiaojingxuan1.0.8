package com.deshang.ttjx.ui.tab1.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab1.view.NewsFragmentView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class NewsFragmentPresenter extends MvpRxSimplePresenter<NewsFragmentView> {

    public void getNewsData(String cat_id, int page) {
        Observable request = RetrofitUtils.getInstance().getNewsListData(cat_id, page);
        getNetWork(request, new RetrofitCallBack<NewsListBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取新闻列表数据异常：" + e.toString());
//                throw new NullPointerException();
            }

            @Override
            public void onSuccess(NewsListBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().getDataSucc(bean);
                }
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
