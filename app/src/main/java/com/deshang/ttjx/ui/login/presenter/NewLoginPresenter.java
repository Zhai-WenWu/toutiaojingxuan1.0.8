package com.deshang.ttjx.ui.login.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.login.view.NewLoginView;
import com.deshang.ttjx.ui.main.bean.BaseBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class NewLoginPresenter extends MvpRxSimplePresenter<NewLoginView> {

    public void login(String uuid, String open_id, String img, String name) {
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }
}
