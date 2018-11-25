package com.deshang.ttjx.ui.tab3.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;
import com.deshang.ttjx.ui.tab3.fragment.MyApprenticeFragment;
import com.deshang.ttjx.ui.tab3.fragment.MyDiscipleFragment;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的徒弟
 * Created by L on 2017/12/6.
 */

public class MyApprenticeActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.my_apprentice_num)
    TextView myApprenticeNum;
    @BindView(R.id.money_num)
    TextView moneyNum;
    @BindView(R.id.my_apprentice_money)
    TextView myApprenticeMoney;
    @BindView(R.id.frame)
    FrameLayout frameLayout;
    @BindView(R.id.my_apprentice)
    TextView myApprentice;
    @BindView(R.id.app_view)
    View app_view;
    @BindView(R.id.my_disciple)
    TextView myDisciple;
    @BindView(R.id.dis_view)
    View dis_view;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private MyApprenticeFragment apprenticeFragment;
    private MyDiscipleFragment discipleFragment;

    // 我的徒弟
    public void setApprenticeNum(String strMoney) {
        myApprenticeNum.setText(strMoney);
    }

    // 收徒奖励收益
    public void setMoneyNum(String gold) {
        moneyNum.setText(gold);
    }

    // 我的徒弟进贡收益
    public void setMyApprenticeMoney(String strContent) {
        myApprenticeMoney.setText(strContent);
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_apprentice);
    }

    @Override
    public void initView() {
        titleBar.setTitle("我的徒弟");
        titleBar.setBack(true);

        apprenticeFragment = MyApprenticeFragment.getInstance();
        discipleFragment = MyDiscipleFragment.getInstance();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame, apprenticeFragment);
        ft.commit();
    }

    @OnClick({R.id.my_apprentice, R.id.my_disciple})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_apprentice:
                if (apprenticeFragment.isVisible()) {
                    return;
                }
                titleBar.setTitle("我的徒弟");
//                moneyNum.setVisibility(View.VISIBLE);
                myApprentice.setTextColor(getResources().getColor(R.color.tab_bg));
                app_view.setVisibility(View.VISIBLE);
                myDisciple.setTextColor(getResources().getColor(R.color.tv_black));
                dis_view.setVisibility(View.GONE);
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, apprenticeFragment);
                ft.commit();
                break;

            case R.id.my_disciple:
                if (discipleFragment.isVisible()) {
                    return;
                }
                titleBar.setTitle("我的徒孙");
//                moneyNum.setVisibility(View.INVISIBLE);
                moneyNum.setText("");
                myApprentice.setTextColor(getResources().getColor(R.color.tv_black));
                app_view.setVisibility(View.GONE);
                myDisciple.setTextColor(getResources().getColor(R.color.tab_bg));
                dis_view.setVisibility(View.VISIBLE);
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, discipleFragment);
                ft.commit();
                break;
        }
    }
}
