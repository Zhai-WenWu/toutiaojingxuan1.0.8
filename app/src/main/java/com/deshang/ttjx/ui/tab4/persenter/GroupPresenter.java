package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;
import com.deshang.ttjx.ui.tab4.bean.GroupBean;
import com.deshang.ttjx.ui.tab4.view.BallotView;
import com.deshang.ttjx.ui.tab4.view.GroupView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by 13364 on 2018/9/27.
 */

public class GroupPresenter extends MvpRxSimplePresenter<GroupView> {

    public void getGroup() {
        Observable request = RetrofitUtils.getInstance().getGroup();
        getNetWork(request, new RetrofitCallBack<GroupBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("数据异常：" + e.toString());
            }
            @Override
            public void onSuccess(GroupBean groupBean) {
                getView().getGroup(groupBean);
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
