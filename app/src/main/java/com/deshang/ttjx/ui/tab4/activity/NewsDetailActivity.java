package com.deshang.ttjx.ui.tab4.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.network.ServerConstants;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ScreenUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.adconfig.TTAdManagerHolder;
import com.deshang.ttjx.ui.login.activity.NewLoginActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.BezierEvaluator;
import com.deshang.ttjx.ui.mywidget.NoScrollLinearLayoutManager;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.RoundView;
import com.deshang.ttjx.ui.mywidget.ScrollWebView;
import com.deshang.ttjx.ui.mywidget.WaveProgressView;
import com.deshang.ttjx.ui.mywidget.WebViewScrollView;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;
import com.deshang.ttjx.ui.tab1.adapter.NewsDetailAdapter;
import com.deshang.ttjx.ui.tab1.bean.GeeTestBean;
import com.deshang.ttjx.ui.tab1.bean.IsFirstTimeShareOnDayBean;
import com.deshang.ttjx.ui.tab1.bean.ReadRewardBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendNewsBean;
import com.deshang.ttjx.ui.tab1.bean.ShareMessageBean;
import com.deshang.ttjx.ui.tab1.bean.VoteBean;
import com.deshang.ttjx.ui.tab1.presenter.ReadRewardPresenter;
import com.deshang.ttjx.ui.tab1.view.ReadRewardView;
import com.geetest.deepknow.bean.DPJudgementBean;
import com.geetest.sensebot.SEAPI;
import com.geetest.sensebot.listener.BaseSEListener;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.shuzilm.core.Main;
import mvp.cn.util.ToastUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * 新闻详情页
 * Created by L on 2018/5/31.
 */

public class NewsDetailActivity extends MvpSimpleActivity<ReadRewardView, ReadRewardPresenter> implements ReadRewardView, NativeExpressAD.NativeExpressADListener {

    // 极验id
    public static final String ID = "817de2e75f5d8a32151b8fbc816f5fce";
    private static final int TIME = 50;
    private static final int RED_PACKET_TIME = 30 * 1000;

    private SEAPI seapi;

    private int AD_COUNT = 10;    // 加载广告的条数，取值范围为[1, 10]
    private int FIRST_AD_POSITION = 1; // 第一条广告的position
    private int ITEMS_PER_AD = 4;     // 每间隔3个新闻插入一条广告  本身加上自己插入的一条广告所以是4

    // 代表每TIME毫秒走过的度数
    private float oneStepAngle = 0f;

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.round)
    RoundView roundView;
    @BindView(R.id.web_view)
    ScrollWebView webView;
    @BindView(R.id.my_progress)
    ProgressBar progressBar;
    @BindView(R.id.share_reward)
    ImageView share_reward;
    @BindView(R.id.share_reward1)
    ImageView share_reward1;
    @BindView(R.id.rl_red)
    RelativeLayout rlRed; // 红包奖励悬浮窗
    @BindView(R.id.scroll_view)
    WebViewScrollView scrollView;
    @BindView(R.id.rl_bd_ad)
    RelativeLayout rl_bd_ad;
    @BindView(R.id.frame_gdt_ad)
    FrameLayout frame_gdt_ad;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.load_more)
    TextView loadMore;
    @BindView(R.id.rl_tj)
    RelativeLayout rl_tj;
    @BindView(R.id.tv_toast)
    TextView tv_toast;
    @BindView(R.id.like)
    ImageView see;
    @BindView(R.id.wallet)
    ImageView wallet;
    @BindView(R.id.main)
    RelativeLayout main;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.bg_red_packet)
    ImageView red_packet;
    @BindView(R.id.ll_vote)
    LinearLayout ll_vote;
    //    @BindView(R.id.image_rotate)
//    ImageView image_rotate;
    @BindView(R.id.image_smoke)
    ImageView image_smoke;
    @BindView(R.id.vote_progress)
    WaveProgressView voteProgress;
    @BindView(R.id.tv_like)
    TextView tv_like;
    @BindView(R.id.rl_zan)
    RelativeLayout rl_zan;
    @BindView(R.id.can_see_image)
    RelativeLayout can_see_image;
    @BindView(R.id.image_rules1)
    ImageView image_rules1;
    @BindView(R.id.rl_rules)
    RelativeLayout rl_rules;
    @BindView(R.id.tv_bonus_money)
    TextView tv_bonus_money;
    @BindView(R.id.rl_tb)
    RelativeLayout rlTB; // 右上角

    private int type; // 标注从哪个页面跳转过来的

    private String id; // 需要加载的url
    private String typeID = "3";

    // 首次只有网页加载完毕的时候才开始计时
    private int first = 0;
    // 用来计算5秒钟时间的累加  毫秒
    private int timeFive = 0;
    // 随机秒数累加 （用户滑动间隔小于此数时#timeFive不重置）毫秒
    private int randomSecond = 0;
    // 是否处于停止状态  请求接口失败的时候此值为1  正常为0
    private int isStop = 0;
    // 问号说明文字3秒倒计时开关
    private int closeWhyTextTime = 0;
    // 分享返回
