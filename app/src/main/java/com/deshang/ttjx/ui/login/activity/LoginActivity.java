package com.deshang.ttjx.ui.login.activity;

import android.content.Intent;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.login.presenter.NewLoginPresenter;
import com.deshang.ttjx.ui.login.view.NewLoginView;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Observable;
import rx.Subscriber;


/**
 * 登录
 *
 * @author
 */
public class LoginActivity extends MvpSimpleActivity<NewLoginView, NewLoginPresenter> implements NewLoginView, IWXAPIEventHandler {


    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
    }

    @Override
    public void loginSucc(NewLoginBean bean) {

    }

    @OnClick({R.id.frame_login, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frame_login:
//                weChatLogin();
                break;

            case R.id.tv_agreement: // 用户协议
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("loadUrl", Constants.USER_AGREEMENT);
                startActivity(intent);
                break;
        }
    }

    // 微信登录
    private void weChatLogin() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (plat.isClientValid()) {
            // 判断是否存在客户端
            showProgressDialog();
        } else {
            ReceiveGoldToast.makeToast(this, "请安装微信客户端").show();
            return;
        }
        if (plat.isAuthValid()) {
            // 已授权  直接取信息登录
            // plat.removeAccount(true);
            dismissProgressDialog();
            PlatformDb db = plat.getDb();
            goToLogin(db.get("unionid"), db.get("openid"), db.get("icon"), db.get("nickname"));
        } else {
            plat.SSOSetting(false);
            plat.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    dismissProgressDialog();
                    PlatformDb db = platform.getDb();
                    goToLogin(db.get("unionid"), db.get("openid"), db.get("icon"), db.get("nickname"));
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    dismissProgressDialog();
                    LogUtils.d("onError" + throwable.toString());
                    ReceiveGoldToast.makeToast(LoginActivity.this, "登录失败，请重试").show();
                    // 清除授权缓存数据
                    platform.removeAccount(true);
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    dismissProgressDialog();
                    ReceiveGoldToast.makeToast(LoginActivity.this, "取消登录").show();
                    // 清除授权缓存数据
                    platform.removeAccount(true);
                }
            });
            plat.showUser(null);
        }
    }

    // 微信去登陆  弃用
    private void goToLogin(String uuid, String open_id, String img, String name) {
        Observable request = RetrofitUtils.getInstance().login(uuid, open_id, 1, img, name, "");
        Subscriber subscriber = new Subscriber<NewLoginBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("登录异常：" + e.toString());
            }

            @Override
            public void onNext(NewLoginBean bean) {
                if (bean.getErrcode() == 0) {
                    if (bean.getReturn() == null) {
                        showToast("数据异常，请重试");
                    }
                    SharedPrefHelper.getInstance().setToken(bean.getReturn().getToken());
                    SharedPrefHelper.getInstance().setUserId(String.valueOf(bean.getReturn().getUserid()));
                    SharedPrefHelper.getInstance().setPhoneNumber(bean.getReturn().getMobile());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast(bean.getErrinf());
                }
            }
        };
        request.subscribe(subscriber);
    }

    @Override
    public NewLoginPresenter createPresenter() {
        return new NewLoginPresenter();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        showProgressDialog();
        LogUtils.d("LoginActivity onReq：" + baseReq.getType());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        showProgressDialog();
        LogUtils.d("LoginActivity onResp：" + baseResp.getType());
    }
}
