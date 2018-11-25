package com.deshang.ttjx.ui.tab4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.ui.tab4.activity.VideoVoteActivity;
import com.deshang.ttjx.ui.tab4.adapter.VideoVoteAdapter;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;
import com.deshang.ttjx.ui.tab4.persenter.MyVideoPresenter;
import com.deshang.ttjx.ui.tab4.view.MyVideoView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

/**
 * Created by 13364 on 2018/10/27.
 */

public class VideoVoteFragment extends MvpSimpleFragment<MyVideoView, MyVideoPresenter> implements MyVideoView {

    @BindView(R.id.xrv)
    XRecyclerView xrv;

    private VideoVoteAdapter videoVoteAdapter;

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_video);
    }

    @Override
    public void initView(View v) {
        videoVoteAdapter = new VideoVoteAdapter(getContext(), Constants.videoVoteData);
        xrv.setAdapter(videoVoteAdapter);//设置适配器

        //设置布局管理器 , 将布局设置成纵向
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        xrv.setLayoutManager(gridLayoutManager);
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Constants.videoVotePage = 1;
                getPresenter().getVideoData(Constants.videoVotePage);
            }

            @Override
            public void onLoadMore() {
                getPresenter().getVideoData(Constants.videoVotePage);
            }
        });

        videoVoteAdapter.setOnClickListener(new VideoVoteAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), VideoVoteActivity.class);
                intent.putExtra("VideoPosition", position);
                startActivity(intent);
            }
        });

        getPresenter().getVideoData(Constants.videoVotePage);
    }

    @Override
    public void onResume() {
        super.onResume();
        videoVoteAdapter.notifyDataSetChanged();
    }

    @Override
    public void getVideoDataSuccess(MyVedioBean bean) {
        xrv.refreshComplete();
        xrv.loadMoreComplete();
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null || bean.getReturn().size() == 0) {
                xrv.setLoadingMoreEnabled(false);
                return;
            } else {
                xrv.setLoadingMoreEnabled(true);
            }
            if (Constants.videoVotePage == 1) {
                xrv.refreshComplete();
                Constants.videoVoteData.clear();
            }
            Constants.videoVoteData.addAll(bean.getReturn());
            videoVoteAdapter.notifyDataSetChanged();
            Constants.videoVotePage++;
        } else {
            showToast(bean.getErrinf());
        }

    }

    @Override
    public MyVideoPresenter createPresenter() {
        return new MyVideoPresenter();
    }
}
