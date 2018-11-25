package com.deshang.ttjx.ui.tab1.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.adconfig.TTAdManagerHolder;
import com.deshang.ttjx.ui.tab1.adapter.NewsAdapter;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab1.presenter.NewsFragmentPresenter;
import com.deshang.ttjx.ui.tab1.view.NewsFragmentView;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * 首页新闻 Fragment
 * Created by L on 2018/6/1.
 */

public class NewsFragment extends MvpSimpleFragment<NewsFragmentView, NewsFragmentPresenter> implements NewsFragmentView, NativeExpressAD.NativeExpressADListener {

    private int FIRST_AD = 4; // 第一条广告初始的位置
    private boolean isOpenAD = true; // 广告开关

    private int AD_COUNT = 10;    // 加载广告的条数，取值范围为[1, 10]
    private int FIRST_AD_POSITION = 4; // 第一条广告的position
//    private int ITEMS_PER_AD = 5;     // 每间隔4个新闻插入一条广告  本身加上自己插入的一天广告所以是5

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.load)
    TextView load;

    private String typeID; // 新闻ID
    private String nameStr; // 新闻名称
    private NewsAdapter adapter;
    private List<Object> data;
    private int page = 1;

    private int isCanLoadAD = 0;

    private int height;
    private int isFirst = 0;

    private TTAdNative mTTAdNative; // 穿山甲AD
    private List<TTFeedAd> mTTADList;

    private NativeExpressAD mADManager;
    private List<NativeExpressADView> mAdViewList;
    private List<NativeExpressADView> mAdBigImageList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    {
        data = new ArrayList<>();
        mAdViewPositionMap = new HashMap<>();
        mAdViewList = new ArrayList<>();
        mAdBigImageList = new ArrayList<>();
        mTTADList = new ArrayList<>();
    }

    public static NewsFragment getInstance(String typeID, String nameStr, int firstPosition, int interval, boolean isOpenAd) {
        NewsFragment fragment = new NewsFragment();
        Bundle b = new Bundle();
        b.putString("typeID", typeID);
        b.putString("nameStr", nameStr);
        b.putInt("firstPosition", firstPosition);
        b.putInt("interval", interval);
        b.putBoolean("isOpenAd", isOpenAd);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_news);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            typeID = savedInstanceState.getString("typeID");
            nameStr = savedInstanceState.getString("nameStr");
            FIRST_AD = savedInstanceState.getInt("firstPosition") - 1;
            if (FIRST_AD < 0) {
                FIRST_AD = 0;
            }
            isOpenAD = savedInstanceState.getBoolean("isOpenAd");
        } else {
            typeID = getArguments().getString("typeID");
            nameStr = getArguments().getString("nameStr");
            FIRST_AD = getArguments().getInt("firstPosition") - 1;
            if (FIRST_AD < 0) {
                FIRST_AD = 0;
            }
            isOpenAD = getArguments().getBoolean("isOpenAd");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("typeID", typeID);
        outState.putString("nameStr", nameStr);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initView(View v) {
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance();
        mTTAdNative = ttAdManager.createAdNative(getActivity());
        adapter = new NewsAdapter(getActivity(), data, mAdViewPositionMap);
        adapter.setOnClickListener(new NewsAdapter.OnClickListener() {
            @Override
            public void onClick(int id, int cat_id) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("Url", String.valueOf(id));
                intent.putExtra("TypeID", String.valueOf(cat_id));
                startActivity(intent);

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNewsData();
            }

            @Override
            public void onLoadMore() {
                getNewsData();
            }
        });
    }

    private boolean isUIVisible = false;
    private boolean isViewCreate = false;

    @Override
    public NewsFragmentPresenter createPresenter() {
        return new NewsFragmentPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
        getData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUIVisible = isVisibleToUser;
        getData();
    }

    private void getData() {
//        LogUtils.d("isUIVisible " + isUIVisible + " isViewCreate " + isViewCreate + " id:" + typeID + " name:" + nameStr);
        if (isUIVisible && isViewCreate) {
            if (data == null || data.size() == 0) {
                page = 1;
                getNewsData();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            animateClose(tvMessage);
        }
    };

    private void animateClose(final View view) {
        if (isFirst == 0) {
            height = view.getHeight();
            isFirst = 1;
        }
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    /**
     * 获取新闻数据
     */
    public void getNewsData() {
        isCanLoadAD = 0;
        Observable request = RetrofitUtils.getInstance().getNewsListData(typeID, page);
        Subscriber subscriber = new Subscriber<NewsListBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取新闻列表数据异常：" + e.toString());
            }

            @Override
            public void onNext(NewsListBean bean) {
                if (page == 1) {
                    load.setVisibility(View.GONE);
                    recyclerView.refreshComplete();
                    recyclerView.setLoadingMoreEnabled(true);
                } else {
                    recyclerView.loadMoreComplete();
                }
                if (bean.getErrcode() == 0) {
                    if (bean.getData() == null || bean.getData().size() == 0) {
                        recyclerView.setLoadingMoreEnabled(false);
                        return;
                    }

                    if (page == 1) {
                        if (isFirst != 0) {
                            ViewGroup.LayoutParams layoutParams = tvMessage.getLayoutParams();
                            layoutParams.height = height;
                            tvMessage.setLayoutParams(layoutParams);
                        }
                        tvMessage.setText("「头条精选」已为您更新" + bean.getData().size() + "条资讯");
                        tvMessage.setVisibility(View.VISIBLE);
                        handler.sendEmptyMessageDelayed(0, 1000);
                        data.clear();
                        FIRST_AD_POSITION = FIRST_AD;
                    }
                    page++;
                    data.addAll(bean.getData());
                    if (refreshState == 1) {
                        refreshState = 0;
                        adapter = new NewsAdapter(getActivity(), data, mAdViewPositionMap);
                        adapter.setOnClickListener(new NewsAdapter.OnClickListener() {
                            @Override
                            public void onClick(int id, int cat_id) {
                                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                                intent.putExtra("Url", String.valueOf(id));
                                intent.putExtra("TypeID", String.valueOf(cat_id));
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    // 广告开关
                    if (isOpenAD) {
//                        loadAd();
                        // 穿山甲广告
                        loadListAd();
                        // 广点通广告
                        initNativeExpressAD(0);
                        initNativeExpressAD(1);
                    }
                } else {
                    showToast(bean.getErrinfo());
                }
            }
        };
        request.subscribe(subscriber);
    }

    private int refreshState = 0;

    // 获取新闻数据
    public void getNews() {
        recyclerView.scrollToPosition(0);
        page = 1;
        refreshState = 1;
        getNewsData();
    }

    @Override
    public void getDataSucc(NewsListBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getData() == null)
                return;

            if (page == 1) {
                data.clear();
                recyclerView.refreshComplete();
            } else {
                recyclerView.loadMoreComplete();
            }
            data.addAll(bean.getData());
            adapter.notifyDataSetChanged();
        } else {
            showToast(bean.getErrinfo());
        }
    }

    /**
     * 加载feed广告
     */
    private void loadListAd() {
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(Constants.TOUTIAO_AD_ID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(AD_COUNT)
                .build();

        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                addNum();
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                mTTADList = ads;
                addNum();
            }
        });
    }

    // 判断是否可以加载广告的计数器累加
    private void addNum() {
        isCanLoadAD++;
        if (isCanLoadAD >= 3) {
            if (FIRST_AD_POSITION == FIRST_AD) {
                addADFactory(0);
            } else {
                addADFactory(1);
            }
        }
    }

    /**
     * 添加广告到列表 工厂方法
     *
     * @param type 是否是第一条
     */
    private void addADFactory(int type) {
        if ((mAdViewList == null || mAdViewList.size() == 0) && (mAdBigImageList == null || mAdBigImageList.size() == 0) && (mTTADList == null || mTTADList.size() == 0)) {
            return;
        }
        int position = FIRST_AD_POSITION;
        if (position < data.size()) {
            if (type == 0) {
                // TODO 第一条广告 添加广点通广告
                int adRandom = (int) (Math.random() * 2); // 0 - 1
                int positionRandom = (int) (1 + Math.random() * 3) + 3; // 4 - 6
                if (adRandom == 0) {
                    // 广点通信息流
                    if (mAdViewList != null && mAdViewList.size() > 0) {
                        NativeExpressADView view = mAdViewList.get(0);
                        mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                        adapter.addADViewToPosition(position, view);
                        mAdViewList.remove(0);
                        FIRST_AD_POSITION += positionRandom;
                    }
                } else {
                    // 今日头条信息流
                    if (mTTADList != null && mTTADList.size() > 0) {
                        TTFeedAd ad = mTTADList.get(0);
                        /*ad.registerViewForInteraction((ViewGroup) holder.itemView, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
                            @Override
                            public void onAdClicked(View view, TTFeedAd ad) {
                            }

                            @Override
                            public void onAdCreativeClick(View view, TTFeedAd ad) {
                            }

                            @Override
                            public void onAdShow(TTFeedAd ad) {
                                LogUtils.d("头条广告：onAdShow " + ad.getTitle());
                            }
                        });*/
                        adapter.addTTADToPosition(position, ad);
                        // 移除第一条广告
                        mTTADList.remove(0);
                        FIRST_AD_POSITION += positionRandom;
                    }
                }
            } else {
                // TODO 随机广告添加到随机位置
                int positionRandom = (int) (1 + Math.random() * 3) + 3; // 4 - 6
                int adRandom = (int) (Math.random() * 10); // 0 - 9
                if (adRandom < 5) { // 广点通
                    if ((int) (Math.random() * 2) == 0) {
                        if (mAdViewList != null && mAdViewList.size() > 0) {
                            NativeExpressADView view = mAdViewList.get(0);
                            mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                            adapter.addADViewToPosition(position, view);
                            mAdViewList.remove(0);
                            FIRST_AD_POSITION += positionRandom;
                        }
                    } else {
                        if (mAdBigImageList != null && mAdBigImageList.size() > 0) {
                            NativeExpressADView view = mAdBigImageList.get(0);
                            mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                            adapter.addADViewToPosition(position, view);
                            mAdBigImageList.remove(0);
                            FIRST_AD_POSITION += positionRandom;
                        }
                    }
                } else if (adRandom == 9) { // 百度
                    AdView adView = new AdView(getActivity(), Constants.BAIDU_BANNER);
                    adapter.addBDADToPosition(position, adView);
                    FIRST_AD_POSITION += positionRandom;
                } else { // 穿山甲
                    if (mTTADList != null && mTTADList.size() > 0) {
                        TTFeedAd ad = mTTADList.get(0);
                        adapter.addTTADToPosition(position, ad);
                        // 移除第一条广告
                        mTTADList.remove(0);
                        FIRST_AD_POSITION += positionRandom;
                    }
                }
            }
            // 重新随机
            addADFactory(1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        addNum();
        LogUtils.d("广告加载失败：" + adError.toString());
    }

    /**
     * 加载广点通广告
     */
    private void initNativeExpressAD(int type) {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        if (type == 1) {
            // 左图右文
            mADManager = new NativeExpressAD(getActivity(), adSize, Constants.GDT_APP_ID, Constants.GDT_LEFT_IMAGE_ID, this);
        } else {
            // 大图广告
            mADManager = new NativeExpressAD(getActivity(), adSize, Constants.GDT_APP_ID, Constants.GDT_BIG_IMAGE, new NativeExpressAD.NativeExpressADListener() {
                @Override
                public void onNoAD(AdError adError) {
                    addNum();
                }

                @Override
                public void onADLoaded(List<NativeExpressADView> list) {
                    addNum();
                    mAdBigImageList = list;
                }

                @Override
                public void onRenderFail(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADExposure(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADClicked(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADClosed(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

                }
            });
        }
        mADManager.loadAD(AD_COUNT);
    }

    private boolean canLoadBaiDuAd = false;

    @Override
    public void onADLoaded(List<NativeExpressADView> adList) {
        mAdViewList = adList;
        addNum();
        /*for (int i = 0; i < mAdViewList.size(); i++) {
            int position = FIRST_AD_POSITION;
            if (position < data.size()) {
                int randNum = (int) (1 + Math.random() * 3) + 3; // 4 - 6
                if ((int) (Math.random() * 3) == 1 && canLoadBaiDuAd) {
                    // 百度大图
                    canLoadBaiDuAd = false;
                    AdView adView = new AdView(getActivity(), Constants.BAIDU_BANNER);
                    adapter.addBDADToPosition(position, adView);
                } else {
                    // 广点通信息流
                    canLoadBaiDuAd = true;
                    NativeExpressADView view = mAdViewList.get(i);
                    mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                    adapter.addADViewToPosition(position, mAdViewList.get(i));
                }
                FIRST_AD_POSITION += randNum;
            }
        }
        adapter.notifyDataSetChanged();*/
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
        LogUtils.d("广点通广告：onRenderSuccess");
    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        LogUtils.d("点击广告了。。。");
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        if (adapter != null) {
            int removedPosition = mAdViewPositionMap.get(nativeExpressADView);
            adapter.removeADView(removedPosition, nativeExpressADView);
        }
    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 使用完了每一个NativeExpressADView之后都要释放掉资源。
        if (mAdViewList != null) {
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }
}
