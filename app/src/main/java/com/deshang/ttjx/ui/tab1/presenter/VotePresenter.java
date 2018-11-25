package com.deshang.ttjx.ui.tab1.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab1.bean.VoteBean;
import com.deshang.ttjx.ui.tab1.view.VoteView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class VotePresenter extends MvpRxSimplePresenter<VoteView> {

    public void getVoteData(String id) {
        Observable request = RetrofitUtils.getInstance().getVoteData(id);
        getNetWork(request, new RetrofitCallBack<VoteBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("新闻投票数据异常：" + e.toString());
            }
            @Override
            public void onSuccess(VoteBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().getVoteData(bean);
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
