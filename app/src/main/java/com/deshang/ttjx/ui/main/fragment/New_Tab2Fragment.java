package com.deshang.ttjx.ui.main.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.utils.BannerImageLoader;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.NoSlidingListView;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.presenter.NewTab2Presenter;
import com.deshang.ttjx.ui.main.view.NewTab2View;
import com.deshang.ttjx.ui.mywidget.BubbleContainer;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab2.activity.RulesActivity;
import com.deshang.ttjx.ui.tab2.adapter.TaskAdapter;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyNewBean;
import com.deshang.ttjx.ui.tab4.activity.AccountInfoActivity;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;
import com.deshang.ttjx.ui.tab4.activity.SaleTBActivity;
import com.deshang.ttjx.ui.tab4.activity.ShowIncomeActivity;
import com.google.gson.Gson;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
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
public class New_Tab2Fragment extends MvpSimpleFragment<NewTab2View, NewTab2Presenter> implements NewTab2View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.user_task)
    NoSlidingListView userTask;
    @BindView(R.id.day_task)
    NoSlidingListView dayTask;
    @BindView(R.id.image_earth)
    ImageView earth;
    @BindView(R.id.container)
    BubbleContainer container;
    @BindView(R.id.diamond_num)
    TickerView diamondNum; // 红钻总数
    @BindView(R.id.detail)
    TextView detail; // 今日总数
    @BindView(R.id.sale)
    TextView sale; // 今日币价
    @BindView(R.id.user_title)
    TextView userTitle;
    @BindView(R.id.day_title)
    TextView dayTitle;
    @BindView(R.id.tv_title_mark)
    TextView tv_title_mark;
    @BindView(R.id.tv_title_mark1)
    TextView tv_title_mark1;
    @BindView(R.id.scroll_text)
    TextView scrollText;
    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    @BindView(R.id.user_view)
    View userView;
    @BindView(R.id.add_diamond_num)
    TextView add_diamond_num;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.ll_load)
    LinearLayout ll_load;
    @BindView(R.id.commit)
    TextView commit;
//    @BindView(R.id.tv_banner)
//    TextBannerView tvBanner;


    private SoundPool soundPool;//声明一个SoundPool
    private int soundID;//创建某个声音对应的音频ID

    private int bubbleSize = 0;
    private List<BubbleBean.ReturnBean> bubbleData;
    private int bubbleWidth, bubbleHeight, bgWidth, bgHeight;
    private List<String> images; // banner

    private List<MakeMoneyNewBean.DataBeanX.DataBean> userData;
    private List<MakeMoneyNewBean.DataBeanX.DataBean> dayData;
    private TaskAdapter userAdapter, dayAdapter;
    private double myTBNum; // 我的TB总和

