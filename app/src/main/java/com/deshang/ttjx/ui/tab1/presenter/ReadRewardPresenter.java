package com.deshang.ttjx.ui.tab1.presenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.bean.IsFirstTimeShareOnDayBean;
import com.deshang.ttjx.ui.tab1.bean.ReadRewardBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendNewsBean;
import com.deshang.ttjx.ui.tab1.bean.ShareMessageBean;
import com.deshang.ttjx.ui.tab1.bean.VoteBean;
import com.deshang.ttjx.ui.tab1.view.ReadRewardView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by L on 2017/5/12.
 */

public class ReadRewardPresenter extends MvpRxSimplePresenter<ReadRewardView> {

    public void getReadRewardData(String id, String time,String device) {
        Observable request = RetrofitUtils.getInstance().readReward(id, time,device);
        getNetWork(request, new RetrofitCallBack<ReadRewardBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("领取阅读奖励数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(ReadRewardBean bean) {
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

    // 激活红包雨领取资格
    public void activateRedPacket(String id, String time) {
        Observable request = RetrofitUtils.getInstance().activateRedPacket(id, time);
        getNetWork(request, new RetrofitCallBack<ReadRewardBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("激活红包雨领取资格异常：" + e.toString());
            }

            @Override
            public void onSuccess(ReadRewardBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().activateRedPacketSucc(bean);
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 获取分享数据
    public void getShareData(String id, final int type) {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getShareData(id, type);
        getNetWork(request, new RetrofitCallBack<ShareMessageBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取分享信息异常：" + e.toString());
            }

            @Override
            public void onSuccess(ShareMessageBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().getShareData(bean, type);
                }
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 新闻详情页下方推荐列表
    public void getRecommendNews(String cat_id, String id, int page) {
        Observable request = RetrofitUtils.getInstance().getRecommendNews(cat_id, id, page);
        getNetWork(request, new RetrofitCallBack<RecommendNewsBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取下方推荐列表异常：" + e.toString());
            }

            @Override
            public void onSuccess(RecommendNewsBean bean) {
                getView().getRecommendNews(bean);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    // 当日是否第一次分享
    public void getIsFirstTimeShareOnDay() {
        getView().showProgressDialog();
        Observable request = RetrofitUtils.getInstance().getIsFirstTimeShareOnDay();
        getNetWork(request, new RetrofitCallBack<IsFirstTimeShareOnDayBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("当日是否第一次分享异常：" + e.toString());
            }

            @Override
            public void onSuccess(IsFirstTimeShareOnDayBean bean) {
                getView().getIsFirstTimeShareOnDay(bean);
            }

            @Override
            public void onComplete() {
                getView().dismissProgressDialog();
            }
        });
    }

    // 统计用户点击内文立即分享次数
    public void shareClickNnm(int type) {
        Observable request = RetrofitUtils.getInstance().shareClickNnm(type);
        getNetWork(request, new RetrofitCallBack<IsFirstTimeShareOnDayBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("用户点击内文立即分享次数异常：" + e.toString());
            }

            @Override
            public void onSuccess(IsFirstTimeShareOnDayBean bean) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void getVoteData(String id) {
        Observable request = RetrofitUtils.getInstance().getVoteData(id);
        getNetWork(request, new RetrofitCallBack<VoteBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("新闻投票数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(VoteBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().getVoteData(bean);
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void voteData(String id) {
        Observable request = RetrofitUtils.getInstance().voteData(id);
        getNetWork(request, new RetrofitCallBack<BaseBean>() {

            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("新闻投票数据异常：" + e.toString());
            }

            @Override
            public void onSuccess(BaseBean bean) {
                if (getView() == null) {
                    LogUtils.d("getView is Null!");
                } else {
                    getView().voteData(bean);
                }
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
