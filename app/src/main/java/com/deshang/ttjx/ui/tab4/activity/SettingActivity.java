package com.deshang.ttjx.ui.tab4.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.mywidget.dialog.LogOutDialog;
import com.deshang.ttjx.ui.mywidget.NoScrollLinearLayoutManager;
import com.deshang.ttjx.ui.tab4.adapter.SettingAdapter;
import com.deshang.ttjx.ui.tab4.bean.SettingBean;
import com.deshang.ttjx.ui.tab4.persenter.SettingPresenter;
import com.deshang.ttjx.ui.tab4.view.SettingView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面
 * Created by L on 2018/6/8.
 */

public class SettingActivity extends MvpSimpleActivity<SettingView, SettingPresenter> implements SettingView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Gson gson;
    private List<SettingBean.ReturnBean> data;
    private SettingAdapter adapter;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        data = new ArrayList<>();
        gson = new Gson();
        titleBar.setTitle("设置");
        titleBar.setBack(true);

        SettingBean settingBean = gson.fromJson(SharedPrefHelper.getInstance().getSettingData(), SettingBean.class);
        if (settingBean != null && settingBean.getReturn() != null) {
            data.addAll(settingBean.getReturn());
        }
        NoScrollLinearLayoutManager linearLayoutManager = new NoScrollLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SettingAdapter(this, data);
        recyclerView.setAdapter(adapter);

        getPresenter().getSettingData();
    }

    @OnClick({R.id.log_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_out:
                UserPathUtils.commitUserPath(12);
                LogOutDialog dialog = new LogOutDialog(SettingActivity.this);
                dialog.setOnSureClickListener(new LogOutDialog.OnSureClickListener() {
                    @Override
                    public void onClick() {
                        // TODO 退出登录
                        SharedPrefHelper.getInstance().setToken("");
                        SharedPrefHelper.getInstance().setUserId("");
                        SharedPrefHelper.getInstance().setUserName("");
                        SharedPrefHelper.getInstance().setPhoneNumber("");
//                        SharedPrefHelper.getInstance().setTime(0);
//                        Constants.TIME_FIVE = 0;
                        Constants.IS_CHOOSE_FIRST = true;
                        finish();
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void getSetSuccess(SettingBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;

            SharedPrefHelper.getInstance().setSettingData(gson.toJson(bean));
            data.clear();
            data.addAll(bean.getReturn());
            adapter.notifyDataSetChanged();
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenter();
    }
}
