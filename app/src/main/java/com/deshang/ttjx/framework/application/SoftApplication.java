package com.deshang.ttjx.framework.application;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;

import com.baidu.mobstat.StatService;
import com.deshang.ttjx.ui.broadcast.JPushMessageReceiver;
import com.geetest.deepknow.DPAPI;
import com.google.gson.Gson;
import com.deshang.ttjx.framework.config.AppConfig;
import com.deshang.ttjx.framework.config.AppInfo;
import com.deshang.ttjx.framework.config.UserInfo;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.shuzilm.core.Main;
import mvp.cn.common.QuickApplication;
import mvp.cn.util.DateUtil;
import mvp.cn.util.NetUtil;
import mvp.cn.util.ToastUtil;


public class SoftApplication extends QuickApplication {
    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> unDestroyActivityList = new ArrayList<>();
    public static SoftApplication softApplication;
    private static AppInfo appInfo;
    private static UserInfo userInfo;
    private static boolean isLogin;// 判断是否已经登录
    private static Context context;
    private static int authStatus;
    private double longitude;
    private double latitude;
    private RefWatcher refWatcher;
    public static final String SMKEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAO8Axe9E3mpuFMKVoQbTJy8T03rddw4zi4vSJPGcM0K3DZFmg66ZtOo7t52OSFfM4ssOA1H4p70azLf7aXsNjyMCAwEAAQ==";

    @Override
    public void onCreate() {
        super.onCreate();
        softApplication = this;
        refWatcher = LeakCanary.install(this);
        context = getApplicationContext();


        // 打开调试开关，可以查看logcat日志。版本发布前，为避免影响性能，移除此代码
        StatService.setDebugOn(false);

        // 开启自动埋点统计，为保证所有页面都能准确统计，建议在Application中调用。
        // 第三个参数：autoTrackWebview：
        // 如果设置为true，则自动track所有webview；如果设置为false，则不自动track webview，
        // 如需对webview进行统计，需要对特定webview调用trackWebView() 即可。
        // 重要：如果有对webview设置过webchromeclient，则需要调用trackWebView() 接口将WebChromeClient对象传入，
        // 否则开发者自定义的回调无法收到。
        StatService.autoTrace(this, true, false);
        // ShareSDK 初始化
        MobSDK.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());

        // 初始化数盟SDk
        Main.init(getApplicationContext(), SMKEY);
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(context);            // 初始化 JPush

        DPAPI.getInstance(this, null);
    }

    //返回
    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * leak检测内存泄露
     *
     * @return
     */
    @Override
    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    /**
     * 实例化AppInfo
     */
    private AppInfo initAppInfo() {
        AppInfo appInfo = AppConfig.getAppConfigInfo(softApplication);
        appInfo.imei = NetUtil.getIMEI(getApplicationContext());
        appInfo.imsi = NetUtil.getIMSI(getApplicationContext()) == null ? "" : NetUtil.getIMSI(getApplicationContext());
        appInfo.osVersion = getOSVersion();
        appInfo.appVersionCode = getAppVersionCode();
        return appInfo;
    }

    /**
     * 得到系统的版本号
     *
     * @return
     */
    public String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 得到应用的版本号
     *
     * @return
     */
    public int getAppVersionCode() {
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 得到应用的版本号
     *
     * @return
     */
    public String getAppVersionName() {
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String versionCode = "";
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取Assert文件夹中的配置文件信息
     *
     * @return
     */
    public AppInfo getAppInfo() {
        return appInfo;
    }

    public String getFrom() {
        return appInfo == null ? "" : appInfo.os;
    }

    public String getApiUser() {
        return appInfo == null ? "" : appInfo.api_user;
    }

    public String getApiPassword() {
        return appInfo == null ? "" : appInfo.api_pwd;
    }

    /**
     * 得到请求头JsonObject
     *
     * @return
     */
    public String getAuthJsonObject(String jsonString) {
        try {
            String timeStamp = DateUtil.getCurrentDateTimeyyyyMMddHHmmss();
            Map<String, Object> authJsonObject = new HashMap<>();
            authJsonObject.put("app_key", appInfo.appKey);
            authJsonObject.put("imei", appInfo.imei);
            authJsonObject.put("os", appInfo.os);
            authJsonObject.put("os_version", appInfo.osVersion);
            authJsonObject.put("app_version", appInfo.appVersionCode);
//            authJsonObject.put("source_id", appInfo.sourceId);
//            authJsonObject.put("ver", appInfo.ver);
            authJsonObject.put("uid", isLogin ? userInfo.uid : "");
            authJsonObject.put("time_stamp", timeStamp);
//            authJsonObject.put("crc", CrcUtil.getCrc(timeStamp, appInfo.imei, (isLogin ? userInfo.uid : appInfo.uid), (isLogin ? passwordWithMd5 : CrcUtil.MD5(appInfo.crc)), jsonString));
//            authJsonObject.put("token", getToken(timeStamp));
            return new Gson().toJson(authJsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 退出应用
     */
    public void quit() {
        for (Activity activity : unDestroyActivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        unDestroyActivityList.clear();
        logout();
    }

    /**
     * 注销帐号
     */
    public void logout() {
        /**
         * 退出登录,清空数据
         */
        userInfo = null;
        isLogin = false;
    }

    public boolean isLogin() {
        return isLogin;
    }


}

