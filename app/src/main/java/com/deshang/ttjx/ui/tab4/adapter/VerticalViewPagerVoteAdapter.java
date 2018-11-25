package com.deshang.ttjx.ui.tab4.adapter;

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
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;
import com.deshang.ttjx.ui.tab4.fragment.VideoVoteShowFragment;

import java.util.List;

public class VerticalViewPagerVoteAdapter extends PagerAdapter {
    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    private List<MyVedioBean.ReturnBean> urlList;
    private Context context;

    public void setUrlList(List<MyVedioBean.ReturnBean> urlList) {
        this.urlList = urlList;
        notifyDataSetChanged();
    }

    public VerticalViewPagerVoteAdapter(FragmentManager fm, Context context) {
        this.fragmentManager = fm;
        this.context = context;
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

        VideoVoteShowFragment fragment = new VideoVoteShowFragment();
        if (urlList != null && urlList.size() > 0) {
            Bundle bundle = new Bundle();
           /* if (position >= urlList.size() - 3 && loadMoreListener != null && haveMore) {
                loadMoreListener.loadMore();
            }*/
            LogUtils.d("position:" + position + " size:" + urlList.size());
            if (position >= urlList.size()) {
                ReceiveGoldToast.makeToast(context, "没有更多了~").show();
            } else {
                MyVedioBean.ReturnBean bean = urlList.get(position);
                bundle.putInt(VideoVoteShowFragment.ID, bean.getAid());
                bundle.putString(VideoVoteShowFragment.URL, bean.getUrl().get(0));
                bundle.putString(VideoVoteShowFragment.IMAGE_URL, bean.getCover_img());
                bundle.putString(VideoVoteShowFragment.VIDEO_TITLE, bean.getTitle());
                bundle.putString(VideoVoteShowFragment.DIAMOND_NUMBER, bean.getGain_gold());
            }
            fragment.setArguments(bundle);
        }
        mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), position));
        return fragment;
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
