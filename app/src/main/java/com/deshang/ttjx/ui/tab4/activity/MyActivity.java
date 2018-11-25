package com.deshang.ttjx.ui.tab4.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;
import com.deshang.ttjx.ui.tab4.adapter.MyFragmentPagerAdapter;
import com.deshang.ttjx.ui.tab4.fragment.BallotFragmet;
import com.deshang.ttjx.ui.tab4.fragment.VideoVoteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyActivity extends BaseActivity {

    private List<String> titleList;// 标题集合
    private List<Fragment> fragmentList;// 碎片集合
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;// viewPager适配器

    @BindView(R.id.ballot_title)
    TitleBar ballotTitle;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my);
    }

    @Override
    public void initView() {
        ballotTitle.setBack(true);
        ballotTitle.setTitle("我的投票");
        titleList = new ArrayList<>();
        titleList.add("文章投票");
        titleList.add("视频投票");
        fragmentList = new ArrayList<>();
        fragmentList.add(new BallotFragmet());
        fragmentList.add(new VideoVoteFragment());

        // 添加标题
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(0)));
        tabTitle.addTab(tabTitle.newTab().setText(titleList.get(1)));

        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), titleList, fragmentList);
        vp.setAdapter(mMyFragmentPagerAdapter);

        // 绑定viewPager与其联动
        tabTitle.setupWithViewPager(vp);

        // 设置打开应用时当前viewPager是第一个
        vp.setCurrentItem(0);

        tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        fragmentList.get(0);
                        break;
                    case 1:
                        fragmentList.get(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
