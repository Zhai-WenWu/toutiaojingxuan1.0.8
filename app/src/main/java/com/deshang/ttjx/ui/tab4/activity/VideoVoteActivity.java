package com.deshang.ttjx.ui.tab4.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.ui.mywidget.viewpager.VerticalViewPager;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab4.adapter.VerticalViewPagerVoteAdapter;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;
import com.deshang.ttjx.ui.tab4.persenter.VideoVoteLoadPresenter;
import com.deshang.ttjx.ui.tab4.view.VideoVoteLoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频播放承载页面
 * Created by L on 2018/10/26.
 */

public class VideoVoteActivity extends MvpSimpleActivity<VideoVoteLoadView, VideoVoteLoadPresenter> implements VideoVoteLoadView {

    @BindView(R.id.viewpager)
    VerticalViewPager viewPager;
    @BindView(R.id.image_smoke)
    ImageView image_smoke;
    @BindView(R.id.wallet)
    ImageView wallet;
    @BindView(R.id.rl_tb)
    RelativeLayout rl_tb;

    private VerticalViewPagerVoteAdapter pagerAdapter;

    private AnimationDrawable drawable;

    private boolean isLoad = false;
    private boolean haveMore = true;
    private int maxPosition = 0;
    private boolean flag;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_video);
    }

    @Override
    public void initView() {
        rl_tb.setVisibility(View.GONE);
        pagerAdapter = new VerticalViewPagerVoteAdapter(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(1);
        pagerAdapter.setUrlList(Constants.videoVoteData);
        pagerAdapter.setOnLoadMoreListener(new VerticalViewPagerVoteAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getPresenter().getVideoData(Constants.videoVotePage);
            }
        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("VideoPosition", 0));
        viewPager.addOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
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
                        if (!haveMore && viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 ) {
                            showToast("已经到底了");
                        } else {
                            if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !flag && !isLoad) {
                                isLoad = true;
//                            showToast("正在加载更多...");
                                getPresenter().getVideoData(Constants.videoVotePage);
                                maxPosition = viewPager.getCurrentItem();
                            }
                        }
                        flag = true;
                        break;

                }
            }
        });
//        getPresenter().videoVoteData();
        if (drawable == null) {
            image_smoke.setImageResource(R.drawable.wallet_red_animlist);
            drawable = (AnimationDrawable) image_smoke.getDrawable();
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
    public void getVideoList(MyVedioBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null && bean.getReturn().size() > 0) {
                haveMore = true;
                pagerAdapter.setHaveMore(true);
                Constants.videoVotePage++;
                Constants.videoVoteData.addAll(bean.getReturn());
                pagerAdapter.setUrlList(Constants.videoVoteData);
                if (isLoad && Constants.videoVoteData.size() - 1 > maxPosition) {
                    isLoad = false;
                    viewPager.setCurrentItem(maxPosition + 1);
                }
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
    public VideoVoteLoadPresenter createPresenter() {
        return new VideoVoteLoadPresenter();
    }
}
