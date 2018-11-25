package com.deshang.ttjx.ui.tab1.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.MessageBean;
import com.deshang.ttjx.ui.tab1.view.SystemMessageView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class SystemMessagePresenter extends MvpRxSimplePresenter<SystemMessageView> {

    public void getSystemMessageData(int page) {
        Observable request = RetrofitUtils.getInstance().systemMessageData(page);
        getNetWork(request, new RetrofitCallBack<MessageBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取系统消息数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(MessageBean bean) {
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

    public void clearMessage() {
        Observable request = RetrofitUtils.getInstance().clearMessage();
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("清空系统消息数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().clearMessageSucc(bean);
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 删除单条消息
    public void deleteMessage(int id) {
        Observable request = RetrofitUtils.getInstance().deleteMessage(id);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("清空系统消息数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().deleteMessageSucc(bean);
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 阅读单条消息
    public void readMessage(int id) {
        Observable request = RetrofitUtils.getInstance().readMessage(id);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("阅读系统消息数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                getView().readMessageSucc(bean);
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