//    private ArrayList<String> autoStringList; // 文字轮播数据源

    {
        images = new ArrayList<>();
        bubbleData = new ArrayList<>();
        bubbleViews=new ArrayList<>();
        userData = new ArrayList<>();
        dayData = new ArrayList<>();
//        autoStringList = new ArrayList<>();
//        autoStringList.add("阅读分享文章可获得更多红钻");
    }

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.f_new_tab2);
    }

    @Override
    public void initView(View v) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "arial_narrow.ttf");
        diamondNum.setTypeface(typeface);
        // scrollNumber.setCharacterList(TickerUtils.getDefaultNumberList());//纯数字
        diamondNum.setCharacterList(TickerUtils.getDefaultListForUSCurrency());//可以有小数点，"/"字符
        diamondNum.setAnimationInterpolator(new DecelerateInterpolator());
        add_diamond_num.setTypeface(typeface);
        add_diamond_num.setText("0.0000");

        Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wallet_bubble_bg1);
        bubbleWidth = bg.getWidth();
        bubbleHeight = bg.getHeight();
        ViewTreeObserver vto = container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                bgWidth = container.getWidth();
                bgHeight = container.getHeight();
            }
        });

        userAdapter = new TaskAdapter(getActivity(), userData);
        userAdapter.setOnClickToFinishListener(new TaskAdapter.OnClickToFinishListener() {
            @Override
            public void onToFinishClick(String title) {
                if (title.contains("收徒")) {
                    UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                } else if (title.contains("视频")) {
                    ((MainActivity) getActivity()).switchFragment(1);
                } else if (title.contains("邀请")) {
                    UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                } else if (title.contains("分享")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                } else if (title.contains("提现")) {
                    UIManager.turnToAct(getActivity(), SaleTBActivity.class);
                } else if (title.contains("投票")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                } else if (title.contains("晒")) {
                    UIManager.turnToAct(getActivity(), ShowIncomeActivity.class);
                } else if (title.contains("阅读")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                }
            }
        });
        userAdapter.setOnReceiveClickListener(new TaskAdapter.OnReceiveClickListener() {
            @Override
            public void onClick(String url) {
                receiveGold(url);
            }
        });
        userTask.setAdapter(userAdapter);
        userTask.setFocusable(false);

        dayAdapter = new TaskAdapter(getActivity(), dayData);
        dayAdapter.setOnClickToFinishListener(new TaskAdapter.OnClickToFinishListener() {
            @Override
            public void onToFinishClick(String title) {
                if (title.contains("收徒")) {
                    UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                } else if (title.contains("视频")) {
                    ((MainActivity) getActivity()).switchFragment(1);
                } else if (title.contains("邀请")) {
                    UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                } else if (title.contains("分享")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                } else if (title.contains("提现")) {
                    UIManager.turnToAct(getActivity(), SaleTBActivity.class);
                } else if (title.contains("投票")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                } else if (title.contains("晒")) {
                    UIManager.turnToAct(getActivity(), ShowIncomeActivity.class);
                } else if (title.contains("阅读")) {
                    ((MainActivity) getActivity()).switchFragment(0);
                }
            }
        });
        dayAdapter.setOnReceiveClickListener(new TaskAdapter.OnReceiveClickListener() {
            @Override
            public void onClick(String url) {
                receiveGold(url);
            }
        });
        dayTask.setAdapter(dayAdapter);
        dayTask.setFocusable(false);

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
                if (position == 1) {
                    UIManager.turnToAct(getActivity(), RulesActivity.class);
                } else {
                    UIManager.turnToAct(getActivity(), ApprenticeActivity.class);
                }
            }
        });

        banner.startAutoPlay();

        initSound();
        getPresenter().getBubbleData();
        getPresenter().getTaskData();
    }

    private void initSound() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        } else {
            soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);  // 创建SoundPool
        }
        soundID = soundPool.load(getContext(), R.raw.music, 1);
    }

    @OnClick({R.id.rl_detail, R.id.rl_withdrawal, R.id.rule, R.id.one_key})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_detail:
                UIManager.turnToAct(getActivity(), AccountInfoActivity.class);
                break;

            case R.id.rl_withdrawal:
                UIManager.turnToAct(getActivity(), SaleTBActivity.class);
                break;

            case R.id.rule:
                UIManager.turnToAct(getActivity(), RulesActivity.class);
                break;

            case R.id.one_key:
                if (bubbleData != null && bubbleData.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < bubbleData.size(); i++) {
                        stringBuffer.append(bubbleData.get(i).getId() + ",");
                    }
                    String strId = stringBuffer.toString().trim();
                    if (strId.length() > 0) {
                        getPresenter().receiveAllBubble(strId.substring(0, strId.length() - 1));
                    }
//                    getPresenter().receiveAllBubble();
                } else {
                    showToast("暂无红钻可领");
                }
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            banner.startAutoPlay();
            if (Constants.tab2ScrollText.size() == 0) {
                scrollText.setText("阅读分享文章可获得更多红钻");
            } else {
                if (scrollTextPosition >= Constants.tab2ScrollText.size()) {
                    scrollText.setText(Constants.tab2ScrollText.get(0));
                    scrollTextPosition = 1;
                } else {
                    scrollText.setText(Constants.tab2ScrollText.get(scrollTextPosition));
                    scrollTextPosition++;
                }
            }
