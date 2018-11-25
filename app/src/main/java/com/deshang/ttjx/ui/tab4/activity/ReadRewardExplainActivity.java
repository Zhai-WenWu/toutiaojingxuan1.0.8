package com.deshang.ttjx.ui.tab4.activity;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import butterknife.BindView;

/**
 * 阅读奖励说明页面
 * Created by L on 2018/6/8.
 */

public class ReadRewardExplainActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
//    @BindView(R.id.one)
//    TextView tvOne;
//    @BindView(R.id.two)
//    TextView tvTwo;

//    SpannableStringBuilder spannable;
//    private String one = "1.认真阅读文章超过30s，即可获得阅读奖励。";
//    private String two = "2.分享文章到朋友圈或朋友，朋友打开认真阅读即可获得300金币。";

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_read_reward_explain);
    }

    @Override
    public void initView() {
        titleBar.setTitle("投票攻略");
        titleBar.setBack(true);

//        spannable = new SpannableStringBuilder(one);
//        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 10, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        tvOne.setText(spannable);
//
//        spannable = new SpannableStringBuilder(two);
//        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), two.length() - 6, two.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        tvTwo.setText(spannable);
    }
}
