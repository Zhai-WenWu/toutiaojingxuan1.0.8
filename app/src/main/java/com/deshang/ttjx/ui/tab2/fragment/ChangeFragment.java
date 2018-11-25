package com.deshang.ttjx.ui.tab2.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.ui.mywidget.RecyclerViewNoBugLinearLayoutManager;
import com.deshang.ttjx.ui.tab2.activity.MyWalletActivity;
import com.deshang.ttjx.ui.tab2.adapter.ChangeAndGoldAdapter;
import com.deshang.ttjx.ui.tab2.bean.ChangeBean;
import com.deshang.ttjx.ui.tab2.bean.ChangeOrGoldBean;
import com.deshang.ttjx.ui.tab2.persenter.ChangePresenter;
import com.deshang.ttjx.ui.tab2.view.ChangeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的钱包/零钱 Fragment
 * Created by L on 2018/7/5.
 */

public class ChangeFragment extends MvpSimpleFragment<ChangeView, ChangePresenter> implements ChangeView {

    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private ChangeAndGoldAdapter adapter;
    private List<ChangeOrGoldBean> data;
    private int page = 1;

    {
        data = new ArrayList<>();
    }

    public static ChangeFragment getInstance() {
        ChangeFragment fragment = new ChangeFragment();
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

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerViewNoBugLinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                recyclerView.setLoadingMoreEnabled(true);
                getPresenter().getChangeData(page);
            }

            @Override
            public void onLoadMore() {
                getPresenter().getChangeData(page);
            }
        });
        getPresenter().getChangeData(page);
    }

    @Override
    public ChangePresenter createPresenter() {
        return new ChangePresenter();
    }

    @Override
    public void getChangeData(ChangeBean bean) {
//        ((MyWalletActivity) getActivity()).setMoney(ProjectUtils.calculateProfit(Double.valueOf(bean.getChange())));
        ((MyWalletActivity) getActivity()).setMoney(bean.getChange());
        ((MyWalletActivity) getActivity()).setGold(bean.getGold());
        ((MyWalletActivity) getActivity()).setContent(bean.getWallet_info());

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
            page++;
            data.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
        }
    }
}
