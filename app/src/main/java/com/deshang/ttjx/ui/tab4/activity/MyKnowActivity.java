package com.deshang.ttjx.ui.tab4.activity;

import android.view.View;
import android.widget.ImageView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import butterknife.BindView;


public class MyKnowActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar title_bar;
    @BindView(R.id.iv_away)
    ImageView ivAway;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_know);
    }

    @Override
    public void initView() {
        title_bar.setBack(true);
        title_bar.setTitle("");
        ivAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.turn_to_other_tab = 2;
                finish();
            }
        });
    }
}
