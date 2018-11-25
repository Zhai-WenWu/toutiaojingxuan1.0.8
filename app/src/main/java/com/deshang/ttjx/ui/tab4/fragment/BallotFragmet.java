package com.deshang.ttjx.ui.tab4.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.tab4.adapter.MyBallotAdapter;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;
import com.deshang.ttjx.ui.tab4.persenter.BallotPresenter;
import com.deshang.ttjx.ui.tab4.view.BallotView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 13364 on 2018/10/27.
 */

public class BallotFragmet extends MvpSimpleFragment<BallotView, BallotPresenter> implements BallotView {
    @BindView(R.id.ballot_title)
    TitleBar ballotTitle;
    @BindView(R.id.rv_ballot)
    XRecyclerView rvBallot;

    List<BallotBean.ReturnBean> returnList;
    private MyBallotAdapter adapter;
    private int page = 1;

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_ballot);
    }


    @Override
    public void initView(View v) {
//        ballotTitle.setBack(true);
//        ballotTitle.setTitle("我的投票");
        returnList = new ArrayList<>();
        adapter = new MyBallotAdapter(returnList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBallot.setLayoutManager(linearLayoutManager);
        rvBallot.setAdapter(adapter);
        rvBallot.setPullRefreshEnabled(false);
        rvBallot.setLoadingMoreEnabled(false);
        rvBallot.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                getPresenter().getBallotData(page);
            }
        });
        getPresenter().getBallotData(page);

    }

    @Override
    public void getBallotDataSuccess(BallotBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;
            if (page == 1) {
                returnList.clear();
            }else {
                rvBallot.loadMoreComplete();
            }
            if (bean.getReturn().size() == 0) {
                rvBallot.setLoadingMoreEnabled(false);
//                ReceiveGoldToast.makeToast(this, "您还没有点过赞，赶紧阅读找寻自己喜欢的文章吧").show();
            }
            returnList.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
            page++;
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public BallotPresenter createPresenter() {
        return new BallotPresenter();
    }


}
