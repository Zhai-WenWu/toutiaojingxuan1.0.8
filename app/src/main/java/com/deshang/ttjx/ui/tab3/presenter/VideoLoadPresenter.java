package com.deshang.ttjx.ui.tab3.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab3.view.VideoLoadView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class VideoLoadPresenter extends MvpRxSimplePresenter<VideoLoadView> {

    public void getVideoData(int page) {
        Observable request = RetrofitUtils.getInstance().getVideoList(page);
        getNetWork(request, new RetrofitCallBack<VideoBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取视频列表异常：" + e.toString());
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

    // 查询余票
    public void videoVoteData() {
        Observable request = RetrofitUtils.getInstance().videoVoteData("1");
        getNetWork(request, new RetrofitCallBack<VideoVoteBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取视频余票异常：" + e.toString());
            }

            @Override
            public void onSuccess(VideoVoteBean bean) {
                getView().getVideoVote(bean);
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
