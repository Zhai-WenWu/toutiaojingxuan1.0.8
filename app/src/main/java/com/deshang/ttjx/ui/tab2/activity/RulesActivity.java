package com.deshang.ttjx.ui.tab2.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;
import com.deshang.ttjx.ui.tab2.bean.RulesBean;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class RulesActivity extends BaseActivity {

    @BindView(R.id.title_g)
    TitleBar titleBar;
    @BindView(R.id.yesterday_mining)
    TextView yesterday_mining;
    /* @BindView(R.id.yesterday_build)
     TextView yesterday_build;*/
    @BindView(R.id.frozen)
    TextView frozen;
    @BindView(R.id.mining)
    TextView mining;
    @BindView(R.id.build)
    TextView build;
    @BindView(R.id.ip_invest)
    TextView ip_invest;
    @BindView(R.id.team)
    TextView team;
    @BindView(R.id.user_total)
    TextView user_total;
    @BindView(R.id.article_total)
    TextView article_total;
    @BindView(R.id.yesterday_bonus)
    TextView yesterday_bonus;
    @BindView(R.id.bonus)
    TextView bonus;
//    @BindView(R.id.bottom)
//    ImageView bottom;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_rules);
    }

    @Override
    public void initView() {
//        GlideLoading.getInstance().loadImageForResources(this, R.mipmap.tb_rules_bottom, bottom);
        titleBar.setBack(true);
        titleBar.setTitle("规则介绍");
        getRules();
    }

    @OnClick({R.id.back})
    public void onClick(){
        finish();
    }

    // 获取数据
    private void getRules() {
        showProgressDialog();
        Observable request = RetrofitUtils.getInstance().rulesTB();
        Subscriber<RulesBean> subscriber = new Subscriber<RulesBean>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
            }

            @Override
            public void onNext(RulesBean bean) {
                if (bean.getErrcode() == 0) {
                    if (bean.getReturn() == null)
                        return;

                    yesterday_mining.setText(bean.getReturn().getYesterday_mining());
                    //yesterday_build.setText(bean.getReturn().getYesterday_build());
                    yesterday_bonus.setText(bean.getReturn().getYesterday_bonus());
                    frozen.setText(bean.getReturn().getFrozen());
                    mining.setText(bean.getReturn().getMining());
                    build.setText(bean.getReturn().getBuild());
                    ip_invest.setText(bean.getReturn().getIp_invest());
                    team.setText(bean.getReturn().getTeam());
                    bonus.setText(bean.getReturn().getBonus());
                    user_total.setText(String.valueOf(bean.getReturn().getUser_total()));
                    article_total.setText(String.valueOf(bean.getReturn().getArticle_total()));
                } else {
                    showToast(bean.getErrinf());
                }
            }
        };
        request.subscribe(subscriber);
    }

}