//    private boolean onShare = false;

    private List<NativeExpressADView> mAdViewList;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap;
    private List<Object> data;
    private NewsDetailAdapter adapter;
    private int page = 1;

    private boolean isLoading = false; // 是否正在加载推荐列表
    private boolean canLoadRecommend = true;
    private boolean isCanReceive = true;

    private TTAdNative mTTAdNative; // 穿山甲AD
    private List<TTFeedAd> mTTADList;
    private int isCanLoadAD = 0;

    // 金币图片
    private Bitmap bitmap;
    private int bitmapWidth, bitmapHeight;

    private AnimationDrawable /*animationDrawable, */drawable;

    // 是否已经点赞
    private boolean isLiker = false;
    // 已投票数
    private int likeNum = 0;
    // 最大点赞数
    private int maxLikeNum = 0;
    // 可用的票数
    private int usableVote = 0;
    // 当前页面是否显示投票上方的图片
    private boolean showVoteImage = false;

    {
        data = new ArrayList<>();
        mAdViewPositionMap = new HashMap<>();
        mAdViewList = new ArrayList<>();
        mTTADList = new ArrayList<>();
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_news_detail);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*if (animationDrawable == null) {
            image_rotate.setImageResource(R.drawable.rotate_animlist);
            animationDrawable = (AnimationDrawable) image_rotate.getDrawable();
            animationDrawable.start();
        }*/
        if (drawable == null) {
            image_smoke.setImageResource(R.drawable.wallet_animlist);
            drawable = (AnimationDrawable) image_smoke.getDrawable();
        }
    }

    @Override
    public void initView() {
        seapi = new SEAPI(this);
//        DPAPI.getInstance(getApplicationContext()).ignoreDPView(getCode, "NewLoginActivity");

        titleBar.setTitle("");
        titleBar.setBack(true);

        // 三到五篇文章显示一个
        Constants.redNewsCount++;
        if (Constants.redNewsCount >= ((int) (Math.random() * 3) + 3)) {
            Constants.redNewsCount = 0;
            showVoteImage = true;
        } else {
            showVoteImage = false;
        }

        // 是否是第一次安装
        if (SharedPrefHelper.getInstance().getFirstTimeOpen() && SharedPrefHelper.getInstance().getOnLine()) {
            canSeeOpen(rl_rules, 1);
        }

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wallet_diamond);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

        TTAdManager ttAdManager = TTAdManagerHolder.getInstance();
        mTTAdNative = ttAdManager.createAdNative(NewsDetailActivity.this);
        if (!SharedPrefHelper.getInstance().getOnLine()) {
            rlRed.setVisibility(View.GONE);
            rlTB.setVisibility(View.GONE);
        } else {
            rlRed.setVisibility(View.VISIBLE);
            rlTB.setVisibility(View.VISIBLE);
        }
        adapter = new NewsDetailAdapter(this, data, mAdViewPositionMap);
        adapter.setOnClickListener(new NewsDetailAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                UserPathUtils.commitUserPath(17);
                canLoadRecommend = false;
                if ("".equals(SharedPrefHelper.getInstance().getUserId())) {
                    webView.loadUrl(ServerConstants.OUR + Constants.NEWS_DETAIL + "/id/" + id);
                } else {
                    webView.loadUrl(ServerConstants.OUR + Constants.NEWS_DETAIL + "/userid/" + SharedPrefHelper.getInstance().getUserId() + "/id/" + id);
                }
            }
        });

        NoScrollLinearLayoutManager layoutManager = new NoScrollLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        type = getIntent().getIntExtra("Type", 0);
        typeID = getIntent().getStringExtra("TypeID");
        id = getIntent().getStringExtra("Url");

        // 每次圆环加的度数
        oneStepAngle = 360f / RED_PACKET_TIME * TIME;

        // 设置圆环角度
        roundView.setAngle(Constants.ROUND_ANGLE);

        roundView.setOnSubmitListener(new RoundView.OnSubmitListener() {
            @Override
            public void onSubmit() {
                stopTimer(0);
                isStop = 1;
                if (!SharedPrefHelper.getInstance().getOnLine()) {
                    // 审核没通过时不提交
                    return;
                }
                String time = String.valueOf(System.currentTimeMillis());
                if (SharedPrefHelper.getInstance().getToken() == null || "".equals(SharedPrefHelper.getInstance().getToken())) {
                    ReceiveGoldToast.makeToast(SoftApplication.getContext(), "您当前处于未登录状态，登录后才可领取金币").show();
                    Intent intent = new Intent(SoftApplication.getContext(), NewLoginActivity.class);
                    intent.putExtra("Type", 1);
                    intent.putExtra("ID", id);
                    intent.putExtra("Time", time);
                    startActivityForResult(intent, 1001);
                    return;
                }
                // Constants.IS_RECEIVE_RED_PACKET = 1时才去请求阅读奖励
                if (isCanReceive) {
                    isCanReceive = false;
                    if (type == 1) {
                        getPresenter().activateRedPacket(id, time.substring(0, time.length() - 3));
                    } else {
                        String device = Main.getQueryID(NewsDetailActivity.this, Constants.CHANNEL_TYPE, Constants.CHANNEL_TYPE);
                        getPresenter().getReadRewardData(id, time.substring(0, time.length() - 3), device);
                    }
                }
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 适配屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.addJavascriptInterface(this, "android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int index = url.indexOf("/id/");
                id = url.substring(index + 4);
                LogUtils.d("请求链接：" + url + " index：" + index + " id：" + id);
                Message message = new Message();
                message.what = 5;
                // 5秒验证极验
                handler.sendMessageDelayed(message, 5000);
                UserPathUtils.commitUserPathWithNews(16, id);
                getPresenter().getVoteData(id);
                rl_tj.setVisibility(View.VISIBLE);
                /*if (SharedPrefHelper.getInstance().getOnLine()) {
                    ll_vote.setVisibility(View.VISIBLE);
                }*/
                page = 1;
                // 获取下方推荐文章
                getPresenter().getRecommendNews(typeID, id, page);
                // 统计次数
//                clickNewsDetail();
                // 大图广告
                int random = (int) (Math.random() * 5);
                if (random == 4) {
                    // 百度广告
                    rl_bd_ad.setVisibility(View.VISIBLE);
                    frame_gdt_ad.setVisibility(View.GONE);
                    AdView adView = new AdView(NewsDetailActivity.this, Constants.BAIDU_BANNER);
                    adView.setListener(new AdViewListener() {
                        @Override
                        public void onAdReady(AdView adView) {

                        }

                        @Override
                        public void onAdShow(JSONObject jsonObject) {

                        }

                        @Override
                        public void onAdClick(JSONObject jsonObject) {

                        }

                        @Override
                        public void onAdFailed(String s) {

                        }

                        @Override
                        public void onAdSwitch() {

                        }

                        @Override
                        public void onAdClose(JSONObject jsonObject) {
                            rl_bd_ad.setVisibility(View.GONE);
                        }
                    });
                    if (Constants.width == 0) {
                        DisplayMetrics dm = new DisplayMetrics();
                        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
                        int winW = dm.widthPixels;
                        int winH = dm.heightPixels;
                        Constants.width = Math.min(winW, winH);
                    }
                    // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
                    RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(Constants.width, Constants.width / 2);
                    rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    if (rl_bd_ad.getChildCount() > 0) {
                        rl_bd_ad.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }
                    rl_bd_ad.addView(adView, rllp);
                } else {
                    // 广点通广告
                    rl_bd_ad.setVisibility(View.GONE);
                    frame_gdt_ad.setVisibility(View.VISIBLE);
                    initNativeExpressAD(0);
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtils.d("当前加载进度：" + newProgress);
                if (newProgress == 100) {
                    ll_vote.setVisibility(View.GONE);
                    scrollView.scrollTo(0, 0);
                    progressBar.setVisibility(View.GONE);
                    first = 1;
                    timeFive = 0;
                    randomSecond = (int) (Math.random() * (SharedPrefHelper.getInstance().getEndSecond() - SharedPrefHelper.getInstance().getStartSecond()))
                            + SharedPrefHelper.getInstance().getStartSecond(); // 4 - 6
                    if (Constants.ROUND_ANGLE >= 360 && "".equals(SharedPrefHelper.getInstance().getToken())) {
                        if (SharedPrefHelper.getInstance().getOnLine()) {
                            showToast("登陆后才能继续赚取金币~");
                        }
                    } else {
                        startRandomTimer();
                        startTimer();
                    }
                    if (!"".equals(SharedPrefHelper.getInstance().getToken())) {
                        showShareRewardHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                    /*if (SharedPrefHelper.getInstance().getLoginState()) {
                        // 当天第一次登陆
                        int number = (int) (Math.random() * 10);
                        if (number > 1) {
                            // 显示分享赚钱
                            shareRewardOpen();
                        }
                    }*/
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

        scrollView.setScrollViewListener(new WebViewScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                // 条件：处于屏幕中 已上线   红包规则已点击我知道了
                if (checkIsVisible(NewsDetailActivity.this, rl_zan) && SharedPrefHelper.getInstance().getOnLine() && !SharedPrefHelper.getInstance().getFirstTimeOpen()) {
                    if (SharedPrefHelper.getInstance().getWhyState() && ll_vote.getVisibility() == View.VISIBLE) {
                        // 未点击
                        canSeeOpen(can_see_image, 0);
                    } else if (showVoteImage && ll_vote.getVisibility() == View.VISIBLE) {
                        // 弹出另外一个说明图
                        canSeeOpen(image_rules1, 0);
                    }
                    closeWhyTextTime = 0;
                    startTimer1();
                } else {
                    canSeeClose(can_see_image, 0);
                    stopTimer1();
                }
                if (Constants.ROUND_ANGLE >= 360 && "".equals(SharedPrefHelper.getInstance().getToken())) {
                    return;
                }
                if (y - oldy > 0 && isStop == 0) {
//                    LogUtils.d("randomSecond：" + randomSecond);
                    if (randomSecond <= 0 && timeFive >= 5000) {
//                        LogUtils.d("randomSecond：进来了");
                        // 判断只有#randomSecond小于0时才重置#timeFive
                        randomSecondNum = randomSecond = (int) (Math.random() * (SharedPrefHelper.getInstance().getEndSecond() - SharedPrefHelper.getInstance().getStartSecond()))
                                + SharedPrefHelper.getInstance().getStartSecond(); //
                        timeFive = 0;
                        startTimer();
                    }
                    randomSecond = randomSecondNum;
                }
            }
        });

        scrollView.setScrollBottomListener(new WebViewScrollView.ScrollBottomListener() {
            @Override
            public void scrollToBottom() {
                if (!isLoading) {
                    if (canLoadRecommend) {
                        isLoading = true;
                        getPresenter().getRecommendNews(typeID, id, page);
                    } else {
                        canLoadRecommend = true;
                    }
                }
            }
        });

        if ("".equals(SharedPrefHelper.getInstance().getUserId())) {
            webView.loadUrl(ServerConstants.OUR + Constants.NEWS_DETAIL + "/id/" + id);
        } else {
            webView.loadUrl(ServerConstants.OUR + Constants.NEWS_DETAIL + "/userid/" + SharedPrefHelper.getInstance().getUserId() + "/id/" + id);
        }
    }

    private int randomSecondNum = 0;

    private Timer timer1;
    private TimerTask task1;

    // 开始计时
    public void startTimer1() {
        if (null == timer1) {
            timer1 = new Timer();
            if (null != task1) {
                task1.cancel();
            }
            task1 = new TimerTask() {
                @Override
                public void run() {
                    if (closeWhyTextTime >= 2) {
                        if (SharedPrefHelper.getInstance().getWhyState()) {
                            // 未点击
                            closeHandler.sendEmptyMessage(1);
                        } else {
                            closeHandler.sendEmptyMessage(2);
                        }
                    } else {
                        closeWhyTextTime++;
                    }
                }
            };
            timer1.schedule(task1, 0, 1000);
        }
    }

    // 停止计时
    public void stopTimer1() {
        if (task1 != null) {
            task1.cancel();
            task1 = null;
        }
        if (null != timer1) {
            timer1.cancel();
            timer1 = null;
        }
    }

    // 投票说明文字关闭
    Handler closeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                canSeeClose(can_see_image, 0);
            } else {
                canSeeClose(image_rules1, 0);
            }
        }
    };


    // 展开投票布局
    @JavascriptInterface
    public void showVoteUI() {
        LogUtils.d("展示投票");
        Message message = new Message();
        message.what = 6;
        handler.sendMessage(message);
    }

    @JavascriptInterface
    public void shareWechat() {
        getPresenter().getShareData(id, 3);
    }

    @JavascriptInterface
    public void shareCircle() {
        getPresenter().getShareData(id, 2);
    }

    Handler showShareRewardHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (SharedPrefHelper.getInstance().getOnLine()) {
                // 审核通过
                if (msg.what == 1) {
                    if (SharedPrefHelper.getInstance().getShareShowTime() < 3 && !SharedPrefHelper.getInstance().getWhyState() && !SharedPrefHelper.getInstance().getFirstTimeOpen()) {
                        shareRewardOpen();
                    }
                } else if (msg.what == 2) {
                    shareRewardClose();
                } else if (msg.what == 3) {
                    toastAnimationOpen((String) msg.obj);
                } else if (msg.what == 4) {
                    toastAnimationClose();
                }
            }
        }
    };

    // 值得看  打开
    private void canSeeOpen(View view, int type) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            ScaleAnimation animation;
            if (type == 1) {
                animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
            } else {
                animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.0f);
            }
            animation.setDuration(200);
            view.startAnimation(animation);
        }
    }

    // 值得看  关闭
    private void canSeeClose(final View view, int type) {
        if (view.getVisibility() == View.VISIBLE) {
            ScaleAnimation animation;
            if (type == 1) {
                animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
            } else {
                animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.0f);
            }
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animation.setDuration(200);
            view.startAnimation(animation);
        }
    }

    // 分享奖励缩放动画  打开
    private void shareRewardOpen() {
        if (share_reward1.getVisibility() == View.GONE) {
            share_reward1.setVisibility(View.VISIBLE);
//            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // 5秒后执行关闭动画
                    SharedPrefHelper.getInstance().setShareShowTime(SharedPrefHelper.getInstance().getShareShowTime() + 1);
                    showShareRewardHandler.sendEmptyMessageDelayed(2, 5000);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animation.setDuration(1000);
            share_reward1.startAnimation(animation);
        }
    }

    // 分享奖励缩放动画  关闭
    private void shareRewardClose() {
        if (share_reward1.getVisibility() == View.VISIBLE) {
//            close.setVisibility(View.GONE);
//            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    share_reward1.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animation.setDuration(1000);
            share_reward1.startAnimation(animation);
        }
    }

    // 提醒  打开
    private void toastAnimationOpen(String text) {
        tv_toast.setText(text);
        if (tv_toast.getVisibility() == View.GONE) {
            tv_toast.setVisibility(View.VISIBLE);
            TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.0f);
            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // 3秒后执行关闭动画
                    showShareRewardHandler.sendEmptyMessageDelayed(4, 3000);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animation1.setDuration(1000);
            animation1.setFillAfter(true);
            tv_toast.startAnimation(animation1);
        }
    }

    // 提醒  关闭
    private void toastAnimationClose() {
        if (tv_toast.getVisibility() == View.VISIBLE) {
            TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f);
            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_toast.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            animation1.setDuration(1000);
            animation1.setFillAfter(true);
            tv_toast.startAnimation(animation1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                // 登录了之后直接重新计时
                timeFive = 0; // 五秒计时清0
                isStop = 0;
                roundView.setAngle(0f);
                startTimer();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                roundView.setArc(oneStepAngle);
            } else if (msg.what == 1) {
                // 红包缩小动画
                shrinkAnimation(red_packet);
            } else if (msg.what == 2) {
                // 红包放大动画
                tv_money.setVisibility(View.GONE);
                red_packet.setVisibility(View.VISIBLE);
                magnifyAnimation(red_packet);
            } else if (msg.what == 3) {
                // 文字缩小动画
                shrinkAnimation(tv_money);
                int[] location = new int[2];
                rlRed.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                int[] location1 = new int[2];
                wallet.getLocationOnScreen(location1);

                ImageView imageView = new ImageView(NewsDetailActivity.this);
                imageView.setImageBitmap(bitmap);
                imageView.setX(x + rlRed.getWidth() / 2 - bitmapWidth / 2);
                imageView.setY(y - ScreenUtils.getStatusBarHeight(NewsDetailActivity.this) + rlRed.getHeight() / 2 - bitmapHeight / 2);
                main.addView(imageView);
                LogUtils.d("图片宽度：" + bitmapWidth + " 图片高度：" + bitmapHeight);
                getBezierValueAnimator(new PointF(x + rlRed.getWidth() / 2 - bitmapWidth / 2, y - ScreenUtils.getStatusBarHeight(NewsDetailActivity.this) + rlRed.getHeight() / 2 - bitmapHeight / 2),
                        new PointF(location1[0], location1[1]), imageView).start();
            } else if (msg.what == 4) {
                // 文字放大动画
                red_packet.setVisibility(View.GONE);
                tv_money.setVisibility(View.VISIBLE);
                magnifyAnimation(tv_money);
            } else if (msg.what == 5) {
                // 极验验证
                geeTest();
            } else if (msg.what == 6) {
                // 展开投票UI
                if (SharedPrefHelper.getInstance().getOnLine()) {
                    ll_vote.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void geeTest() {
        seapi.onVerify(new DPJudgementBean(ID, 1, new HashMap<String, Object>()), new BaseSEListener() {
            /**
             * SDK内部show loading dialog
             */
            @Override
            public void onShowDialog() {
            }

            @Override
            public void onError(String errorCode, String error) {
                showToast(errorCode + ":" + error);
            }

            /**
             * 验证码Dialog关闭
             * 1：webview的叉按钮关闭
             * 2：点击屏幕外关闭
             * 3：点击回退键关闭
             *
             * @param num
             */
            @Override
            public void onCloseDialog(int num) {
            }

            /**
             * show 验证码webview
             */
            @Override
            public void onDialogReady() {
            }

            /**
             * 验证成功
             * @param challenge
             */
            @Override
            public void onResult(String challenge) {
                geeTestCommitDetail(challenge);
            }
        });
    }

    /**
     * 统计内文点击次数
     */
    public void clickNewsDetail() {
        Observable request = RetrofitUtils.getInstance().clickNewsDetail();
        Subscriber<BaseBean> subscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(BaseBean bean) {
            }
        };
        request.subscribe(subscriber);
    }

    /**
     * 验证极验
     */
    public void geeTestCommitDetail(String challenge) {
        Observable request = RetrofitUtils.getWalletInstance().geeTestCommitDetail(id, challenge);
        Subscriber<GeeTestBean> upDataSubscriber = new Subscriber<GeeTestBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("验证极验异常" + e.toString());
            }

            @Override
            public void onNext(final GeeTestBean bean) {
                if (bean != null && bean.getRisk_code() != null && bean.getRisk_code().size() > 0) {
                    if (bean.getRisk_level() == 3 || bean.getRisk_level() == 7 || bean.getRisk_level() == 9){
                        commitGeeTestDetail(bean.getRisk_code().get(0));
                    }
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 极验提交
     */
    public void commitGeeTestDetail(String risk_code) {
        Observable request = RetrofitUtils.getWalletInstance().commitGeeTestDetail(id, risk_code);
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交极验异常" + e.toString());
            }

            @Override
            public void onNext(final BaseBean bean) {
            }
        };
        request.subscribe(upDataSubscriber);
    }

    private Timer timer, randomTimer;
    private TimerTask task, randomTask;

    // 开始计时
    public void startTimer() {
        LogUtils.d("timer:圆圈计时  开始");
        if (null == timer) {
            timer = new Timer();
            if (null != task) {
                task.cancel();
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    timeFive += TIME;
                    if (timeFive >= 5000) {
                        // 计时到了5秒
                        stopTimer(1);
                    } else {
                        handler.sendEmptyMessage(0);
                    }
                }
            };
            timer.schedule(task, 0, TIME);
        }
    }

    // 停止计时
    public void stopTimer(int type) {
        LogUtils.d("timer:圆圈计时  停止");
        if (type == 1){
            startRandomTimer();
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startRandomTimer();
        startTimer();
    }

    // 随机数second开始计时
    public void startRandomTimer() {
        LogUtils.d("timer:随机计时  开始");
        if (null == randomTimer) {
            randomTimer = new Timer();
            if (null != randomTask) {
                randomTask.cancel();
            }
            randomTask = new TimerTask() {
                @Override
                public void run() {
                    if (randomSecond > 0) {
                        randomSecond--;
                    } else {
                        stopRandomTimer();
                    }
//                    LogUtils.d("randomSecond:" + randomSecond);
                }
            };
            randomTimer.schedule(randomTask, 0, 1000);
        }
    }

    // 随机数second停止计时
    public void stopRandomTimer() {
        LogUtils.d("timer:随机计时  停止");
        if (randomTask != null) {
            randomTask.cancel();
            randomTask = null;
        }
        if (null != randomTimer) {
            randomTimer.cancel();
            randomTimer = null;
        }
    }

    @OnClick({R.id.share, R.id.rl_red, R.id.bg_red_packet, R.id.share_reward1, R.id.load_more, R.id.can_see, R.id.rl_tb, R.id.i_know, R.id.image_like_bg, R.id.rules_know})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                UserPathUtils.commitUserPathWithNews(18, id);
//                getPresenter().getShareData(id, 0);
//                getPresenter().getIsFirstTimeShareOnDay();
                getPresenter().shareClickNnm(1);
                ShareDialog dialog = new ShareDialog(NewsDetailActivity.this, R.style.dialog_style);
                dialog.setVisible(1);
                dialog.setClickListener(new ShareDialog.ClickListener() {
                    @Override
                    public void onClickListener(int type) {
                        if (type == 1) {
                            // 微信朋友圈
                            UserPathUtils.commitUserPath(39);
                            getPresenter().shareClickNnm(2);
                            getPresenter().getShareData(id, 2);
                        } else if (type == 2) {
                            // 微信好友
                            UserPathUtils.commitUserPath(40);
                            getPresenter().shareClickNnm(3);
                            getPresenter().getShareData(id, 3);
                        } else if (type == 6) {
                            // QQ
                            UserPathUtils.commitUserPath(41);
                            getPresenter().shareClickNnm(4);
                            getPresenter().getShareData(id, 1);
                        }
                    }
                });
                dialog.show();
                break;

            case R.id.rl_red:
//                ReceiveGoldToast.showGoldToast(this, "+34");
                break;

            case R.id.bg_red_packet:
//                UIManager.turnToAct(this, ReadRewardExplainActivity.class);
                break;

            case R.id.share_reward1:
                UIManager.turnToAct(NewsDetailActivity.this, ShareRewardActivity.class);
                shareRewardClose();
                break;

            case R.id.load_more:
                loadMore.setText("加载中...");
                getPresenter().getRecommendNews(typeID, id, page);
                break;

            case R.id.can_see:
                UIManager.turnToAct(this, ReadRewardExplainActivity.class);
                break;

            case R.id.image_like_bg:
                // 点赞
                if (usableVote <= 0) {
                    // 没有可用的票
                    break;
                }
                if (likeNum >= maxLikeNum) {
                    // 最大投票数
                    showToast("已达到每日最大投票数");
                } else {
                    if (!isLiker) {
                        getPresenter().voteData(id);
                    } else {
                        showToast("已经点赞了");
                    }
                }
                break;

            case R.id.rl_tb:
                // 去赚钱页面
                Constants.turn_to_other_tab = 2;
                finish();
                break;

            case R.id.i_know:
                // 红包我知道了
                SharedPrefHelper.getInstance().setFirstTimeOpen(false);
                canSeeClose(rl_rules, 1);
                break;

            case R.id.rules_know:
                // 我知道了
                SharedPrefHelper.getInstance().setWhyState(false);
                canSeeClose(can_see_image, 0);
                // 打开分享说明页
                shareRewardOpen();
                break;
        }
    }

    // 控件缩小动画
    private void shrinkAnimation(final View view) {
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);

        animatorSetsuofang.setDuration(500);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view.getId() == R.id.bg_red_packet) {
                    // 文字放大
                    handler.sendEmptyMessage(4);
                } else {
                    // 红包放大
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSetsuofang.start();
    }

    // 控件放大动画
    private void magnifyAnimation(final View view) {

        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);

        animatorSetsuofang.setDuration(500);
        animatorSetsuofang.setInterpolator(new OvershootInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view.getId() == R.id.tv_money) {
                    handler.sendEmptyMessageDelayed(3, 1000);
                } else {
//                    magnifyAnimation(red_packet);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSetsuofang.start();
    }

    private ValueAnimator getBezierValueAnimator(PointF pointF1, PointF pointF2, final View target) {
        // 初始化贝塞尔估值器
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(Constants.width / 2 - 50, 10));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF1, pointF2);
        animator.setTarget(target);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
