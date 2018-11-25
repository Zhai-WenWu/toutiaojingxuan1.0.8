package com.deshang.ttjx.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.ui.login.activity.NewLoginActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab1.activity.MessageActivity;
import com.deshang.ttjx.ui.tab1.bean.NewsTypeBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;
import com.deshang.ttjx.ui.tab1.fragment.NewsFragment;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;

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

import rx.Observable;
import rx.Subscriber;

/**
 * Tab1模块
 * 首页
 * Created by L on 2016/5/18.
 */
public class Tab1_1Fragment extends Fragment implements View.OnClickListener {

    TextView time;
    ViewPager viewPager;
    MagicIndicator magicIndicator;
    ImageView message;

    private List<Fragment> list;
    private List<String> nameList;
    private View view;

    {
        nameList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_tab1_1, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initView() {

        time = (TextView) view.findViewById(R.id.time);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        message = (ImageView) view.findViewById(R.id.message);
        message.setOnClickListener(this);
        magicIndicator = (MagicIndicator) view.findViewById(R.id.mIndicator);
        list = new ArrayList<>();
        adapter = new NormalFragmentPagerAdapter(getChildFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.d("滑动选中的position：" + position);
                if (position == 0) {
                    ((NewsFragment) list.get(position)).getNews();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getTypeData();
    }

    private CommonNavigator navigator;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initViewPager() {
        navigator = new CommonNavigator(getActivity());
        navigator.setEnablePivotScroll(true);
        navigator.setIndicatorOnTop(false);
        navigator.setSkimOver(true);

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
                clipPagerTitleView.setClipColor(getResources().getColor(R.color.tab_bg));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                        if (index == 0) {
                            if (adapter != null && adapter.getCurrentFragment() != null)
                                adapter.getCurrentFragment().getNews();
                        }
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(ProjectUtils.dp2px(context, 0f));
                indicator.setLineWidth(ProjectUtils.dp2px(context, 15f));
                indicator.setRoundRadius(ProjectUtils.dp2px(context, 2f));
                indicator.setYOffset(ProjectUtils.dp2px(context, 0.5f));
                indicator.setColors(getResources().getColor(R.color.tab_bg));
                return indicator;
            }
        };
        navigator.setAdapter(navigatorAdapter);
        magicIndicator.setNavigator(navigator);

        ViewPagerHelper.bind(magicIndicator, viewPager);

    }


    private NormalFragmentPagerAdapter adapter;

    protected static final String FRAGMENT_TAG = "android:switcher:" + R.id.view_pager + ":";

    /**
     * 获取新闻分类
     */
    public void getTypeData() {
        Observable request = RetrofitUtils.getInstance().getNewsType();
        Subscriber<NewsTypeBean> upDataSubscriber = new Subscriber<NewsTypeBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final NewsTypeBean bean) {
                LogUtils.d("获取首页新闻分类");
                if (bean.getErrcode() == 0) {
                    if (bean.getData() != null) {
                        nameList.clear();
                        list.clear();
                        for (int i = 0; i < bean.getData().size(); i++) {
                            nameList.add(bean.getData().get(i).getName());
                            NewsFragment newsFragment = (NewsFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG + i);
                            if (newsFragment == null) {
                                list.add(NewsFragment.getInstance(bean.getData().get(i).getId() + "", bean.getData().get(i).getName(),
                                        bean.getData().get(i).getSdk1(), bean.getData().get(i).getSdk2(), bean.getData().get(i).getSdk3() == 1));
                            } else {
                                list.add(newsFragment);
                            }
                        }
                        viewPager.removeAllViews();
                        viewPager.setAdapter(adapter);
                        initViewPager();
                    }
                } else {
                    showToast(bean.getErrinfo());
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 提交登录状态
     */
    public void commitLoginState() {
        Observable request = RetrofitUtils.getInstance().commitLoginState();
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final BaseBean bean) {
                if (bean == null)
                    return;

                if (bean.errcode == 0) {
                    // 当日第一次登录
                    Constants.CAN_RECEIVE = true;
                    SharedPrefHelper.getInstance().setTime(0);
                    Constants.TIME_FIVE = 0;
                    // 每日首次登
                    // 陆弹出窗
                    /*if (SharedPrefHelper.getInstance().getOnLine() && !SharedPrefHelper.getInstance().getNewUser()) {
                        // 审核过了以后再弹窗
                        DayShowDialog dayShowDialog = new DayShowDialog(getActivity());
                        dayShowDialog.setOnClickListener(new DayShowDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                getShareUrl();
                            }
                        });
                        dayShowDialog.show();
                    }*/
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 获取分享链接
     */
    public void getShareUrl() {
        Observable request = RetrofitUtils.getInstance().getShareUrl();
        Subscriber<ShareUrlBean> upDataSubscriber = new Subscriber<ShareUrlBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final ShareUrlBean bean) {
                if (bean.getErrcode() == 0) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    if (bean.getReturn() == null) {
                        intent.putExtra("Url", "1");
                    } else {
                        int index = bean.getReturn().getUrl().indexOf("id/");
                        intent.putExtra("Url", bean.getReturn().getUrl().substring(index + 3, bean.getReturn().getUrl().length()));
                    }
                    startActivity(intent);
                } else {
                    showToast(bean.getErrinf());
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    private void showToast(String str) {
        ReceiveGoldToast.makeToast(getActivity(), str).show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            commitLoginState();
            if (adapter != null && adapter.getCurrentFragment() != null)
                adapter.getCurrentFragment().getNews();
        }
    }

    // 处在新闻tab时，又点击首页tab后刷新当前页面的新闻数据
    public void refreshNews() {
        if (adapter != null && adapter.getCurrentFragment() != null)
            adapter.getCurrentFragment().getNews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message:
//                if (true) {
//                    UIManager.turnToAct(getActivity(), BannerActivity.class);
//                    break;
//                }
                if ((SharedPrefHelper.getInstance().getToken() == null || "".equals(SharedPrefHelper.getInstance().getToken()))) {
                    // 游客
                    Intent intent = new Intent(getActivity(), NewLoginActivity.class);
                    startActivity(intent);
                } else {
                    UIManager.turnToAct(getActivity(), MessageActivity.class);
                }
                break;
        }
    }

    class NormalFragmentPagerAdapter extends FragmentPagerAdapter {

        private NewsFragment fragment;

        private NormalFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return nameList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            fragment = ((NewsFragment) object);
            super.setPrimaryItem(container, position, object);
        }

        public NewsFragment getCurrentFragment() {
            return fragment;
        }
    }
}
