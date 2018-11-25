package com.deshang.ttjx.ui.main.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.base.SystemBarTintManager;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.utils.BannerImageLoader;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.NoSlidingListView;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.main.presenter.Tab2Presenter;
import com.deshang.ttjx.ui.main.view.Tab2View;
import com.deshang.ttjx.ui.mywidget.scroll_text.AutoScrollTextView;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab2.adapter.MakeMoneyAdapter;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;
import com.deshang.ttjx.ui.tab2.activity.MyWalletActivity;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.ShowIncomeActivity;
import com.deshang.ttjx.ui.tab4.activity.WithdrawalActivity;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 赚钱Tab
 * Created by L on 2016/5/18.
 */
public class Tab2Fragment extends MvpSimpleFragment<Tab2View, Tab2Presenter> implements Tab2View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.auto_scroll_tv)
    AutoScrollTextView auto_scroll_tv;
    @BindView(R.id.all_money)
    TextView allMoney;
    @BindView(R.id.list_view)
    NoSlidingListView listView;

    private MakeMoneyAdapter listAdapter;
    private List<MakeMoneyBean.DataBeanX.TaskBean> data;
    private ArrayList<String> autoStringList;
    private MakeMoneyBean makeMoneyBean;

    private List<String> images; // banner

    private MainActivity parentActivity;

//    private String allMyMoney; // 我的赚的钱数

    {
        images = new ArrayList<>();
        data = new ArrayList<>();
        autoStringList = new ArrayList<>();
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.f_tab2);
    }

    @Override
    public void initView(View v) {
        listAdapter = new MakeMoneyAdapter(getActivity());
        listAdapter.setOnReceiveClickListener(new MakeMoneyAdapter.OnReceiveClickListener() {
            @Override
            public void onClick(String url) {
                // TODO 领任务金币
                receiveGold(url);
            }
        });

        listAdapter.SetOnClickButtonListener(new MakeMoneyAdapter.OnClickButtonListener() {
            @Override
            public void onClick(int position, String link) {
                LogUtils.d("link:" + link);
                if (link == null)
                    return;
                switch (link) {
                    case "立即收徒":
                        UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                        break;

                    case "去提现":
                        Intent intent1 = new Intent(getActivity(), WithdrawalActivity.class);
                        startActivity(intent1);
                        break;

                    case "首页":
                        parentActivity = (MainActivity) getActivity();
                        parentActivity.switchFragment(0);
                        break;

                    case "去晒晒":
                        Intent intent = new Intent(getActivity(), ShowIncomeActivity.class);
                        if (makeMoneyBean != null && makeMoneyBean.getData() != null)
                            intent.putExtra("allMoney", makeMoneyBean.getData().getTotal_income());
//                            intent.putExtra("allMoney", ProjectUtils.calculateProfit(Double.valueOf(makeMoneyBean.getData().getTotal_income())));
                        startActivity(intent);
                        break;
                }
            }
        });
        listView.setAdapter(listAdapter);
        listView.setFocusable(false);

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
                getPresenter().statsUserClick();
                UserPathUtils.commitUserPath(29);
                UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
            }
        });
        auto_scroll_tv.startAutoScroll();
        banner.startAutoPlay();
        getPresenter().getData();
        setTranslucentStatus(R.color.pink_tab2);
    }

    @Override
    public void getDataSucc(MakeMoneyBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getData() == null)
                return;

            makeMoneyBean = bean;
            // 文字轮播
            if (bean.getData().getVerb() != null && bean.getData().getVerb().size() > 0) {
                autoStringList.clear();
                for (int i = 0; i < bean.getData().getVerb().size(); i++) {
                    autoStringList.add(bean.getData().getVerb().get(i).getTitle());
                }
                auto_scroll_tv.setTextList(autoStringList);
            }

            // 图片轮播
            if (bean.getData().getBanner() != null && bean.getData().getBanner().size() > 0) {
                images.clear();
                for (int i = 0; i < bean.getData().getBanner().size(); i++) {
                    images.add(bean.getData().getBanner().get(i).getImg());
                }
                banner.setImages(images);
                banner.start();
            }

            String money = bean.getData().getTotalgold();

            if (money.length() >= 5) {
                allMoney.setText(money.substring(0, money.length() - 5));
            }

            // 任务轮播
            if (bean.getData().getTask() != null && bean.getData().getTask().size() > 0) {
                data.clear();
                data.addAll(bean.getData().getTask());
                listAdapter.setData(data);
            }
        } else {
            // 失败
            showToast(bean.getErrinfo());
        }
    }

    @Override
    public void statsUserClick() {

    }

    @OnClick({R.id.how_make_money, R.id.wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.how_make_money:
                UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                break;

            case R.id.wallet:
                Intent intent = new Intent(getActivity(), MyWalletActivity.class);
                if (makeMoneyBean != null && makeMoneyBean.getData() != null)
                    intent.putExtra("allMoney", makeMoneyBean.getData().getTotal_income());
                startActivity(intent);
                break;
        }
    }

    // 领取任务
    private void receiveGold(String url) {
        LogUtils.d("领金币链接：" + url);
        if (url == null || url.trim().length() == 0)
            return;

        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("领金币失败：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    LogUtils.d("领金币返回数据：" + str);
                    Gson gson = new Gson();
                    final ReceiveRedPacketBean bean = gson.fromJson(str, ReceiveRedPacketBean.class);
                    if (bean == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getErrcode() == 0) {
                                getPresenter().getData();
                                ReceiveGoldToast.showGoldToast(getActivity(), bean.getReturn().getGold() + "金币");
                            } else {
                                showToast(bean.getErrinf());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setTranslucentStatus(R.color.pink_tab2);
            auto_scroll_tv.startAutoScroll();
            banner.startAutoPlay();
            getPresenter().getData();
        } else {
            setTranslucentStatus(R.color.black);
            auto_scroll_tv.stopAutoScroll();
            banner.stopAutoPlay();
        }
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getActivity().getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorResId);// 状态栏无背景
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Tab2Presenter createPresenter() {
        return new Tab2Presenter();
    }
}
