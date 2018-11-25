package com.deshang.ttjx.ui.tab4.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 13364 on 2018/10/27.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
        super(fm);
        this.mTitleList = titleList;
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