//            tvBanner.startViewAnimator();
            getPresenter().getBubbleData();
            getPresenter().getTaskData();
        } else {
            banner.stopAutoPlay();
//            tvBanner.stopViewAnimator();
        }
    }

    // 领取任务
    private void receiveGold(String url) {
        LogUtils.d("领金币链接：" + url);
        showProgressDialog();
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
                dismissProgressDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dismissProgressDialog();
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
                                getPresenter().getTaskData();
                                getPresenter().getBubbleData();
//                                ReceiveGoldToast.showGoldToast(getActivity(), bean.getReturn().getGold() + "红钻");
                            } else {
                                showToast(bean.getErrinf());
                            }
                        }
                    });
                }
            }
        });
    }

    // 清除现有的红钻
    private void clearUI() {
        container.removeAllViews();
        container.clearData();
    }

    @Override
    public void getBubbleData(BubbleBean bean) {
        if (bean.getErrcode() == 0) {
            clearUI();

            bubbleViews.clear();

            myTBNum = Double.valueOf(bean.getTotal());
            diamondNum.setText(bean.getTotal() + "TB");
            detail.setText("今日:" + bean.getToday_gold() + "TB");

            sale.setText("今日:" + bean.getGold_price() + "元/TB");
            if (bean.getReturn() != null && bean.getReturn().size() > 0) {
                bubbleData.clear();
                bubbleData.addAll(bean.getReturn());
                bubbleSize = bean.getReturn().size();
//                LogUtils.d("diamondNum.Y:" + diamondNum.getY() + " diamondNum.getHeight:" + diamondNum.getHeight());
                for (int i = 0; i < bean.getReturn().size(); i++) {
                    int widthRandom = (int) (Math.random() * (bgWidth - bubbleWidth)); //
                    int heightRandom = (int) (Math.random() * (bgHeight - bubbleHeight - diamondNum.getY() - diamondNum.getHeight()) + diamondNum.getY() + diamondNum.getHeight()); //
                    addBubble(bean.getReturn().get(i).getId(), i, widthRandom, heightRandom, bean.getReturn().get(i).getGold());
                }
            } else {
                /*int widthRandom = (int) (Math.random() * (bgWidth - bubbleWidth)); //
                int heightRandom = (int) (Math.random() * (bgHeight - bubbleHeight - diamondNum.getY() - diamondNum.getHeight()) + diamondNum.getY() + diamondNum.getHeight()); //
                addBubble(1, 0, widthRandom, heightRandom, "0.0123");*/
                switch (bean.getReceive_statu()) {
                    case 0:
                        addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "停产中");
                        break;
                    case 1:
                        addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "生产中");
                        break;
                    case 2:
                        addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "领取中");
                        break;
                    default:
                        addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "生产中");
                        break;
                }
