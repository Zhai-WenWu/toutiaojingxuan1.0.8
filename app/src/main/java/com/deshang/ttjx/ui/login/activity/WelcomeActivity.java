package com.deshang.ttjx.ui.login.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.baidu.mobstat.StatService;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.SystemBarTintManager;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.tab1.bean.ADImageBean;
import com.deshang.ttjx.ui.tab4.activity.WebViewActivity;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.shuzilm.core.Main;
import rx.Observable;
import rx.Subscriber;

/**
 * 闪屏页
 * Created by L on 2017/12/5.
 */

public class WelcomeActivity extends AppCompatActivity implements SplashADListener {

    @BindView(R.id.image_ad)
    ImageView image_ad; // 广告图片
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.adsRl)
    RelativeLayout adsRl;
    @BindView(R.id.splash_container)
    ViewGroup container;

//    private ADImageBean adImageBean;

    /**
     * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
     * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
     * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值（单位ms）
     **/
    private int minSplashTimeWhenNoAD = 2000;
    /**
     * 记录拉取广告的时间
     */
    private long fetchSplashADTime = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String SKIP_TEXT = "点击跳过 %d";
    public boolean canJump = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        setContentView(R.layout.activity_welcome);
        // 百度统计
        StatService.start(this);
        ButterKnife.bind(this);
        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(this, container, time, Constants.GDT_APP_ID, Constants.GDT_KAI_PING_ID, this, 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
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
            fetchSplashAD(this, container, time, Constants.GDT_APP_ID, Constants.GDT_KAI_PING_ID, this, 0);
        } else {
            // 缺少权限
            handler1.sendEmptyMessageDelayed(1, 3000);
        }
    }


    private void initView() {
        setTranslucentStatus(R.color.gray_light);
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorResId);// 状态栏无背景
    }

    private void next() {
        if (canJump) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                goNext();
            }
        }
    };

    // 下一个跳转页面
    private void goNext() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.image_ad, R.id.time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_ad:
                /*if (adImageBean == null) {
                    break;
                }
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("Type", 3); // 代表是从闪屏页跳转过去的
                intent.putExtra("loadUrl", adImageBean.getReturn().getUrl());
                startActivity(intent);
                finish();*/
                break;

            case R.id.time: // 点击跳过广告
                goNext();
                break;
        }
    }

    /**
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        fetchSplashADTime = System.currentTimeMillis();
        SplashAD splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    //  启动页禁用返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onADDismissed() {
        next();
    }

    @Override
    public void onNoAD(AdError error) {
        LogUtils.d(String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(), error.getErrorMsg()));
        /**
         * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
         * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
         * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值
         **/
        long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
        long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD
                - alreadyDelayMills;//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
        // 计算出还需要延时多久
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, shouldDelayMills);
    }

    @Override
    public void onADPresent() {
        image_ad.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {

    }

    @Override
    public void onADTick(long millisUntilFinished) {
        // 倒计时
        time.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
    }

    @Override
    public void onADExposure() {

    }
}
