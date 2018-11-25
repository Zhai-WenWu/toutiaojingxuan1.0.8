package com.deshang.ttjx.ui.tab3.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.ui.tab3.activity.MyApprenticeActivity;
import com.deshang.ttjx.ui.tab3.adapter.ApprenticeOrDiscipleAdapter;
import com.deshang.ttjx.ui.tab3.bean.ApprenticeOrDiscipleBean;
import com.deshang.ttjx.ui.tab3.bean.MyDiscipleBean;
import com.deshang.ttjx.ui.tab3.presenter.MyDisciplePresenter;
import com.deshang.ttjx.ui.tab3.view.MyDiscipleView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的徒弟/我的徒弟 Fragment
 * Created by L on 2018/7/5.
 */

public class MyDiscipleFragment extends MvpSimpleFragment<MyDiscipleView, MyDisciplePresenter> implements MyDiscipleView {

    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.no_data_text)
    TextView tvNoData;

    private ApprenticeOrDiscipleAdapter adapter;
    private List<ApprenticeOrDiscipleBean> data;

    {
        data = new ArrayList<>();
    }

    public static MyDiscipleFragment getInstance() {
        MyDiscipleFragment fragment = new MyDiscipleFragment();
        return fragment;
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_change);
    }

    @Override
    public MyDisciplePresenter createPresenter() {
        return new MyDisciplePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View v) {
//        llNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(R.string.null_disciple);
        adapter = new ApprenticeOrDiscipleAdapter(getActivity(), data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getMyDisciple();
    }

    @Override
    public void getMyDisciple(MyDiscipleBean bean) {
        MyApprenticeActivity activity = (MyApprenticeActivity) getActivity();
        activity.setApprenticeNum("我的徒孙：" + bean.getDisciple_number() + "名");
        activity.setMyApprenticeMoney("我的徒孙进贡收益：" + bean.getOne_into() + "红钻");

        if (bean.getErrcode() == 0) {
            if (bean.getErrcode() == 0) {
                if (bean.getReturn() == null) {
                    if (data.size() == 0) {
                        llNoData.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                data.clear();
                data.addAll(bean.getReturn());
                if (data.size() > 0) {
                    llNoData.setVisibility(View.GONE);
                } else {
                    llNoData.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
