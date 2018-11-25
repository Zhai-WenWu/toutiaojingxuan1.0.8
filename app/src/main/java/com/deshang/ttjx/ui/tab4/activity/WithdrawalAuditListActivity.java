package com.deshang.ttjx.ui.tab4.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.tab4.adapter.WithdrawalAuditAdapter;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalAuditBean;
import com.deshang.ttjx.ui.tab4.persenter.WithdrawalAuditPresenter;
import com.deshang.ttjx.ui.tab4.view.WithdrawalAuditView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by L on 2018/6/15.
 */

public class WithdrawalAuditListActivity extends MvpSimpleActivity<WithdrawalAuditView, WithdrawalAuditPresenter> implements WithdrawalAuditView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.ll_no_data)
    LinearLayout noData;

    private WithdrawalAuditAdapter adapter;
    private List<WithdrawalAuditBean.ReturnBean> data;

    {
        data = new ArrayList<>();
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_withdrawal_audit);
    }

    @Override
    public void initView() {
        titleBar.setBack(true);
        titleBar.setTitle("提现列表");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WithdrawalAuditAdapter(this, data);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getPresenter().getWithdrawalAuditData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        getPresenter().getWithdrawalAuditData();
    }

    @Override
    public WithdrawalAuditPresenter createPresenter() {
        return new WithdrawalAuditPresenter();
    }

    @Override
    public void getWithdrawalData(WithdrawalAuditBean bean) {
        recyclerView.refreshComplete();
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null) {
                if (data.size() == 0) {
                    noData.setVisibility(View.VISIBLE);
                }
                return;
            }

            data.clear();
            data.addAll(bean.getReturn());
            if (data.size() > 0) {
                noData.setVisibility(View.GONE);
            } else {
                noData.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        } else {
            showToast(bean.getErrinf());
            if (data.size() == 0) {
                noData.setVisibility(View.VISIBLE);
            }
        }
    }
}
