package com.deshang.ttjx.ui.tab3.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.viewpager.PagerAdapter;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.fragment.VideoADFragment;
import com.deshang.ttjx.ui.tab3.fragment.VideoMainFragment;
import com.deshang.ttjx.ui.tab4.bean.StartAppView;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerticalViewPagerAdapter extends PagerAdapter {
    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    private List<Object> urlList;
    private Context context;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;

    public VerticalViewPagerAdapter(FragmentManager fm, Context context, List<Object> data, HashMap<NativeExpressADView, Integer> mAdViewPositionMap) {
        urlList = new ArrayList<>();
        this.fragmentManager = fm;
        this.mAdViewPositionMap = mAdViewPositionMap;
        this.context = context;
        urlList.addAll(data);
    }

    public void setData(List<Object> data) {
        urlList.clear();
        urlList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<VideoBean.ReturnBean> data) {
        urlList.addAll(data);
//        notifyDataSetChanged();
        /*for (int i = 0; i < urlList.size(); i++) {
            if (urlList.get(i) instanceof VideoBean.ReturnBean) {
                LogUtils.d("位置：" + i + " 标题：" + ((VideoBean.ReturnBean) urlList.get(i)).getTitle());
            } else {
                LogUtils.d("位置：" + i + " 广告");
            }
        }*/
    }

    public int getListSize() {
        return urlList.size();
    }

    public void bianli() {
        for (int i = 0; i < urlList.size(); i++) {
            if (urlList.get(i) instanceof VideoBean.ReturnBean) {
                LogUtils.d("位置：" + i + " 标题：" + ((VideoBean.ReturnBean) urlList.get(i)).getTitle());
            } else {
                LogUtils.d("位置：" + i + " 广告");
            }
        }
    }

    // 添加广点通大图广告
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < urlList.size() && adView != null) {
            urlList.add(position, adView);
        }
    }

    // 添加广点通开屏广告
    public void addStartAppAD(int position, StartAppView adView) {
        if (position >= 0 && position < urlList.size() && adView != null) {
            urlList.add(position, adView);
        }
    }

    @Override
    public int getCount() {
        return urlList.size();
//        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }
        if (urlList.get(position) instanceof VideoBean.ReturnBean) {
            VideoMainFragment fragment = new VideoMainFragment();
            if (urlList != null && urlList.size() > 0) {
                Bundle bundle = new Bundle();
                LogUtils.d("position:" + position + " size:" + urlList.size());
                if (position >= urlList.size()) {
                    ReceiveGoldToast.makeToast(context, "没有更多了~").show();
                } else {
                    VideoBean.ReturnBean bean = (VideoBean.ReturnBean) urlList.get(position);
                    bundle.putInt(VideoMainFragment.ID, bean.getId());
                    bundle.putString(VideoMainFragment.URL, bean.getUrl().get(0));
                    bundle.putString(VideoMainFragment.IMAGE_URL, bean.getCover_img());
                    bundle.putString(VideoMainFragment.VIDEO_TITLE, bean.getTitle());
                    bundle.putInt(VideoMainFragment.LIKE_NUMBER, bean.getArticle_vote_count());
                    bundle.putInt(VideoMainFragment.TURN_NUMBER, bean.getShare_total());
                    bundle.putDouble(VideoMainFragment.DIAMOND_NUMBER, bean.getArticle_vote_sum());
                    bundle.putBoolean(VideoMainFragment.IS_VOTE, bean.getVote_type() == 1);
                    bundle.putInt("position", position);
                }
                fragment.setArguments(bundle);
            }
            mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), position));
            return fragment;
        } else if (urlList.get(position) instanceof NativeExpressADView) {
            LogUtils.d("进来广告:大图");
            VideoADFragment fragment = VideoADFragment.getInstance((NativeExpressADView) urlList.get(position), 1, position);
            mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), position));
            return fragment;
        } else {
            VideoADFragment fragment = VideoADFragment.getInstance(null, 0, position);
            mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), position));
            return fragment;
        }
    }

    // 视频还有更多视频
    private boolean haveMore = true;

    public boolean isHaveMore() {
        return haveMore;
    }

    public void setHaveMore(boolean haveMore) {
        this.haveMore = haveMore;
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public OnLoadMoreListener loadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = fragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment) object);
        mCurTransaction.remove((Fragment) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    private String makeFragmentName(int viewId, int position) {
        return "android:switcher:" + viewId + position;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }
}
