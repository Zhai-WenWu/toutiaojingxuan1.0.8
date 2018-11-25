package com.deshang.ttjx.ui.tab3.activity;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import butterknife.BindView;

/**
 * 如何收徒
 * Created by L on 2018/7/9.
 */

public class HowApprenticeActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_how_apprentice);
    }

    @Override
    public void initView() {
        titleBar.setTitle("如何收徒");
        titleBar.setBack(true);
    }
}
