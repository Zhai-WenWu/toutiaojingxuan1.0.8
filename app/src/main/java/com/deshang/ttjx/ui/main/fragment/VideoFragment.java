package com.deshang.ttjx.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.ui.main.adapter.VideoAdapter;
import com.deshang.ttjx.ui.main.presenter.VideoPresenter;
import com.deshang.ttjx.ui.main.view.VideoView;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.activity.VideoActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 小视频Tab模块
 * Created by L on 2017/12/4.
 */
public class VideoFragment extends MvpSimpleFragment<VideoView, VideoPresenter> implements VideoView {

    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private VideoAdapter adapter;

//    private List<Object> data;
//    private List<String> imageList;
//    private List<String> videoList;

    {
        /*imageList = new ArrayList<>();
        imageList.add("http://ksy.fffffive.com/mda-himtqzs2un1u8x2v/mda-himtqzs2un1u8x2v.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hiw5zixc1ghpgrhn/mda-hiw5zixc1ghpgrhn.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hiw61ic7i4qkcvmx/mda-hiw61ic7i4qkcvmx.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hihvysind8etz7ga/mda-hihvysind8etz7ga.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hiw60i3kczgum0av/mda-hiw60i3kczgum0av.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hidnzn5r61qwhxp4/mda-hidnzn5r61qwhxp4.jpg");
        imageList.add("http://ksy.fffffive.com/mda-he1zy3rky0rwrf60/mda-he1zy3rky0rwrf60.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hh6cxd0dqjqcszcj/mda-hh6cxd0dqjqcszcj.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hifsrhtqjn8jxeha/mda-hifsrhtqjn8jxeha.jpg");
        imageList.add("http://ksy.fffffive.com/mda-hics799vjrg0w5az/mda-hics799vjrg0w5az.jpg");*/

        /*videoList = new ArrayList<>();
        videoList.add("http://ksy.fffffive.com/mda-himtqzs2un1u8x2v/mda-himtqzs2un1u8x2v.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hiw5zixc1ghpgrhn/mda-hiw5zixc1ghpgrhn.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hiw61ic7i4qkcvmx/mda-hiw61ic7i4qkcvmx.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hihvysind8etz7ga/mda-hihvysind8etz7ga.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hiw60i3kczgum0av/mda-hiw60i3kczgum0av.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hidnzn5r61qwhxp4/mda-hidnzn5r61qwhxp4.mp4");
        videoList.add("http://ksy.fffffive.com/mda-he1zy3rky0rwrf60/mda-he1zy3rky0rwrf60.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hh6cxd0dqjqcszcj/mda-hh6cxd0dqjqcszcj.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hifsrhtqjn8jxeha/mda-hifsrhtqjn8jxeha.mp4");
        videoList.add("http://ksy.fffffive.com/mda-hics799vjrg0w5az/mda-hics799vjrg0w5az.mp4");*/

        /*for (int i = 0; i < videoList.size(); i++) {
            VideoBean.ReturnBean bean = new VideoBean.ReturnBean();
            bean.setImageUrl(imageList.get(i));
            bean.setVideoUrl(videoList.get(i));
            Constants.videoData.add(bean);
        }*/
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.f_video);
    }

    @Override
    public void initView(View v) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new VideoAdapter(getActivity(), Constants.videoData);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new VideoAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("VideoPosition", position);
                startActivity(intent);
            }
        });

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Constants.videoPage = 1;
                getPresenter().getVideoList(Constants.videoPage);
            }

            @Override
            public void onLoadMore() {
                getPresenter().getVideoList(Constants.videoPage);
            }
        });
        getPresenter().getVideoList(Constants.videoPage);
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public VideoPresenter createPresenter() {
        return new VideoPresenter();
    }

    @Override
    public void getVideoList(VideoBean bean) {
        recyclerView.refreshComplete();
        recyclerView.loadMoreComplete();
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null && bean.getReturn().size() > 0) {
                if (Constants.videoPage == 1) {
                    recyclerView.setLoadingMoreEnabled(true);
                    Constants.videoData.clear();
                }
                Constants.videoPage++;
                Constants.videoData.addAll(bean.getReturn());
                adapter.notifyDataSetChanged();
            } else {
                recyclerView.setLoadingMoreEnabled(false);
            }
        } else {
            showToast(bean.getErrinf());
        }
    }
}