//                addLoadBubble(bgWidth / 2 - bubbleWidth / 2, 0);
            }
        } else {
            showToast(bean.getErrinf());
            switch (bean.getReceive_statu()) {
                case 0:
                    addBubble(0, 20, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "停产中");
                    break;
                case 1:
                    addBubble(0, 20, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "生产中");
                    break;
                case 2:
                    addBubble(0, 20, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "领取中");
                    break;
                default:
                    addBubble(0, 20, bgWidth / 2 - bubbleWidth / 2, diamondNum.getHeight(), "生产中");
                    break;
            }
//            addLoadBubble(bgWidth / 2 - bubbleWidth / 2, 0);
        }
    }

    @Override
    public void receiveBubble(BaseBean bean, View view, final int id, String str) {
        if (bean.errcode == 0) {
            for (int i = 0; i < bubbleData.size(); i++) {
                if (id == bubbleData.get(i).getId()) {
                    bubbleData.remove(i);
                    break;
                }
            }
            bubbleClose(view, str, -1);
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void receiveAllBubble(BaseBean bean) {
        if (bean.errcode == 0) {
            bubbleData.clear();
            for (int i = 0; i < bubbleViews.size(); i++) {
                bubbleClose(bubbleViews.get(i), "", i);
            }
        } else {
            showToast(bean.errinf);
        }
    }

    private int scrollTextPosition = 0;

    @Override
    public void getTaskData(MakeMoneyNewBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getData() == null)
                return;
            if (bean.getData().getMessage() != null && bean.getData().getMessage().size() > 0) {
                Constants.tab2ScrollText.clear();
//                autoStringList.clear();
                for (int i = 0; i < bean.getData().getMessage().size(); i++) {
//                    autoStringList.add(bean.getData().getMessage().get(i).getTitle());
                    Constants.tab2ScrollText.add(bean.getData().getMessage().get(i).getTitle());
                }
                if (scrollTextPosition == 0) {
                    scrollText.setText(bean.getData().getMessage().get(0).getTitle());
                    scrollTextPosition++;
                }
//                tvBanner.setDatas(autoStringList);
//                tvBanner.startViewAnimator();
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

            if (bean.getData() != null && bean.getData().getData() != null && bean.getData().getData().size() > 0) {
                ll_user.setVisibility(View.VISIBLE);
                userView.setVisibility(View.VISIBLE);
                userTask.setVisibility(View.VISIBLE);
                userTitle.setText(bean.getData().getTitle());
                tv_title_mark.setText(bean.getData().getTitle_mark());
                userData.clear();
                userData.addAll(bean.getData().getData());
                userAdapter.notifyDataSetChanged();
            } else {
                ll_user.setVisibility(View.GONE);
                userView.setVisibility(View.GONE);
                userTask.setVisibility(View.GONE);
            }

            if (bean.getData() != null && bean.getData().getData1() != null && bean.getData().getData1().size() > 0) {
                dayTitle.setText(bean.getData().getTitle1());
                tv_title_mark1.setText(bean.getData().getTitle_mark1());
                dayData.clear();
                dayData.addAll(bean.getData().getData1());
                dayAdapter.notifyDataSetChanged();
            }
        } else {
            showToast(bean.getErrinfo());
        }
    }

    @Override
    public void statsUserClick() {

    }

    private List<View> bubbleViews;
    // 本轮领取的钻石总数
    private double bubbleDiamond = 0;

    // 添加气泡
    private void addBubble(final int id, final int index, final int x, final int y, final String str) {
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_bubble, null);
                RelativeLayout item = (RelativeLayout) view.findViewById(R.id.rl_bubble);
                ImageView image1 = (ImageView) view.findViewById(R.id.image1);
                ImageView image2 = (ImageView) view.findViewById(R.id.image2);
                ImageView image3 = (ImageView) view.findViewById(R.id.image3);
                TextView tv_diamond = (TextView) view.findViewById(R.id.tv_diamond);
                tv_diamond.setText(str);
                rotatingAnimation(image1, 0);
                rotatingAnimation(image2, 1);
                rotatingStar(image3, 0);
                if (id == 0) {
                    bubbleAnimation(item, 1);
                } else {
                    bubbleAnimation(item, 0);
                }
                container.setChildPosition(x, y, view);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if (id == 1) {
                            bubbleDiamond = 0.0123;
                            bubbleClose(view, "", 0);
                        } else */
                        if (id != 0) {
                            if (bubbleData != null && bubbleData.size() > 0) {
                                StringBuffer stringBuffer = new StringBuffer();
                                bubbleDiamond = 0;
                                for (int i = 0; i < bubbleData.size(); i++) {
                                    stringBuffer.append(bubbleData.get(i).getId() + ",");
                                    bubbleDiamond += Double.valueOf(bubbleData.get(i).getGold());
                                }
                                String strId = stringBuffer.toString().trim();
                                if (strId.length() > 0) {
                                    getPresenter().receiveAllBubble(strId.substring(0, strId.length() - 1));
                                }
                            }
                            // 领取单个气泡
//                            getPresenter().receiveBubble(id, view, str);
                        } else {
                            showToast("阅读新闻可获取红钻");
                        }
                    }
                });
                bubbleViews.add(view);
                container.addView(view);
            }
        }, (index) * 50);
    }

    // 添加气泡
    private void addLoadBubble(final int x, final int y) {
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_loading_bubble, null);
                RelativeLayout item = (RelativeLayout) view.findViewById(R.id.rl_bubble);
                ImageView image1 = (ImageView) view.findViewById(R.id.image1);
                ImageView image2 = (ImageView) view.findViewById(R.id.image2);
                ImageView image3 = (ImageView) view.findViewById(R.id.image3);
                rotatingAnimation(image1, 0);
                rotatingAnimation(image2, 1);
                rotatingStar(image3, 0);
                bubbleAnimation(item, 1);
                container.setChildPosition(x, y, view);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("阅读新闻可获取红钻");
                    }
                });
                container.addView(view);
            }
        }, 0);
    }

    // 气泡上下浮动动画
    private void bubbleAnimation(View view, int type) {
        TranslateAnimation animation;
        if (type == 1) {
            animation = new TranslateAnimation(0, 0, -5, 5);
        } else {
            if ((int) (Math.random() * 2) == 1) {
                animation = new TranslateAnimation(0, 0, 5, -5);
            } else {
                animation = new TranslateAnimation(0, 0, -5, 5);
            }
        }
        animation.setInterpolator(new CycleInterpolator(1));
        animation.setDuration(3000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);
    }

    // 隐藏泡泡动画  type：1 一键收取
    private void bubbleClose(final View view, final String str, final int type) {
        AnimatorSet animatorSet = new AnimatorSet(); // 组合动画
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, -view.getX() + diamondNum.getX() + diamondNum.getWidth()/* - bubbleWidth / 2*/);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 0, -view.getY());
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (type == -1) {
                    playSound();
                    view.setVisibility(View.GONE);
                    bubbleSize--;
                    if (bubbleSize <= 0) {
                        getPresenter().getBubbleData();
                    } else {
                        myTBNum += Double.valueOf(str);
                        diamondNum.setText(ProjectUtils.getSix(myTBNum) + "TB");
                    }
                } else {
                    if (type == bubbleViews.size() - 1) {
                        ll_load.setVisibility(View.VISIBLE);
                        commit.setVisibility(View.VISIBLE);
                        add_diamond_num.setText("+" + ProjectUtils.getSix(bubbleDiamond));
                        handler.sendEmptyMessageDelayed(10, (int) ((3 + Math.random() * 3) * 1000));
                        loadRotatingAnimation();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.play(translationX).with(translationY).with(scaleX).with(scaleY); // 动画同时开始
        animatorSet.start();
    }

    private void playSound() {
        soundPool.play(
                soundID,
                0.1f,   //左耳道音量【0~1】
                0.5f,   //右耳道音量【0~1】
                0,     //播放优先级【0表示最低优先级】
                0,     //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }

    // load旋转动画
    private void loadRotatingAnimation() {
        RotateAnimation rotate;
        // 顺时针
        rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1000);//设置动画持续周期
        rotate.setRepeatCount(5);
        rotate.setRepeatMode(Animation.RESTART);
        loading.startAnimation(rotate);
    }

    // 旋转动画
    private void rotatingAnimation(View view, int type) {
        RotateAnimation rotate;
        if (type == 1) {
            // 顺时针
            rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            // 逆时针
            rotate = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(5000);//设置动画持续周期
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.RESTART);
        view.startAnimation(rotate);
    }

    // 旋转动画
    private void rotatingStar(final View view, final int type) {
        RotateAnimation rotate;
        if (type == 1) {
            rotate = new RotateAnimation(90f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotate = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(3000);//设置动画持续周期
        rotate.setFillAfter(true);

        AlphaAnimation alphaAnimation;
        if (type == 1) {
            alphaAnimation = new AlphaAnimation(1f, 0f);
        } else {
            alphaAnimation = new AlphaAnimation(0f, 1f);
        }
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setDuration(2500);//设置动画持续周期

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotate);
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Message message = new Message();
                message.what = type == 1 ? 0 : 1;
                message.obj = view;
                handler.sendMessageDelayed(message, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(set);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                ll_load.setVisibility(View.GONE);
                commit.setVisibility(View.GONE);
                add_diamond_num.setText("0.0000");
                getPresenter().getBubbleData();

                playSound();
            } else {
                rotatingStar((View) msg.obj, msg.what);
            }
        }
    };

    @Override
    public NewTab2Presenter createPresenter() {
        return new NewTab2Presenter();
    }
}
