package com.deshang.ttjx.ui.tab1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * 正常的ViewPager+Fragment的适配器
 * Created by L on 2018/1/26.
 */

public class NormalFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager manager;
    private FragmentTransaction mCurTransaction;
    private List<Fragment> list;
    private List<String> nameList;

    public NormalFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> nameList) {
        super(fm);
        this.list = list;
        this.nameList = nameList;
        manager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);//显示第几个页面
    }

    @Override
    public int getCount() {
        return list.size();//有几个页面
    }
}
