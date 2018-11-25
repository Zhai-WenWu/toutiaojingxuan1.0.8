package com.deshang.ttjx.framework.network.callback;


import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.base.BaseResponse;

import mvp.cn.util.LogUtil;
import mvp.cn.util.ToastUtil;
import rx.Subscriber;

/**
 * Created by hh on 2017/8/28.
 */

public abstract class RetrofitCallBack<T extends BaseResponse> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        LogUtil.log("onCompleted");
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.logError("error:" + e.getMessage());
        onComplete();
        onPostFail(e);
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            onPostFail(new Throwable("解析出问题,可能您需要检查bean"));
            return;
        }
        /*if (t.errcode == 2) {
            onSuccess(t);
        } else {
            onCodeError(t);
        }*/
        onSuccess(t);
        onComplete();
//            onCodeError(t);
    }

    public abstract void onPostFail(Throwable e);

    public void onCodeError(T t) {
        ToastUtil.showToast(SoftApplication.softApplication, "请求失败");
    }

    public abstract void onSuccess(T t);

    public abstract void onComplete();
}
