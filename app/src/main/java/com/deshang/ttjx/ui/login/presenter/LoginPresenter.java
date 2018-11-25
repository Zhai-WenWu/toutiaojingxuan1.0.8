package com.deshang.ttjx.ui.login.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.login.view.BindPhoneView;
import com.deshang.ttjx.ui.login.view.LoginView;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.GeeTestBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2018/5/12.
 */

public class LoginPresenter extends MvpRxSimplePresenter<LoginView> {

    public void login(String phone, String code, int type, String id, String time, String device) {
        getView().showProgressDialog();
        Observable login = RetrofitUtils.getInstance().login(phone, code, type, id, time, device);
        getNetWork(login, new RetrofitCallBack<NewLoginBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("登录异常：" + e.toString());
            }

            @Override
            public void onSuccess(NewLoginBean bean) {
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

    public void commitSimulatorPhone(String phoneNumber) {
        getView().showProgressDialog();
        Observable getCode = RetrofitUtils.getInstance().commitSimulatorPhone(phoneNumber);
        getNetWork(getCode, new RetrofitCallBack<BaseBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();

                LogUtils.d("模拟器提交手机号：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean baseResponse) {
                getView().commitSimulatorPhone(baseResponse);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 提交用户授权信息
    public void commitAuthorization(String uuid, String open_id, String img, String name) {
//        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().commitAuthorization(uuid, open_id, img, name, SharedPrefHelper.getInstance().getToken());
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("提交用户授权信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().commitAuthorization(bean);
            }

            @Override
            public void onComplete() {
//                getView().dismissProgressDialog();
            }
        });
    }

    public void commitSimulator(String virtual) {
        getView().showProgressDialog();
        Observable getCode = RetrofitUtils.getInstance().commitSimulator(virtual);
        getNetWork(getCode, new RetrofitCallBack<BaseBean>() {
            @Override
            public void onPostFail(Throwable e) {
                getView().dismissProgressDialog();
                LogUtils.d("提交是否是模拟器：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean baseResponse) {
                getView().commitSimulator(baseResponse);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    public void commitGeeTest(String phone, String challenge) {
        Observable getCode = RetrofitUtils.getWalletInstance().geetestCommit(phone, challenge);
        getNetWork(getCode, new RetrofitCallBack<GeeTestBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("提交极验：" + e.toString());
            }

            @Override
            public void onSuccess(GeeTestBean baseResponse) {
                getView().commitGeeTest(baseResponse);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void commitGeeTestLevel(String phone, String risk_code) {
        Observable getCode = RetrofitUtils.getWalletInstance().commitGeeTest(phone, risk_code);
        getNetWork(getCode, new RetrofitCallBack<GeeTestBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("提交极验：" + e.toString());
            }

            @Override
            public void onSuccess(GeeTestBean baseResponse) {
                getView().commitGeeTest(baseResponse);
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
