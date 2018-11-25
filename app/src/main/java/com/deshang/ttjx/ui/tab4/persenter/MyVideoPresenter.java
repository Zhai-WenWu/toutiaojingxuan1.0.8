package com.deshang.ttjx.ui.tab4.persenter;

import com.deshang.ttjx.framework.network.callback.RetrofitCallBack;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;
import com.deshang.ttjx.ui.tab4.view.MyVideoView;

import mvp.cn.rx.MvpRxSimplePresenter;
import rx.Observable;

/**
 * Created by 13364 on 2018/9/27.
 */

public class MyVideoPresenter extends MvpRxSimplePresenter<MyVideoView> {

    public void getVideoData(int page) {
        Observable request = RetrofitUtils.getInstance().myVideoItem(page);
        getNetWork(request, new RetrofitCallBack<MyVedioBean>() {
            @Override
            public void onPostFail(Throwable e) {
                LogUtils.d("获取投票视频：" + e.toString());
            }
            @Override
            public void onSuccess(MyVedioBean vedioBean) {
               getView().getVideoDataSuccess(vedioBean);
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
