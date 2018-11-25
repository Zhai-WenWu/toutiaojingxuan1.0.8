package com.deshang.ttjx.ui.main.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.NetWorkUtil;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.TabBar;
import com.deshang.ttjx.ui.login.activity.NewLoginActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.bean.UpdateBean;
import com.deshang.ttjx.ui.main.fragment.New_Tab2Fragment;
import com.deshang.ttjx.ui.main.fragment.Tab1_1Fragment;
import com.deshang.ttjx.ui.main.fragment.Tab4Fragment;
import com.deshang.ttjx.ui.main.fragment.VideoFragment;
import com.deshang.ttjx.ui.main.presenter.MainPresenter;
import com.deshang.ttjx.ui.main.view.MainView;
import com.deshang.ttjx.ui.mywidget.AlertDialog;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.dialog.ActivationDialog;
import com.deshang.ttjx.ui.mywidget.dialog.BindPhoneDialog;
import com.deshang.ttjx.ui.mywidget.dialog.NewAppUpDataDialog;
import com.deshang.ttjx.ui.mywidget.dialog.NewUserGXDialog;
import com.deshang.ttjx.ui.mywidget.dialog.NotLoginDialog;
import com.deshang.ttjx.ui.mywidget.dialog.ReceiveOnlineRedPacketDialog;
import com.deshang.ttjx.ui.mywidget.dialog.ReceiveRedPacketOnWayDialog;
import com.deshang.ttjx.ui.mywidget.dialog.RedPacketOnWayDialog;
import com.deshang.ttjx.ui.mywidget.dialog.SignDialog;
import com.deshang.ttjx.ui.tab1.bean.IsSignBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;
import com.deshang.ttjx.ui.tab1.bean.SignBean;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.shuzilm.core.Main;
import mvp.cn.util.DateUtil;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends MvpSimpleActivity<MainView, MainPresenter> implements MainView {

    private final int DOWN_ERROR = 100;
    private static final int TIME = 1000;
    private static final int TIME_TEN = 600;
    private static final String startTime = "06:00:00";
    private static final String endTime = "22:00:00";

    @BindView(R.id.m_frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.m_bottom)
    TabBar mBottom;
    @BindView(R.id.time)
    TextView time;
    //    @BindView(R.id.rl_time)
//    RelativeLayout rlTime;
    @BindView(R.id.image_red)
    ImageView imageRed;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private List<Fragment> fragments = new ArrayList<>();
    private Tab1_1Fragment tab1;
    private VideoFragment videoFragment;
    private New_Tab2Fragment tab2;
    private Tab4Fragment tab4;
    //    private TranslateAnimation animation;
    private boolean CAN_RECEIVE = false; // 控制红包动画开关
    private Fragment mCurrentFragment = null; // 记录当前显示的Fragment
    private NotLoginDialog notLoginDialog;
    private NewUserGXDialog userGXDialog;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
    }

    public void initView() {

        if (JPushInterface.isPushStopped(this.getActivity())) {
            JPushInterface.resumePush(getApplicationContext());
        }

        if (NetWorkUtil.isNetWorkAvailable(this)) {
            try {
                Main.setConfig("apiKey", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAO8Axe9E3mpuFMKVoQbTJy8T03rddw4zi4vSJPGcM0K3DZFmg66ZtOo7t52OSFfM4ssOA1H4p70azLf7aXsNjyMCAwEAAQ==");
                Main.go(MainActivity.this, Constants.CHANNEL_TYPE, Constants.CHANNEL_TYPE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showToast("请检查网络连接");
        }
        if (!SharedPrefHelper.getInstance().getOnLine()) {
            // 没有上线
            mBottom.setVisibilityGone();
//            rlTime.setVisibility(View.GONE);
        }
        fm = getSupportFragmentManager();

        // 这段是在线红包的动画效果 在线红包已经去掉 所以无用
        /*animation = new TranslateAnimation(3, -3, 0, 0);
        animation.setInterpolator(new CycleInterpolator(1));
        animation.setDuration(500);
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setRepeatMode(Animation.REVERSE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (CAN_RECEIVE && isTop == 0) {
//                    rlTime.startAnimation(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });*/

        tab1 = new Tab1_1Fragment();
        videoFragment = new VideoFragment();
        tab2 = new New_Tab2Fragment();
        tab4 = new Tab4Fragment();
        fragments.add(tab1);
        fragments.add(videoFragment);
        fragments.add(tab2);
        fragments.add(tab4);
        //默认选中的界面
        mBottom.setOnItemChangedListener(onBottomItemClickListener);
        switchFragment(0);
        getVersionData();
        getData();
        // 如果targetSDKVersion >= 23，就要申请好权限。如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        }

        /*if (SharedPrefHelper.getInstance().getOnLine()) {
            if (SharedPrefHelper.getInstance().getNewUser()) {
                // 新用户
                if (userGuideDialog != null && userGuideDialog.isShowing()) {
                    return;
                }
                userGuideDialog = new NewUserGuideDialog(this);
                userGuideDialog.setOnClickListener(new NewUserGuideDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        userGuideDialog.dismiss();
                        switchFragment(2);
                    }
                });
                userGuideDialog.show();
            }
        }*/
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DOWN_ERROR) {
                //下载apk失败
                ReceiveGoldToast.makeToast(MainActivity.this, "下载新版本失败").show();
                getVersionData();
            } else if (msg.what == 1) {
                if (notLoginDialog != null && notLoginDialog.isShowing()) {
                    notLoginDialog.dismiss();
                }
            } else {
                mBottom.checkTab(Constants.TabPosition);
            }
        }
    };

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
//            fetchSplashAD(this, container, skipView, Constants.APPID, getPosId(), this, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024) {
            if (!hasAllPermissionsGranted(grantResults)) {
                // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                ReceiveGoldToast.makeToast(this, "缺少必要的权限！请点击\"权限\"，打开所需要的权限，否则有可能影响应用的正常使用").show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        } else if (requestCode == 1001) {
            if (!hasAllPermissionsGranted(grantResults)) {
                // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
                ReceiveGoldToast.makeToast(MainActivity.this, "请在权限管理中，打开“存储权限”,否则无法更新").show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    // 转换Fragment
    public void switchFragment(int position) {
        switch (position) {
            case 0:
                UserPathUtils.commitUserPath(1);
                break;

            case 1:
                UserPathUtils.commitUserPath(2);
                break;

            case 2:
                UserPathUtils.commitUserPath(4);
                break;

            case 3:
                UserPathUtils.commitUserPath(5);
                break;
        }
        Constants.TabPosition = position;
        Fragment to = fragments.get(position);
        handler1.sendEmptyMessage(0);
        if (mCurrentFragment == null) {
            ft = fm.beginTransaction();
            ft.add(R.id.m_frameLayout, to).show(to);
            ft.commitAllowingStateLoss();
            mCurrentFragment = to;
            return;
        }
        if (mCurrentFragment != to) {
            ft = fm.beginTransaction();
            if (!to.isAdded()) {
                // 没有添加过:
                // 隐藏当前的，添加新的，显示新的
                ft.hide(mCurrentFragment).add(R.id.m_frameLayout, to).show(to);
            } else {
                // 隐藏当前的，显示新的
                ft.hide(mCurrentFragment).show(to);
            }
            mCurrentFragment = to;
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 底部导航栏的点击
     * 未登录状态下
     */
    TabBar.OnItemChangedListener onBottomItemClickListener = new TabBar.OnItemChangedListener() {
        @Override
        public void onItemChecked(int position) {
            if (Constants.TabPosition == position) {
                if (position == 0) {
                    tab1.refreshNews();
                }
                return;
            }
            if ((SharedPrefHelper.getInstance().getToken() == null || "".equals(SharedPrefHelper.getInstance().getToken()))) {
                if (position != 0 && position != 1) {
                    // 游客
                    Intent intent = new Intent(MainActivity.this, NewLoginActivity.class);
                    startActivity(intent);
                } else {
                    switchFragment(position);
                }
            } else {
                switchFragment(position);
            }
        }
    };

    private Timer timer, timer1;
    private TimerTask task, task1;
    private int a, b;
    private int isOpen = 0;
    private boolean isFirst = true;

    @OnClick({R.id.rl_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_time:
                UserPathUtils.commitUserPath(3);
                if (SharedPrefHelper.getInstance().getToken() == null || "".equals(SharedPrefHelper.getInstance().getToken())) {
                    // 未登录状态
                    Intent intent = new Intent(MainActivity.this, NewLoginActivity.class);
                    startActivity(intent);
                    break;
                }
                if (Constants.TIME_FIVE <= 0) {
                    // 查询资格
                    getPresenter().canReceiveRedPacket();
                } else {
                    // 红包还在路上弹窗
                    if (isOpen == 0) {
                        getPresenter().getRecommendData(2);
                    } else {
                        getPresenter().getRecommendData(3);
                    }
                }
                break;
        }
    }

    // 开始计时
    public void startTimer() {
        if (null == timer) {
            timer = new Timer();
            if (null != task) {
                task.cancel();
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    if (Constants.IS_BACKGROUND) {
                        // 处于后台状态
                    } else {
                        // 当时间大于1秒的时候才递减，否则暂停时间递减。
                        if (Constants.TIME_FIVE > 0) {
                            Constants.TIME_FIVE--;
                            SharedPrefHelper.getInstance().setTime(Constants.TIME_FIVE);
                        }
                        // 当前页面处于前台时更新UI
                        if (isTop == 0) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }
            };
            timer.schedule(task, 0, TIME);
        }
    }

    // 停止计时
    public void stopTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    private boolean betweenTime = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // 22 - 6点之间
                CAN_RECEIVE = false;
                time.setText("");
                if (betweenTime) {
                    imageRed.setImageDrawable(getResources().getDrawable(R.mipmap.red_rain_gray_packet));
                    betweenTime = false;
                }
                stopTimer();
                Constants.TIME_FIVE = TIME_TEN;
            } else if (msg.what == 2) {
                // 6 - 22点之间
                CAN_RECEIVE = false;
                Constants.TIME_FIVE = msg.arg1;
                startTimer();
                if (!betweenTime) {
                    imageRed.setImageDrawable(getResources().getDrawable(R.mipmap.red_rain_red_packet));
                    betweenTime = true;
                }
            } else {
                if (Constants.CAN_RECEIVE) {
                    // 当日第一次登陆
                    Constants.TIME_FIVE = 0;
                    SharedPrefHelper.getInstance().setTime(0);
                    redPacketAnimation();
                    CAN_RECEIVE = true;
                    time.setText("领取");
                    if (onWayDialog != null && onWayDialog.isShowing()) {
                        onWayDialog.setTime("00:00");
                    }
                    time.setTextColor(getResources().getColor(R.color.orange));
                    stopTimer();
                    if (!betweenTime) {
                        imageRed.setImageDrawable(getResources().getDrawable(R.mipmap.red_rain_red_packet));
                        betweenTime = true;
                    }
                    return;
                }
                if (Constants.TIME_FIVE <= 0) {
                    // 倒计时变成0
                    redPacketAnimation();
                    CAN_RECEIVE = true;
                    time.setText("领取");
                    if (onWayDialog != null && onWayDialog.isShowing()) {
                        onWayDialog.setTime("00:00");
                    }
                    if (!betweenTime) {
                        imageRed.setImageDrawable(getResources().getDrawable(R.mipmap.red_rain_red_packet));
                        betweenTime = true;
                    }
                    time.setTextColor(getResources().getColor(R.color.orange));
                    stopTimer();
                } else {
                    // 正常倒计时
                    if (!betweenTime) {
                        imageRed.setImageDrawable(getResources().getDrawable(R.mipmap.red_rain_red_packet));
                        betweenTime = true;
                    }
                    CAN_RECEIVE = false;
                    time.setTextColor(getResources().getColor(R.color.white));
                    if (Constants.TIME_FIVE % 60 > 9) {
                        time.setText("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                        // 红包雨领取成功新闻弹窗设置时间
                        /*// 红包雨领取成功收徒弹窗设置时间
                        if (apprenticeDialog != null && apprenticeDialog.isShowing()) {
                            apprenticeDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                        }
                        // 红包雨领取成功分享弹窗设置时间
                        if (shareDialog != null && shareDialog.isShowing()) {
                            shareDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                        }*/
                        // 红包雨倒计时新闻弹窗设置时间
                        if (onWayDialog != null && onWayDialog.isShowing()) {
                            onWayDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                        }
                    } else {
                        time.setText("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                        /*// 红包雨领取成功 收徒弹窗 设置时间
                        if (apprenticeDialog != null && apprenticeDialog.isShowing()) {
                            apprenticeDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                        }
                        // 红包雨领取成功 分享弹窗 设置时间
                        if (shareDialog != null && shareDialog.isShowing()) {
                            shareDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                        }*/
                        // 红包雨 倒计时新闻弹窗 设置时间
                        if (onWayDialog != null && onWayDialog.isShowing()) {
                            onWayDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                        }
                    }
                }
            }
        }
    };

    // 开始计时  用于判断是否在6点到22点之间
    public void startTimer1() {
        if (null == timer1) {
            timer1 = new Timer();
            if (null != task) {
                task1.cancel();
            }
            task1 = new TimerTask() {
                @Override
                public void run() {
                    a = ProjectUtils.compare_date(DateUtil.getHHmmss(), startTime);
                    b = ProjectUtils.compare_date(DateUtil.getHHmmss(), endTime);
                    if (a == 0) {
                        // 6点整
                        isOpen = 1;
                        Message msg = new Message();
                        msg.what = 2;
                        msg.arg1 = SharedPrefHelper.getInstance().getTime();
                        handler.sendMessage(msg);
                    } else if (b == 0) {
                        // 22点整
                        isOpen = 0;
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else if (a == 1 && b == -1) {
                        // 22点-6点之间  计时时间
                        if (isOpen == 2) {
                            //  当日第一次  计时清空
                            LogUtils.d("当日第一次！");
                            Message msg = new Message();
                            msg.what = 2;
                            msg.arg1 = 0;
                            handler.sendMessage(msg);
                        } else if (isOpen == 0) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.arg1 = SharedPrefHelper.getInstance().getTime();
                            handler.sendMessage(msg);
                        }
                        isOpen = 1;
                    } else {
                        if (isOpen != 0 || isFirst) {
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                            isOpen = 0;
                        }
                    }
                    isFirst = false;
                }
            };
            timer1.schedule(task1, 0, TIME);
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

    // 当前视图是否可见  可见更新时间每秒，不可见只计时不更新时间UI
    private int isTop = 0;

    @Override
    public void onPause() {
        super.onPause();
        isTop = 1;
        LogUtils.d("Tab1 onPause");
    }

    // 红包左右晃动动画
    private void redPacketAnimation() {
//        if (SharedPrefHelper.getInstance().getOnLine())
//            rlTime.startAnimation(animation);
    }

    // 查询资格
    @Override
    public void canReceiveRedPacket(BaseBean bean) {
        if (bean.errcode == 0) {
            getPresenter().receiveRedPacket();
        } else if (bean.errcode == -2) {
            showToast(bean.errinf);
        } else {
            // 获取推荐文章
            getPresenter().getRecommendData(0);
        }
    }

    // 获取推荐文章
    @Override
    public void getRecommendSuccess(RecommendBean bean, int type) {
        if (bean.getErrcode() == 0) {
            switch (type) {
                case 0:
                    //  激活弹窗
                    ActivationDialog activationDialog = new ActivationDialog(getActivity(), bean);
                    activationDialog.show();
                    break;

                case 1:
                    //  领取成功弹窗
                    break;

                case 2:
                    //  还在路上弹窗
                    RedPacketOnWayDialog redPacketOnWayDialog = new RedPacketOnWayDialog(getActivity(), bean);
                    redPacketOnWayDialog.show();
                    break;

                case 3:
                    //  红包雨倒计时弹窗
                    onWayDialog = new ReceiveRedPacketOnWayDialog(getActivity(), bean);
                    onWayDialog.show();
                    if (Constants.TIME_FIVE % 60 > 9) {
                        onWayDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                    } else {
                        onWayDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                    }
                    break;
            }

        } else {
            showToast(bean.getErrinf());
        }
    }

    //    private ReceiveRedPacketDialog dialog; // 领取成功新闻弹窗
    //    private ReceiveRedPacketApprenticeDialog apprenticeDialog; // 领取成功收徒弹窗
//    private ReceiveRedPacketShareDialog shareDialog; // 领取成功分享弹窗
    private ReceiveRedPacketOnWayDialog onWayDialog; // 红包还在路上
    private int receiverGoldNum; // 领取金币

    // 领取红包
    @Override
    public void receiveRedPacket(ReceiveRedPacketBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;

            receiverGoldNum = bean.getReturn().getGold();
            ReceiveOnlineRedPacketDialog dialog = new ReceiveOnlineRedPacketDialog(this, receiverGoldNum);
            dialog.show();
            /*// 随机弹窗  新闻/收徒/分享 每种1:1:1
            int number = (int) (Math.random() * 3);
            LogUtils.d("随机数：" + number);
            if (number == 0) {
                // 弹出收徒弹窗
                apprenticeDialog = new ReceiveRedPacketApprenticeDialog(this, receiverGoldNum);
                apprenticeDialog.show();
                if (Constants.TIME_FIVE % 60 > 9) {
                    apprenticeDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                } else {
                    apprenticeDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                }
            } else if (number == 1) {
                // 弹出新闻弹窗
                getPresenter().getRecommendData(1);
            } else {
                // 弹出去分享弹窗
                shareDialog = new ReceiveRedPacketShareDialog(this, receiverGoldNum);
                shareDialog.setOnClickListener(new ReceiveRedPacketShareDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        getPresenter().getShareUrl();
                    }
                });
                shareDialog.show();
                if (Constants.TIME_FIVE % 60 > 9) {
                    shareDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":" + Constants.TIME_FIVE % 60);
                } else {
                    shareDialog.setTime("0" + Constants.TIME_FIVE / 60 + ":0" + Constants.TIME_FIVE % 60);
                }
            }*/
            Constants.TIME_FIVE = TIME_TEN;
            Constants.CAN_RECEIVE = false;
            startTimer();
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void commitLoginState(BaseBean bean) {
        if (bean == null)
            return;

        if (bean.errcode == 0) {
            // 当日第一次登录
            getPresenter().commitLoginTime();
            LogUtils.d("当日第一次登录");
            CAN_RECEIVE = true;
            Constants.CAN_RECEIVE = true;
            isOpen = 2;
            Constants.TIME_FIVE = 0;
            SharedPrefHelper.getInstance().setTime(0);

            // 隐藏每日弹出的分享弹窗
            /*if (SharedPrefHelper.getInstance().getOnLine()) {
                // 审核过了以后再弹窗
                DayShowDialog dayShowDialog = new DayShowDialog(getActivity());
                dayShowDialog.setOnClickListener(new DayShowDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        getPresenter().getShareUrl();
                    }
                });
                dayShowDialog.show();
            }*/
        }
    }

    @Override
    public void commitLoginTime(BaseBean bean) {

    }

    @Override
    public void getShareUrl(ShareUrlBean bean) {
        if (bean.getErrcode() == 0) {
            Intent intent = new Intent(this, NewsDetailActivity.class);
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

    private SignDialog signDialog; // 签到弹窗
    //private NewUserRewardDialog rewardDialog; // 新用户奖励弹窗
//    private NewUserGuideDialog userGuideDialog;

    // 签到数据
    @Override
    public void getSignData(SignBean bean) {
        if (bean.getErrcode() == 0) {
            if (signDialog != null && signDialog.isShowing()) {
                return;
            }
            signDialog = new SignDialog(MainActivity.this, bean.getSign_day(), bean.getReturn());
            signDialog.show();
        }
    }

    @Override
    public void getIsSignData(IsSignBean bean) {
        if (bean.getSigntype() == 0) {
            // 还未签到  去签到
            getPresenter().getSignData();
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    public void getData() {
        // 是否是第一次登陆
        if (getIntent().getIntExtra("IsFirstLogin", 0) == 1) {
            BindPhoneDialog dialog = new BindPhoneDialog(this);
            dialog.show();
        }
//        startTimer1();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    private boolean closePid = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                ReceiveGoldToast.makeToast(this, "再按一次退出头条精选").show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefHelper.getInstance().getNewAppFirst() == 0 && SharedPrefHelper.getInstance().getOnLine() && !"".equals(SharedPrefHelper.getInstance().getToken())) {
            NewAppUpDataDialog dataDialog = new NewAppUpDataDialog(MainActivity.this);
            dataDialog.show();
        }
        if (Constants.turn_to_other_tab != 0 && !"".equals(SharedPrefHelper.getInstance().getToken()) && SharedPrefHelper.getInstance().getOnLine()) {
            // 在其他页面回来时 看是否需要跳转到其他Tab页面
            if (Constants.turn_to_other_tab < fragments.size()) {
                // 如果不越界就跳转 越界就拉倒
                switchFragment(Constants.turn_to_other_tab);
            }
            Constants.turn_to_other_tab = 0;
        }
        if (!"".equals(SharedPrefHelper.getInstance().getUserId())) {
            /*if (SharedPrefHelper.getInstance().getOnLine()) {
                // 获取签到信息
                getPresenter().getIsSignData();
            }*/
            // 提交登录状态
            getPresenter().commitLoginState();
        }
        if (Constants.IS_CHOOSE_FIRST) {
            // 退出登录时 默认选中首页
            Constants.IS_CHOOSE_FIRST = false;
            switchFragment(0);
        }
        /*if (Constants.IS_LOGIN == 2) {
            // 在线红包计时  已无用
            a = ProjectUtils.compare_date(DateUtil.getHHmmss(), startTime);
            b = ProjectUtils.compare_date(DateUtil.getHHmmss(), endTime);
            if (a >= 0 && b <= 0) {
                // 计时时区
//                startTimer();
            }
        }
        isTop = 0;
        if (CAN_RECEIVE) {
            redPacketAnimation();
        }*/
        if (Constants.width == 0) {
            // 测量手机屏幕的像素点  以备不时之需
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            Constants.width = display.getWidth();
            Constants.height = display.getHeight();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        switchFragment(0);
    }

    @Override
    public void onDestroy() {
        if (closePid) {
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
        JPushInterface.stopPush(getApplicationContext());
        stopTimer();
        stopTimer1();
        super.onDestroy();
    }

    /**
     * 获取版本信息
     */
    public void getVersionData() {
        Observable request = RetrofitUtils.getInstance().getVersionData();
        Subscriber<UpdateBean> upDataSubscriber = new Subscriber<UpdateBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final UpdateBean bean) {
                if (bean.getErrCode() == 0) {
                    // isOnLine == 1 时没上线 隐藏该要隐藏的功能
                    if (bean.getIsOnLine() != 2) {
                        mBottom.setVisibilityVisible();
//                        rlTime.setVisibility(View.VISIBLE);
                    } else {
                        // 没有上线
                        mBottom.setVisibilityGone();
//                        rlTime.setVisibility(View.GONE);
                    }
                    SharedPrefHelper.getInstance().setOnLine(bean.getIsOnLine() != 2);
                    SharedPrefHelper.getInstance().setShareUrl(bean.getShare_url());
                    SharedPrefHelper.getInstance().setVideoShareUrl(bean.getVideo_url());
                    SharedPrefHelper.getInstance().setApprenticeUrl(bean.getApprentice_url());
                    SharedPrefHelper.getInstance().setSubhead(bean.getSubhead());
                    SharedPrefHelper.getInstance().setSharehead(bean.getSharehead());
                    SharedPrefHelper.getInstance().setStartSecond(bean.getRead_slide1());
                    SharedPrefHelper.getInstance().setEndSecond(bean.getRead_slide2());
                    SharedPrefHelper.getInstance().setVideoWatchTimeAddVote(bean.getRead_video_vote());

                    SharedPrefHelper.getInstance().setVideoEffectiveNumber(bean.getVideo_effective_number());

                    if (ProjectUtils.check(bean.getVersion(), ProjectUtils.getVersion(getActivity()))) { // 需要升级
                        // 发现新版本，提示用户更新
                        if (bean.getForce() == 0) {  // 不强制更新
                            new AlertDialog(getActivity()).builder()
                                    .setTitle("提示")
                                    .setMsg(bean.getDesc())
                                    .setCancelable(true)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            updateApk(bean.getAppurl());
                                        }
                                    }).setNegativeButton("取消", null).show();
                        } else if (bean.getForce() == 1) { // 强制更新
                            new AlertDialog(getActivity()).builder()
                                    .setDismiss(false)
                                    .setTitle("提示")
                                    .setMsg(bean.getDesc())
                                    .setCancelable(false)
                                    .setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            updateApk(bean.getAppurl());
                                        }
                                    }).show();
                        }
                    } else {
                        // 判断是否是第一次打开App 第一次弹出用户引导弹窗
                        /*if (SharedPrefHelper.getInstance().getFirst() == 0 && SharedPrefHelper.getInstance().getOnLine()) {
                            SharedPrefHelper.getInstance().setFirst(1);
                            HaloDialog dialog = new HaloDialog(MainActivity.this);
                            dialog.show();
                        }*/
                        if ("".equals(SharedPrefHelper.getInstance().getToken()) && SharedPrefHelper.getInstance().getOnLine()) {
                            // 未登录状态
                            userGXDialog = new NewUserGXDialog(getActivity());
                            userGXDialog.show();
                            userGXDialog.setOnGetMoreClickListener(new NewUserGXDialog.OnGetMoreClickListener() {
                                @Override
                                public void onWithdrawalClick() {
                                    startActivity(new Intent(getActivity(), NewLoginActivity.class));
                                }
                            });
                            userGXDialog.setOnCloseClickListener(new NewUserGXDialog.OnCloseClickListener() {
                                @Override
                                public void onCloseClick() {
                                    userGXDialog.dismiss();
                                }
                            });
                            //handler1.sendEmptyMessageDelayed(1, 2000);
                        }
                    }
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    // 更新APK
    private void updateApk(String updateUrl) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            } else {
                downLoadApk(updateUrl);
            }
        } else {
            downLoadApk(updateUrl);
        }
    }

    /**
     * 从服务器中下载APK
     */
    protected void downLoadApk(final String url) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("安装包下载中...");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = ProjectUtils.getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler1.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(this, "com.deshang.ttjx.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
