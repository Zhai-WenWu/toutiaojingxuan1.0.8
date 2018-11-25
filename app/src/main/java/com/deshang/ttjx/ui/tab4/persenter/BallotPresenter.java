package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;
import com.deshang.ttjx.ui.tab4.view.BallotView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by 13364 on 2018/9/27.
 */

public class BallotPresenter extends MvpRxSimplePresenter<BallotView> {

    public void getBallotData(int page) {
        Observable request = RetrofitUtils.getInstance().myBallot(page);
        getNetWork(request, new RetrofitCallBack<BallotBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取账户信息异常：" + e.toString());
            }
            @Override
            public void onSuccess(BallotBean ballotBean) {
                getView().getBallotDataSuccess(ballotBean);
            }
            @Override
            public void onComplete() {
                LogUtils.d("获取信息完成");
            }
        });
    }
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }
}
