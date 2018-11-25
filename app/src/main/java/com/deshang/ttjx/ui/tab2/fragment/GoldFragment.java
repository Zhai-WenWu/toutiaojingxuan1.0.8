package com.deshang.ttjx.ui.tab2.fragment;

import android.os.Bundle;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.ui.mywidget.RecyclerViewNoBugLinearLayoutManager;
import com.deshang.ttjx.ui.tab2.adapter.ChangeAndGoldAdapter;
import com.deshang.ttjx.ui.tab2.bean.ChangeOrGoldBean;
import com.deshang.ttjx.ui.tab2.bean.GoldBean;
import com.deshang.ttjx.ui.tab2.persenter.GoldPresenter;
import com.deshang.ttjx.ui.tab2.view.GoldView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的钱包/金币 Fragment
 * Created by L on 2018/7/5.
 */

public class GoldFragment extends MvpSimpleFragment<GoldView, GoldPresenter> implements GoldView {

    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private ChangeAndGoldAdapter adapter;
    private List<ChangeOrGoldBean> data;
    private int page = 1;

    {
        data = new ArrayList<>();
    }

    public static GoldFragment getInstance() {
        GoldFragment fragment = new GoldFragment();
        return fragment;
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_change);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View v) {
        adapter = new ChangeAndGoldAdapter(getActivity(), data);
        adapter.setType(1);

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerViewNoBugLinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                recyclerView.setLoadingMoreEnabled(true);
                getPresenter().getGoldData(page);
            }

            @Override
            public void onLoadMore() {
                getPresenter().getGoldData(page);
            }
        });
        getPresenter().getGoldData(page);
    }

    @Override
    public GoldPresenter createPresenter() {
        return new GoldPresenter();
    }

    @Override
    public void getGoldData(GoldBean bean) {
        if (page == 1) {
            recyclerView.refreshComplete();
        } else {
            recyclerView.loadMoreComplete();
        }

        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null) {
                return;
            }

            if (page == 1) {
                data.clear();
            }
            data.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
            page++;
        }
    }
}
