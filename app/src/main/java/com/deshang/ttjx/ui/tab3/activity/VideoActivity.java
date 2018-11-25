package com.deshang.ttjx.ui.tab3.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.mywidget.viewpager.VerticalViewPager;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.adapter.VerticalViewPagerAdapter;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab3.presenter.VideoLoadPresenter;
import com.deshang.ttjx.ui.tab3.view.VideoLoadView;
import com.deshang.ttjx.ui.tab4.bean.StartAppView;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频播放承载页面
 * Created by L on 2018/10/26.
 */

public class VideoActivity extends MvpSimpleActivity<VideoLoadView, VideoLoadPresenter> implements VideoLoadView {

    private int AD_COUNT = 10;    // 加载广告的条数，取值范围为[1, 10]
    private int FIRST_AD_POSITION = 4; // 第一条广告的position

    @BindView(R.id.viewpager)
    VerticalViewPager viewPager;
    @BindView(R.id.image_smoke)
    ImageView image_smoke;
    @BindView(R.id.wallet)
    ImageView wallet;
    @BindView(R.id.rl_tb)
    RelativeLayout rl_tb;

    private VerticalViewPagerAdapter pagerAdapter;

    private AnimationDrawable drawable;

    private boolean isLoad = false;
    private boolean haveMore = true;
    private int maxPosition = 0;
    private boolean flag;

    private NativeExpressAD mADManager;
    // 广点通大图广告集
    private List<NativeExpressADView> mAdBigImageList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    private int startPosition = 0; // 上级列表点击了哪条视频

    int[] location1;

    {
        mAdBigImageList = new ArrayList<>();
        mAdViewPositionMap = new HashMap<>();
        location1 = new int[2];
    }

