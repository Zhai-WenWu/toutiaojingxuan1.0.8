package com.deshang.ttjx.ui.tab4.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.tab4.adapter.MyBallotAdapter;
import com.deshang.ttjx.ui.tab4.adapter.MyGroupAdapter;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;
import com.deshang.ttjx.ui.tab4.bean.GroupBean;
import com.deshang.ttjx.ui.tab4.persenter.BallotPresenter;
import com.deshang.ttjx.ui.tab4.persenter.GroupPresenter;
import com.deshang.ttjx.ui.tab4.view.BallotView;
import com.deshang.ttjx.ui.tab4.view.GroupView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import mvp.cn.util.ToastUtil;

public class MyGroupActivity extends MvpSimpleActivity<GroupView, GroupPresenter> implements GroupView {

    @BindView(R.id.group_title)
    TitleBar groupTitle;
    @BindView(R.id.rv_group)
    XRecyclerView rvGroup;

    List<GroupBean.ReturnBean> returnList;
    private MyGroupAdapter adapter;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_group);
    }

    @Override
    public void initView() {
        groupTitle.setBack(true);
        groupTitle.setTitle("加入讨论");
        returnList = new ArrayList<>();
        adapter = new MyGroupAdapter(returnList, this);
        rvGroup.setPullRefreshEnabled(false);
        rvGroup.setLoadingMoreEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGroup.setLayoutManager(linearLayoutManager);
        rvGroup.setAdapter(adapter);
        adapter.setOnAddClick(new MyGroupAdapter.OnAddClickListener() {
            @Override
            public void OnAddClick(String key) {
                joinQQGroup(key);
            }
        });
        getPresenter().getGroup();
    }

    public void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            showToast( "未安装手Q或安装的版本不支持");
        }
    }

    @Override
    public void getGroup(GroupBean bean) {
        if (bean.getErrcode() == 0) {
            returnList.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public GroupPresenter createPresenter() {
        return new GroupPresenter();
    }
}
