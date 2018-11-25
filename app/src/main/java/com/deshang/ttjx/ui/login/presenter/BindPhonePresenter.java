package com.deshang.ttjx.ui.login.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.bean.LoginBean;
import com.deshang.ttjx.ui.login.view.BindPhoneView;
import com.deshang.ttjx.ui.main.bean.BaseBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class BindPhonePresenter extends MvpRxSimplePresenter<BindPhoneView> {

    public void bindPhone(String phone, String code) {
        getView().showProgressDialog();
        Observable login = RetrofitUtils.getInstance().bindPhone(phone, code);
        getNetWork(login, new RetrofitCallBack<BaseBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("登录异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().loginSucc(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    public void getCode(String phone) {
        getView().showProgressDialog();
        Observable getCode = RetrofitUtils.getInstance().getCode(phone);
        getNetWork(getCode, new RetrofitCallBack<CodeBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("登录获取验证码异常：" + e.toString());
            }

            @Override
            public void onSuccess(CodeBean baseResponse) {
                getView().sendMessageSucc(baseResponse);
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
