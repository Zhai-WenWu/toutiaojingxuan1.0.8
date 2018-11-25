package com.deshang.ttjx.ui.tab4.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.persenter.AccountInfoPresenter;
import com.deshang.ttjx.ui.tab4.view.AccountInfoView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.widget.CircleImageView;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;
import com.deshang.ttjx.ui.tab4.adapter.AccountAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by L on 2018/6/8.
 * 账户信息
 */

public class AccountInfoActivity extends MvpSimpleActivity<AccountInfoView, AccountInfoPresenter> implements AccountInfoView {

    @BindView(R.id.header_image)
    CircleImageView header;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.all_money)
    TextView allMoney;
    @BindView(R.id.balance_money)
    TextView balanceMoney;
    @BindView(R.id.today_money)
    TextView todayMoney;
    @BindView(R.id.balance_dollar)
    TextView balanceDollar;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    private AccountAdapter adapter;
    private List<AccountInfoBean.ReturnBean> data;
    private int page = 1;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_acc_info);
    }

    @Override
    public void initView() {
        titleBar.setTitle("挖矿明细");
        titleBar.setBack(true);
        data = new ArrayList<>();
        adapter = new AccountAdapter(this, data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                recyclerView.setLoadingMoreEnabled(true);
                page = 1;
                getPresenter().getAccountData(page);
            }

            @Override
            public void onLoadMore() {
                getPresenter().getAccountData(page);
            }
        });

        /*GlideLoading.getInstance().loadImageHeader(this, SharedPrefHelper.getInstance().getAvatar(), header);

        if ("".equals(SharedPrefHelper.getInstance().getUserName())){
            userName.setText("用户" + SharedPrefHelper.getInstance().getUserId());
        } else {
            userName.setText(SharedPrefHelper.getInstance().getUserName());
        }
        allMoney.setText("总收益：" + getIntent().getIntExtra("allMoney", 0));
        balanceMoney.setText(String.valueOf(getIntent().getIntExtra("balanceMoney", 0)));
        todayMoney.setText(String.valueOf(getIntent().getIntExtra("todayMoney", 0)));
        balanceDollar.setText(getIntent().getStringExtra("balanceDollar"));*/

        getPresenter().getAccountData(page);
    }

    @Override
    public void getAccountDataSuccess(AccountInfoBean bean) {
        if (page == 1) {
            recyclerView.refreshComplete();
        } else {
            recyclerView.loadMoreComplete();
        }
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;
            if (page == 1) {
                data.clear();
            }
            if (bean.getReturn().size() == 0) {
                recyclerView.setLoadingMoreEnabled(false);
            }
            data.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
            page++;
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public AccountInfoPresenter createPresenter() {
        return new AccountInfoPresenter();
    }
}
