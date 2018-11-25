package com.deshang.ttjx.ui.main.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.BannerImageLoader;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.CircleImageView;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.presenter.Tab4Presenter;
import com.deshang.ttjx.ui.main.view.Tab4View;
import com.deshang.ttjx.ui.mywidget.Tab4NewUserPop;
import com.deshang.ttjx.ui.tab3.activity.MyApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.MyActivity;
import com.deshang.ttjx.ui.tab4.activity.MyGroupActivity;
import com.deshang.ttjx.ui.tab4.activity.MyKnowActivity;
import com.deshang.ttjx.ui.tab4.activity.MyServiceActivity;
import com.deshang.ttjx.ui.tab4.activity.MyWhiteBookActivity;
import com.deshang.ttjx.ui.tab4.activity.NormalQuestionActivity;
//import com.deshang.ttjx.ui.tab4.activity.RewardVideoActivity;
import com.deshang.ttjx.ui.tab4.activity.SaleTBActivity;
import com.deshang.ttjx.ui.tab4.activity.SettingActivity;
import com.deshang.ttjx.ui.tab4.bean.MeBean;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import com.deshang.ttjx.ui.mywidget.dialog.MePopwindow;

/**
 * Created by L on 2016/5/18.
 */
public class Tab4Fragment extends MvpSimpleFragment<Tab4View, Tab4Presenter> implements Tab4View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.header_image)
    CircleImageView headerImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_tel)
    TextView user_tel;
    @BindView(R.id.balance_dollar)
    TickerView balanceDollar; // 账户余额
    @BindView(R.id.balance_money)
    TextView balanceMoney; // 参与分红
    @BindView(R.id.ll_withdrawal)
    LinearLayout llWithdrawal;
    @BindView(R.id.ll_disciple)
    LinearLayout ll_disciple;
    @BindView(R.id.ll_book)
    LinearLayout llBook;
    @BindView(R.id.ll_gold)
    LinearLayout llGold;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;
    @BindView(R.id.total_dollar)
    TextView totalDollar;
    @BindView(R.id.wite_draw)
    TextView witeDraw;
    @BindView(R.id.textView2)
    TextView prices;
    @BindView(R.id.ll_balance)
    LinearLayout ll_balance;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.ll_a)
    LinearLayout ll_a;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.tv_problem)
    TextView tvProblem;
    @BindView(R.id.tv_csd)
    TextView tvCsd;
    //    @BindView(R.id.tv_dividend_balance)
//    TickerView dividendBalance;
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.tv_book)
    TextView tvbook;
    @BindView(R.id.tv_disciple)
    TextView tvDisciple;
    @BindView(R.id.tv_total)
    TextView tvTotal;


    @BindView(R.id.image)
    ImageView image;

    private List<String> images;
    private MeBean meBean;

    {
        images = new ArrayList<>();
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.f_tab4);
    }

    @Override
    public void initView(View v) {
        //设置字体
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "arial_narrow.ttf");
//        dividendBalance.setTypeface(typeface);
//        dividendBalance.setCharacterList(TickerUtils.getDefaultListForUSCurrency());//可以有小数点，"/"字符
//        dividendBalance.setAnimationInterpolator(new DecelerateInterpolator());
        balanceDollar.setTypeface(typeface);
        balanceDollar.setCharacterList(TickerUtils.getDefaultListForUSCurrency());//可以有小数点，"/"字符
        balanceDollar.setAnimationInterpolator(new DecelerateInterpolator());
        balanceDollar.setText("0.0000");
        //totalDollar.setTypeface(typeface);
        balanceMoney.setTypeface(typeface);
//        balanceMoney.getPaint().setFakeBoldText(true);
        witeDraw.setTypeface(typeface);
//        witeDraw.getPaint().setFakeBoldText(true);

        rlBanner.setVisibility(View.GONE);
        GlideLoading.getInstance().loadImageHeader(getActivity(), SharedPrefHelper.getInstance().getAvatar(), headerImage);
        if ("".equals(SharedPrefHelper.getInstance().getUserName())) {
            userName.setText("用户" + SharedPrefHelper.getInstance().getUserId());
        } else {
            userName.setText(SharedPrefHelper.getInstance().getUserName());
        }
        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new BannerImageLoader());
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);

        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(3000);
        //设置点击事件，下标是从1开始
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //设置点击事件
                UserPathUtils.commitUserPath(30);
                UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
            }
        });
        banner.startAutoPlay();

        getPresenter().getMeInfo();
