package com.deshang.ttjx.ui.main.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.ui.main.view.VideoView;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;

import mvp.cn.rx.MvpRxSimplePresenter;
import mvp.cn.util.LogUtil;
import rx.Observable;

/**
 * Created by hh on 2017/5/12.
 */

public class VideoPresenter extends MvpRxSimplePresenter<VideoView> {

    public void getVideoList(int page) {
        Observable request = RetrofitUtils.getInstance().getVideoList(page);
        getNetWork(request, new RetrofitCallBack<VideoBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtil.log("获取视频列表异常：" + e.toString());
            }

            @Override
            public void onSuccess(VideoBean bean) {
                getView().getVideoList(bean);
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
