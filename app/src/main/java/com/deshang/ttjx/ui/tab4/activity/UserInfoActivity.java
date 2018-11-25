package com.deshang.ttjx.ui.tab4.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.widget.CircleImageView;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.login.activity.BindPhoneActivity;
import com.deshang.ttjx.ui.tab4.persenter.UserInfoPresenter;
import com.deshang.ttjx.ui.tab4.view.UserInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by L on 2018/6/19.
 */

public class UserInfoActivity extends MvpSimpleActivity<UserInfoView, UserInfoPresenter> implements UserInfoView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.header)
    CircleImageView header;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.wechat_name)
    TextView wechat_name;
    @BindView(R.id.image_phone)
    ImageView imagePhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private boolean havePhone = false;

    @Override
    public void getUserInfoSuccess(BaseResponse bean) {

    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        titleBar.setTitle("用户信息");
        titleBar.setBack(true);

        GlideLoading.getInstance().loadImgUrlNyImgLoader(this, SharedPrefHelper.getInstance().getAvatar(), header);
        userName.setText(SharedPrefHelper.getInstance().getUserName());
        wechat_name.setText("已绑定(" + SharedPrefHelper.getInstance().getUserName() + ")");
    }

    @OnClick({R.id.bind_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_phone:
                if (!havePhone) {
                    UIManager.turnToAct(UserInfoActivity.this, BindPhoneActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefHelper.getInstance().getPhoneNumber() == null || "".equals(SharedPrefHelper.getInstance().getPhoneNumber())) {
            imagePhone.setVisibility(View.VISIBLE);
            havePhone = false;
        } else {
            imagePhone.setVisibility(View.GONE);
            tvPhone.setText(SharedPrefHelper.getInstance().getPhoneNumber());
            havePhone = true;
        }
    }

    @Override
    public UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }
}