//        setTranslucentStatus(R.color.green_tab4);
        if (SharedPrefHelper.getInstance().getOnLine()) {
            setUI(View.VISIBLE);
        } else {
            setUI(View.GONE);
        }

        ViewTreeObserver vto = ll_a.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (SharedPrefHelper.getInstance().getFirstTimeTab4()) {
                    int[] location = new int[2];
                    witeDraw.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    LogUtils.d("x:" + x + " y:" + y);
                    if (Build.VERSION.SDK_INT >= 16) {
                        ll_a.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        ll_a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    Tab4NewUserPop pop = new Tab4NewUserPop(getActivity());
                    pop.setOnClickListener(new Tab4NewUserPop.OnClickListener() {
                        @Override
                        public void onClick() {
                            //((MainActivity) getActivity()).switchFragment(2);
                            Intent intent = new Intent(getActivity(), MyKnowActivity.class);
                            startActivity(intent);
                        }
                    });
                    pop.showAtLocation(main, Gravity.NO_GRAVITY, x, y + witeDraw.getHeight());
                    SharedPrefHelper.getInstance().setFirstTimeTab4(false);
                }
            }
        });
    }

    @OnClick({R.id.ll_book, R.id.ll_withdrawal, R.id.ll_disciple, R.id.ll_question, R.id.header_image, R.id.ll_service, R.id.iv_maichu, R.id.iv_right, R.id.iv_back, R.id.iv_draw, R.id.ll_grounp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_book:
                UserPathUtils.commitUserPath(6);
               /* Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
                if (meBean != null && meBean.getReturn() != null) {
                    intent.putExtra("allMoney", meBean.getReturn().getTotal());
                    intent.putExtra("balanceDollar", meBean.getReturn().getTotal());
                }
                startActivity(intent);*/
                UIManager.turnToAct(getActivity(), MyWhiteBookActivity.class);
                break;

            case R.id.ll_withdrawal:
                UserPathUtils.commitUserPath(7);
                Intent intent1 = new Intent(getActivity(), MyActivity.class);
                startActivity(intent1);
                break;
            //我的收徒
            case R.id.ll_disciple:
                UserPathUtils.commitUserPath(15);
                UIManager.turnToAct(getActivity(), MyApprenticeActivity.class);
                break;

            case R.id.ll_question:
                UserPathUtils.commitUserPath(8);
                UIManager.turnToAct(getActivity(), NormalQuestionActivity.class);
                break;

            case R.id.header_image:
//                UIManager.turnToAct(getActivity(), UserInfoActivity.class);
                break;

            case R.id.ll_service:
              /*  UserPathUtils.commitUserPath(9);*/
                UIManager.turnToAct(getActivity(), MyServiceActivity.class);
                //   UIManager.turnToAct(getActivity(), RewardVideoActivity.class);
                break;

            case R.id.iv_maichu:
                UserPathUtils.commitUserPath(10);
                UIManager.turnToAct(getActivity(), SaleTBActivity.class);
                break;
            case R.id.iv_right:
                UserPathUtils.commitUserPath(11);
                UIManager.turnToAct(getActivity(), SettingActivity.class);
                break;
            case R.id.iv_draw:
                if (Double.valueOf(witeDraw.getText().toString().trim()) > 0) {
                    // >0才去请求接口
                    getPresenter().getMeBonus();
                } else {
                    showToast("暂无分红可领");
                }
                break;
            case R.id.ll_grounp:
                UIManager.turnToAct(getActivity(), MyGroupActivity.class);
                break;
        }
    }

    // 是否显示账户等信息
    private void setUI(int state) {
        llWithdrawal.setVisibility(state);
        ll_disciple.setVisibility(state);
        llGold.setVisibility(state);
        image.setVisibility(state);
        ll_balance.setVisibility(state);
//        llBook.setVisibility(state);
        rlBanner.setVisibility(state);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (SharedPrefHelper.getInstance().getOnLine()) {
                setUI(View.VISIBLE);
            } else {
                setUI(View.GONE);
            }
            banner.startAutoPlay();
            getPresenter().getMeInfo();
        } else {
            banner.stopAutoPlay();
        }
    }

    @Override
    public Tab4Presenter createPresenter() {
        return new Tab4Presenter();
    }

    @Override
    public void getMeInfoSucc(MeBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null) {
                meBean = bean;
                SharedPrefHelper.getInstance().setUserName(bean.getReturn().getNickname());
                SharedPrefHelper.getInstance().setAvatar(bean.getReturn().getThumb());
                GlideLoading.getInstance().loadImgUrlNyImgLoader(getActivity(), bean.getReturn().getThumb(), headerImage);
                userName.setText(bean.getReturn().getNickname());
//                allMoney.setText("总收益：" + bean.getReturn().getTotal() + "");
//                String phoneNumber = SharedPrefHelper.getInstance().getPhoneNumber();
                user_tel.setText(SharedPrefHelper.getInstance().getPhoneNumber());
                balanceDollar.setText(bean.getReturn().getTotal());
                balanceMoney.setText(String.valueOf(bean.getReturn().getBonus()));

                witeDraw.setText(bean.getReturn().getReceive_bonus());

                prices.setText("今日回购价格：1红钻 = " + bean.getReturn().getGold_price() + "元");
                tvTotal.setText("账户余额：" + bean.getReturn().getChange() + "元");
                double price = Double.valueOf(bean.getReturn().getGold_price());
                double total = Double.valueOf(bean.getReturn().getTotal());

                totalDollar.setText(ProjectUtils.getTwo(price * total) + "元");

                double change = Double.valueOf(bean.getReturn().getChange());
//                dividendBalance.setText(ProjectUtils.getTwo(change));
                tvVote.setText(bean.getReturn().getMy_vote());
                tvProblem.setText(bean.getReturn().getProblem());
                tvCsd.setText(bean.getReturn().getMy_call());
                tvGroup.setText(bean.getReturn().getMy_group());
                tvbook.setText(bean.getReturn().getWhite());
                tvDisciple.setText(bean.getReturn().getApprentice());

                //自定义图片加载框架
                banner.setImages(images);
                banner.start();
            }
        } else {
            // 失败 没啥卵用
            showToast(bean.getErrinf());
        }
    }

    //分紅接口
    @Override
    public void getMeBonusSucc(BaseBean bean) {
        if (bean.errcode == 0) {
            getPresenter().getMeInfo();
        } else {
            showToast(bean.errinf);
        }
    }
}
