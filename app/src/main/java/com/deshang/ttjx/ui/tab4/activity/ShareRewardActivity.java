package com.deshang.ttjx.ui.tab4.activity;

import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文章详情/分享赚钱
 * Created by L on 2018/7/5.
 */

public class ShareRewardActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_share_reward);
    }

    @Override
    public void initView() {
        titleBar.setTitle("分享赚钱");
        titleBar.setBack(true);
        UserPathUtils.commitUserPath(27);
    }

    @OnClick(R.id.go_apprentice)
    public void onClick(View view) {
        UIManager.turnToAct(this, ApprenticeActivity.class);
    }

}
