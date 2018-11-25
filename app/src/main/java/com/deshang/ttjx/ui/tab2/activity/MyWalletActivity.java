package com.deshang.ttjx.ui.tab2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.tab1.adapter.NormalFragmentPagerAdapter;
import com.deshang.ttjx.ui.tab2.fragment.ChangeFragment;
import com.deshang.ttjx.ui.tab2.fragment.GoldFragment;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.ShowIncomeActivity;
import com.deshang.ttjx.ui.tab4.activity.WithdrawalActivity;
import com.deshang.ttjx.ui.tab4.persenter.MyWalletPresenter;
import com.deshang.ttjx.ui.tab4.view.MyWalletView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by L on 2017/12/6.
 */

public class MyWalletActivity extends MvpSimpleActivity<MyWalletView, MyWalletPresenter> implements MyWalletView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.gold_num)
    TextView goldNum;
    @BindView(R.id.how_make_money)
    TextView howMakeMoney;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.mIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.withdrawal)
    TextView withdrawal;

    private NormalFragmentPagerAdapter adapter;
    private List<Fragment> list;
    private List<String> nameList;
    private CommonNavigator navigator;

    private boolean isClick=false;

    {
        list = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("金币");
        nameList.add("零钱");
    }

    public void setMoney(String strMoney) {
        money.setText("¥" + strMoney);
    }

    public void setGold(int gold) {
        goldNum.setText("金币：" + gold);
    }

    public void setContent(String strContent) {
        content.setText(strContent);
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_wallet);
    }

    @Override
    public void initView() {
        titleBar.setTitle("我的钱包");
        titleBar.setBack(true);

       if(SharedPrefHelper.getInstance().getIsClick()){
            withdrawal.setText("提现");
        }else {
            withdrawal.setText("开启提现");
        }

        howMakeMoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        list.add(GoldFragment.getInstance());
        list.add(ChangeFragment.getInstance());
        adapter = new NormalFragmentPagerAdapter(getSupportFragmentManager(), list, nameList);
        viewPager.setAdapter(adapter);
        initViewPager();
    }

    @OnClick({R.id.withdrawal, R.id.share, R.id.how_make_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdrawal:
                SharedPrefHelper.getInstance().setIsClick(true);
                UIManager.turnToAct(this, WithdrawalActivity.class);
                break;

            case R.id.share:
                UserPathUtils.commitUserPath(19);
                Intent intent = new Intent(this, ShowIncomeActivity.class);
                intent.putExtra("allMoney", getIntent().getStringExtra("allMoney"));
                startActivity(intent);
                break;

            case R.id.how_make_money:
                UserPathUtils.commitUserPath(20);
                UIManager.turnToAct(this, ApprenticeActivity.class);
                break;
        }
    }

    private void initViewPager() {
        navigator = new CommonNavigator(this);
        navigator.setAdjustMode(true);
        navigator.setEnablePivotScroll(true);
        navigator.setIndicatorOnTop(false);
        navigator.setSkimOver(true);
        navigator.setScrollPivotX(1f);

        CommonNavigatorAdapter navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return nameList == null ? 0 : nameList.size();
            }

            @Override
            public IPagerTitleView getTitleView(final Context context, final int index) {

                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(nameList.get(index));
                clipPagerTitleView.setBackgroundResource(R.color.white_milky);
                clipPagerTitleView.setTextSize(getResources().getDimensionPixelOffset(R.dimen.sp_biger));
                clipPagerTitleView.setTextColor(getResources().getColor(R.color.tv_black));
                clipPagerTitleView.setClipColor(getResources().getColor(R.color.red_light));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(ProjectUtils.dp2px(context, 2f));
                indicator.setLineWidth(ProjectUtils.dp2px(context, 15f));
                indicator.setRoundRadius(ProjectUtils.dp2px(context, 2f));
                indicator.setYOffset(ProjectUtils.dp2px(context, 0.5f));
                indicator.setColors(getResources().getColor(R.color.red_light));
                return indicator;
            }
        };
        navigator.setAdapter(navigatorAdapter);
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void getDataSucc(BaseBean bean) {

    }

    @Override
    public MyWalletPresenter createPresenter() {
        return new MyWalletPresenter();
    }
}