//                LogUtils.d("pointF.x" + pointF.x + " pointF.y" + pointF.y);
                target.setX(pointF.x);
                target.setY(pointF.y);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                target.setVisibility(View.GONE);
                main.removeView(target);
            }
        });
        animator.setDuration(1000);
        return animator;
    }

    @Override
    public void getDataSucc(ReadRewardBean bean) {
        isCanReceive = true;
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null) {
                getPresenter().getVoteData(id);
//                ReceiveGoldToast.showGoldToast(SoftApplication.getContext(), bean.getReturn().getGold() + "金币");
                handler.sendEmptyMessage(1);
                tv_money.setText("+" + bean.getReturn().getGold());
            }
            timeFive = 0;
            isStop = 0;
            roundView.setAngle(0f);
            startTimer();
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void activateRedPacketSucc(ReadRewardBean bean) {
        isCanReceive = true;
        if (bean.getErrcode() == 0) {
            getPresenter().getVoteData(id);
            if (bean.getReturn() != null) {
                ReceiveGoldToast.showGoldToast(this, bean.getReturn().getGold() + "红钻");
            }
            timeFive = 0;
            isStop = 0;
            roundView.setAngle(0f);
            startTimer();
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void getShareData(final ShareMessageBean bean, int type) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;

            switch (type) {
                case 1:
                    // QQ 分享
                    ShareUtils.shareSDK(NewsDetailActivity.this, bean.getReturn().getTitle(), SharedPrefHelper.getInstance().getShareUrl() + Constants.NEWS_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                            bean.getReturn().getCover_img(), QQ.NAME, 1);
                    break;

                case 2:
                    // 朋友圈分享
                    ShareUtils.showShare(NewsDetailActivity.this, bean.getReturn().getTitle(), bean.getReturn().getContent(), SharedPrefHelper.getInstance().getShareUrl() + Constants.NEWS_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                            bean.getReturn().getCover_img(), WechatMoments.NAME, 1);
                    break;

                case 3:
                    // 微信分享
                    ShareUtils.showShare(NewsDetailActivity.this, bean.getReturn().getTitle(), bean.getReturn().getContent(), SharedPrefHelper.getInstance().getShareUrl() + Constants.NEWS_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                            bean.getReturn().getCover_img(), Wechat.NAME, 1);
                    break;
            }
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void getRecommendNews(RecommendNewsBean bean) {
        loadMore.setText("点击加载更多...");
        isLoading = false;
        if (bean.getCode() == 0) {
            if (page == 1) {
                FIRST_AD_POSITION = 1;
                data.clear();
            }
            page++;
            data.addAll(bean.getData());
            adapter.notifyDataSetChanged();
            initNativeExpressAD(1);
            loadListAd();
        }
    }

    @Override
    public void getIsFirstTimeShareOnDay(IsFirstTimeShareOnDayBean bean) {

        ShareDialog dialog = new ShareDialog(NewsDetailActivity.this, R.style.dialog_style);
        if (bean.getReturn() == null) {
            dialog.setVisible(1);
        } else {
            dialog.setVisible(bean.getReturn().getDayfirst());
        }
        dialog.setClickListener(new ShareDialog.ClickListener() {
            @Override
            public void onClickListener(int type) {
                if (type == 1) {
                    // 微信朋友圈
                    UserPathUtils.commitUserPath(39);
                    getPresenter().getShareData(id, 2);
                } else if (type == 2) {
                    // 微信好友
                    UserPathUtils.commitUserPath(40);
                    getPresenter().getShareData(id, 3);
                } else if (type == 6) {
                    // QQ
                    UserPathUtils.commitUserPath(41);
                    getPresenter().getShareData(id, 1);
                }
            }
        });
        dialog.show();
    }

    // 获取投票信息
    @Override
    public void getVoteData(VoteBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null) {
                return;
            }
            tv_bonus_money.setText(String.valueOf(bean.getReturn().getArticle_vote_sum()));
            usableVote = bean.getReturn().getTotal_votes();
            likeNum = bean.getReturn().getCast_votes();
            maxLikeNum = bean.getReturn().getMaximum_vote();
            tv_like.setText(String.valueOf(bean.getReturn().getArticle_vote_count()));
            isLiker = bean.getReturn().getVote_type() == 1;
            if (bean.getReturn().getVote_type() == 1) {
                // 已点赞
                see.setImageDrawable(getResources().getDrawable(R.mipmap.like_selector));
            } else {
                // 未点赞
                see.setImageDrawable(getResources().getDrawable(R.mipmap.like));
            }
            voteProgress.setMax(bean.getReturn().getRead_vote() + 1);
            if (bean.getReturn().getVote_number_type() == 1) {
                // 无余票
                voteProgress.setProgress(bean.getReturn().getRead_number() + 1);
                voteProgress.setText("0");
            } else {
                // 有余票
                voteProgress.setProgress(bean.getReturn().getRead_vote() + 1);
                voteProgress.setText(String.valueOf(bean.getReturn().getTotal_votes()));
            }
            if (bean.getReturn().getEinnahmen_count() == 0 && drawable != null) {
                // 没有可以领取的钻石
                wallet.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_tb_gray));
                drawable.stop();
                image_smoke.setVisibility(View.INVISIBLE);
            } else {
                if (drawable != null) {
                    wallet.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_tb));
                    image_smoke.setVisibility(View.VISIBLE);
                    drawable.start();
                }
            }
        } else {
            showToast(bean.getErrinf());
        }
    }

    // 投票
    @Override
    public void voteData(BaseBean bean) {
        if (bean.errcode == 0) {
            int[] location = new int[2];
            see.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            int[] location1 = new int[2];
            wallet.getLocationOnScreen(location1);

            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_diamond));
            imageView.setX(x + see.getWidth() / 2 - bitmapWidth / 2);
            imageView.setY(y - ScreenUtils.getStatusBarHeight(this));