    // 红钻图片宽度
    private int bitmapWidth;

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public int[] getLocation1() {
        wallet.getLocationOnScreen(location1);
        return location1;
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_video);
    }

    @Override
    public void initView() {
        if (!SharedPrefHelper.getInstance().getOnLine()) {
            rl_tb.setVisibility(View.GONE);
        }
        startPosition = getIntent().getIntExtra("VideoPosition", 0);
        FIRST_AD_POSITION = FIRST_AD_POSITION + startPosition;

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wallet_diamond);
        bitmapWidth = bitmap.getWidth();

        pagerAdapter = new VerticalViewPagerAdapter(getSupportFragmentManager(), this, Constants.videoData, mAdViewPositionMap);
        viewPager.setOffscreenPageLimit(1);
        pagerAdapter.setOnLoadMoreListener(new VerticalViewPagerAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getPresenter().getVideoData(Constants.videoPage);
            }
        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(startPosition);
        viewPager.addOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.d("onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        flag = false;
                        break;

                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        if (!haveMore && viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
                            showToast("已经到底了");
                        } else {
                            if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !flag && !isLoad) {
                                isLoad = true;
//                            showToast("正在加载更多...");
                                getPresenter().getVideoData(Constants.videoPage);
                                maxPosition = viewPager.getCurrentItem();
                                LogUtils.d("maxPosition：" + maxPosition + " 数据长度：" + Constants.videoData.size());
                            }
                        }
                        flag = true;
                        break;

                }
            }
        });
        if (!"".equals(SharedPrefHelper.getInstance().getToken())) {
            getPresenter().videoVoteData();
        }
        if (drawable == null) {
            image_smoke.setImageResource(R.drawable.wallet_red_animlist);
            drawable = (AnimationDrawable) image_smoke.getDrawable();
        }
        getADList();
    }

    // 拉取广告数据
    private void getADList() {
        // 大图广告
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(getActivity(), adSize, Constants.GDT_APP_ID, Constants.GDT_BIG_IMAGE, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                pagerAdapter.notifyDataSetChanged();
                if (isLoad) {
                    isLoad = false;
                    viewPager.setCurrentItem(maxPosition + 1);
                }
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                mAdBigImageList = list;
                addADFactory();
                /*for (int i = 0; i < Constants.videoData.size(); i++) {
                    LogUtils.d("---------位置：" + i + " 标题：" + ((VideoBean.ReturnBean) Constants.videoData.get(i)).getTitle());
                }*/
                if (isLoad) {
                    isLoad = false;
                    viewPager.setCurrentItem(maxPosition + 1);
                }
//                pagerAdapter.bianli();
                pagerAdapter.notifyDataSetChanged();
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
        mADManager.loadAD(AD_COUNT);
    }

    // 添加广告
    private void addADFactory() {
        if (mAdBigImageList == null || mAdBigImageList.size() == 0) {
            return;
        }
        int position = FIRST_AD_POSITION;
        if (position < pagerAdapter.getListSize()) {
            // TODO 随机广告添加到随机位置
            int positionRandom = (int) (1 + Math.random() * 3) + 3; // 4 - 6
            int adRandom = (int) (Math.random() * 2); // 0 - 1
            LogUtils.d("位置:" + FIRST_AD_POSITION + " 数字随机:" + adRandom + " 数据长度：" + Constants.videoData.size());
            if (adRandom == 0) {
                // 大图
                if (mAdBigImageList != null && mAdBigImageList.size() > 0) {
                    NativeExpressADView view = mAdBigImageList.get(0);
                    pagerAdapter.addADViewToPosition(position, view);
                    mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                    mAdBigImageList.remove(0);
                    FIRST_AD_POSITION += positionRandom;
                }
            } else {
                // 开屏
                pagerAdapter.addStartAppAD(position, new StartAppView());
                FIRST_AD_POSITION += positionRandom;
            }
            addADFactory();
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean getIsAllowOneScreen() {
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        if (drawable == null) {
            image_smoke.setImageResource(R.drawable.wallet_red_animlist);
            drawable = (AnimationDrawable) image_smoke.getDrawable();
        }
    }

    @OnClick({R.id.back, R.id.rl_tb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.rl_tb:
                // 去赚钱页面
                Constants.turn_to_other_tab = 2;
                finish();
                break;
        }
    }

    @Override
    public void getVideoList(VideoBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null && bean.getReturn().size() > 0) {
                haveMore = true;
                pagerAdapter.setHaveMore(true);
                Constants.videoPage++;
                Constants.videoData.addAll(bean.getReturn());
                pagerAdapter.addData(bean.getReturn());
                getADList();
            } else {
                pagerAdapter.setHaveMore(false);
                haveMore = false;
            }
        } else {
            pagerAdapter.setHaveMore(false);
            haveMore = false;
            showToast(bean.getErrinf());
        }
    }

    public void setWalletUI(int visible) {
        if (visible == 0) {
            // 没有可以领取的钻石
            wallet.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_tb_gray));
            drawable.stop();
            image_smoke.setVisibility(View.INVISIBLE);
        } else {
            if (drawable != null) {
                wallet.setImageDrawable(getResources().getDrawable(R.mipmap.video_wallet_select));
                image_smoke.setVisibility(View.VISIBLE);
                drawable.start();
            }
        }
    }

    @Override
    public void getVideoVote(VideoVoteBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;
            Constants.videoVoteNumber = bean.getReturn().getTotal_votes();
            if (bean.getReturn().getEinnahmen_count() == 0 && drawable != null) {
                // 没有可以领取的钻石
                wallet.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_tb_gray));
                drawable.stop();
                image_smoke.setVisibility(View.INVISIBLE);
            } else {
                if (drawable != null) {
                    wallet.setImageDrawable(getResources().getDrawable(R.mipmap.video_wallet_select));
                    image_smoke.setVisibility(View.VISIBLE);
                    drawable.start();
                }
            }
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public VideoLoadPresenter createPresenter() {
        return new VideoLoadPresenter();
    }
}
