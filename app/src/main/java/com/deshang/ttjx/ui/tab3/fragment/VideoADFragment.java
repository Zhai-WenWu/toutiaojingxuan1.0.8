package com.deshang.ttjx.ui.tab3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.mywidget.LazyLoadingFragment;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

import java.util.HashMap;

import butterknife.BindView;

public class VideoADFragment extends LazyLoadingFragment {

    @BindView(R.id.ad_container)
    ViewGroup container;
    @BindView(R.id.ad_image_container)
    ViewGroup image_container;
    @BindView(R.id.time)
    TextView time;

    private int position;
    private NativeExpressADView view;
    private int type;

    public static VideoADFragment getInstance(NativeExpressADView view, int type, int position) {
        VideoADFragment fragment = new VideoADFragment();
        fragment.view = view;
        fragment.position = position;
        fragment.type = type;
        return fragment;
    }

    /*public void setAdView(NativeExpressADView view) {
        this.view = view;
    }

    public void setAdMap(HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        this.mAdViewPositionMap = mAdViewPositionMap;
    }*/

    @Override
    protected void loadData() {
        LogUtils.d("当前的位置：" + position+ "  标题:广告");
    }

    @Override
    protected void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_video_ad);
    }

    @Override
    protected void initView(View v) {
//        Bundle bundle = getArguments();
//        position = bundle.getInt("position", 0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d("进来广告:setUserVisibleHint()" + isVisibleToUser);
        if (isVisibleToUser) {
        } else {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("进来广告:onResume()");
        if (type == 1) {
            // 列表大图广告
            //            mAdViewPositionMap.put(view, 0); // 广告在列表中的位置是可以被更新的
            if (image_container.getChildCount() > 0 && image_container.getChildAt(0) == view) {
                return;
            }
            if (image_container.getChildCount() > 0) {
                image_container.removeAllViews();
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            image_container.addView(view);
            view.render(); // 调用render方法后sdk才会开始展示广告
        } else {
            // 开屏广告
            fetchSplashAD(getActivity(), container, time, Constants.GDT_APP_ID, Constants.GDT_KAI_PING_ID, null, 0);
        }
    }

    /**
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        SplashAD splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("进来广告:onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("进来广告:onDestroy()");
    }


}