//                LogUtils.d("pointF.123123123:" + (x + see.getWidth() / 2 - bitmapWidth / 2) + " pointF.123123123:" + (y - ScreenUtils.getStatusBarHeight(this)));
            main.addView(imageView);
            getBezierValueAnimator(new PointF(x, y - ScreenUtils.getStatusBarHeight(this)),
                    new PointF(location1[0], location1[1]), imageView).start();
            getPresenter().getVoteData(id);
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (first == 1) {
//            timeFive = 0;
            if (Constants.ROUND_ANGLE >= 360 && "".equals(SharedPrefHelper.getInstance().getToken())) {
            } else {
                if (isStop == 0 && isFirstIn)
                    startTimer();
            }
        }
    }

    private boolean isFirstIn = true;

    @Override
    public void onPause() {
        super.onPause();
        stopTimer(0);
        stopRandomTimer();
        isFirstIn = false;
    }

    @Override
    public void onDestroy() {
        stopTimer(0);
        stopRandomTimer();
        stopTimer1();
        handler.removeMessages(0);
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再 destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
        // 使用完了每一个NativeExpressADView之后都要释放掉资源。
        if (mAdViewList != null) {
            for (NativeExpressADView view : mAdViewList) {
                view.destroy();
            }
        }
    }

    /**
     * 加载feed广告
     */
    private void loadListAd() {
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(Constants.TOUTIAO_AD_ID)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(AD_COUNT)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                addNum();
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                mTTADList = ads;
                addNum();
            }
        });
    }

    // 判断是否可以加载广告的计数器累加
    private void addNum() {
        isCanLoadAD++;
        if (isCanLoadAD >= 2) {
            addADFactory();
        }
    }

    /**
     * 添加广告到列表 工厂方法
     *
     * @param
     */
    private void addADFactory() {
        if ((mAdViewList == null || mAdViewList.size() == 0) && (mTTADList == null || mTTADList.size() == 0)) {
            return;
        }
        int position = FIRST_AD_POSITION;
        if (position < data.size()) {
            int adRandom = (int) (Math.random() * 2); // 0 - 1
            if (adRandom == 0) {
                // 广点通信息流
                if (mAdViewList != null && mAdViewList.size() > 0) {
                    NativeExpressADView view = mAdViewList.get(0);
                    mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                    adapter.addADViewToPosition(position, view);
                    mAdViewList.remove(0);
                    FIRST_AD_POSITION += ITEMS_PER_AD;
                }
            } else {
                // 今日头条信息流
                if (mTTADList != null && mTTADList.size() > 0) {
                    TTFeedAd ad = mTTADList.get(0);
                    adapter.addTTADToPosition(position, ad);
                    // 移除第一条广告
                    mTTADList.remove(0);
                    FIRST_AD_POSITION += ITEMS_PER_AD;
                }
            }
            // 重新随机
            addADFactory();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        addNum();
        LogUtils.d("广告加载失败：" + adError.toString());
    }

    /**
     * 加载广点通广告
     */
    private void initNativeExpressAD(int type) {
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        NativeExpressAD mADManager;
        if (type == 1) {
            // 左图右文
            mADManager = new NativeExpressAD(getActivity(), adSize, Constants.GDT_APP_ID, Constants.GDT_RIGHT_IMAGE_ID, this);
            mADManager.loadAD(AD_COUNT);
        } else {
            // 大图广告
            mADManager = new NativeExpressAD(getActivity(), adSize, Constants.GDT_APP_ID, Constants.GDT_BIG_IMAGE, new NativeExpressAD.NativeExpressADListener() {
                @Override
                public void onNoAD(AdError adError) {

                }

                @Override
                public void onADLoaded(List<NativeExpressADView> list) {
                    if (frame_gdt_ad.getChildCount() > 0) {
                        frame_gdt_ad.removeAllViews();
                    }
                    NativeExpressADView nativeExpressADView = list.get(0);
                    // 广告可见才会产生曝光，否则将无法产生收益。
                    frame_gdt_ad.addView(nativeExpressADView);
                    nativeExpressADView.render();
                }

                @Override
                public void onRenderFail(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADExposure(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADClicked(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADClosed(NativeExpressADView nativeExpressADView) {
                    // 当广告模板中的关闭按钮被点击时，广告将不再展示。NativeExpressADView也会被Destroy，释放资源，不可以再用来展示。
                    if (frame_gdt_ad != null && frame_gdt_ad.getChildCount() > 0) {
                        frame_gdt_ad.removeAllViews();
                        frame_gdt_ad.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

                }

                @Override
                public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

                }
            });
            mADManager.loadAD(1);
        }
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> adList) {
        mAdViewList = adList;
        addNum();
        /*for (int i = 0; i < mAdViewList.size(); i++) {
            int position = FIRST_AD_POSITION;
            if (position < data.size()) {
                // 广点通信息流
                int randNum = (int) (1 + Math.random() * 3) + 3; // 4 - 6
                NativeExpressADView view = mAdViewList.get(i);
                mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                adapter.addADViewToPosition(position, mAdViewList.get(i));
                FIRST_AD_POSITION += randNum;
            }
        }
        adapter.notifyDataSetChanged();*/
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        LogUtils.d("点击广告了。。。");
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        if (adapter != null) {
            int removedPosition = mAdViewPositionMap.get(nativeExpressADView);
            adapter.removeADView(removedPosition, nativeExpressADView);
        }
    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public ReadRewardPresenter createPresenter() {
        return new ReadRewardPresenter();
    }

    private Boolean checkIsVisible(Context context, View view) {
        // 如果已经加载了，判断广告view是否显示出来，然后曝光
        int screenWidth = getScreenMetrics(context).x;
        int screenHeight = getScreenMetrics(context).y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            //view已不在屏幕可见区域;
            return false;
        }
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    private Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }
}

