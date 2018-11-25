package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.adconfig.TTAdManagerHolder;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.tab1.adapter.RecommendNewsAdapter;
import com.deshang.ttjx.ui.tab1.adapter.RecommendNewsListAdapter;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 领取红包雨倒计时Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class ReceiveRedPacketOnWayDialog extends Dialog implements View.OnClickListener, NativeExpressAD.NativeExpressADListener {

    private static final int AD_COUNT = 10;    // 加载广告的条数，取值范围为[1, 10]
    private int FIRST_AD_POSITION = 1; // 第一条广告的position
    private int ITEMS_PER_AD = 3;     // 每间隔3个新闻插入一条广告  本身加上自己插入的一天广告所以是4

    private Context context;
    private View view;
    private ImageView close;
    private TextView redPacketTime;
    private TextView tvClose, tvShare;
    private TextView readMore;

    private RecommendBean bean;
    private ListView listView;
    private RecommendNewsListAdapter adapter;

    private List<Object> data;
    private NativeExpressAD mADManager;
    private List<NativeExpressADView> mAdViewList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    private TTAdNative mTTAdNative; // 穿山甲AD
    private List<TTFeedAd> mTTADList;
    private int isCanLoadAD = 0;

    {
        data = new ArrayList<>();
        mAdViewPositionMap = new HashMap<>();
        mAdViewList = new ArrayList<>();
        mTTADList = new ArrayList<>();
    }

    public ReceiveRedPacketOnWayDialog(@NonNull Context context, RecommendBean bean) {
        super(context, R.style.Dialog);
        this.context = context;
        this.bean = bean;
    }

    public void setTime(String time) {
        redPacketTime.setText(time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_receive_red_packet_on_way, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        TTAdManager ttAdManager = TTAdManagerHolder.getInstance();
        mTTAdNative = ttAdManager.createAdNative(context);
        close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);

        redPacketTime = (TextView) view.findViewById(R.id.time);
        tvClose = (TextView) view.findViewById(R.id.tv_close);
        tvClose.setOnClickListener(this);
        tvShare = (TextView) view.findViewById(R.id.tv_share);
        tvShare.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.list_view);

        readMore = (TextView) view.findViewById(R.id.read_more);
        readMore.setOnClickListener(this);

        data.addAll(bean.getReturn());
        adapter = new RecommendNewsListAdapter(context, data, mAdViewPositionMap);
        adapter.setOnClickListener(new RecommendNewsListAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                dismiss();
            }
        });
        listView.setAdapter(adapter);
        initNativeExpressAD();
        loadListAd();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.tv_close:
                dismiss();
                break;

            case R.id.read_more:
                dismiss();
                ((MainActivity) context).switchFragment(0);
                break;

            case R.id.tv_share:

                break;
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
        if (isCanLoadAD >= 2) {
            addADFactory();
        }
    }

    /**
     * 添加广告到列表 工厂方法
     *
     * @param
     */
    private void addADFactory() {
        if ((mAdViewList == null || mAdViewList.size() == 0) && (mTTADList == null || mTTADList.size() == 0)) {
            return;
        }
        int position = FIRST_AD_POSITION;
        if (position < data.size()) {
            int adRandom = (int) (Math.random() * 2); // 0 - 1
            if (adRandom == 0) {
                // 广点通信息流
                if (mAdViewList != null && mAdViewList.size() > 0) {
                    NativeExpressADView view = mAdViewList.get(0);
                    mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                    adapter.addADViewToPosition(position, view);
                    mAdViewList.remove(0);
                    FIRST_AD_POSITION += ITEMS_PER_AD;
                }
            } else {
                // 今日头条信息流
                if (mTTADList != null && mTTADList.size() > 0) {
                    TTFeedAd ad = mTTADList.get(0);
                    adapter.addTTADToPosition(position, ad);
                    // 移除第一条广告
                    mTTADList.remove(0);
                    FIRST_AD_POSITION += ITEMS_PER_AD;
                }
            }
            // 重新随机
            addADFactory();
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
    private void initNativeExpressAD() {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        // 左图右文
        mADManager = new NativeExpressAD(context, adSize, Constants.GDT_APP_ID, Constants.GDT_LEFT_IMAGE_ID, this);
        mADManager.loadAD(AD_COUNT);
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> adList) {
        mAdViewList = adList;
        addNum();
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
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(32);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mTTAdNative = null;
        if (mAdViewList != null) {
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }
}
