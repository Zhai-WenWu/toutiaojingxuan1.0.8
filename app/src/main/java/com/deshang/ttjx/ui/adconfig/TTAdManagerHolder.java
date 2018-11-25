package com.deshang.ttjx.ui.adconfig;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.ui.service.AppDownloadStatusListener;

/**
 * 可以用一个单例来保存TTAdManager实例
 */
public class TTAdManagerHolder {

    private static boolean sInit;

    public static TTAdManager getInstance() {
        TTAdManager ttAdManager = TTAdManagerFactory.getInstance(SoftApplication.getContext());
        if (!sInit) {
            synchronized (TTAdManagerHolder.class) {
                if (!sInit) {
                    doInit(ttAdManager);
                    sInit = true;
                }
            }
        }
        return ttAdManager;
    }

    private static void doInit(TTAdManager ttAdManager) {
        ttAdManager.setAppId(Constants.TOUTIAO_APP_ID) //
                .setName("头条精选_android")
                .setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_LIGHT)
                .setAllowShowNotifiFromSDK(true)
                .setAllowLandingPageShowWhenScreenLock(true)
                .openDebugMode()
                .setGlobalAppDownloadListener(new AppDownloadStatusListener(SoftApplication.getContext()))
                .setDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G);
    }
}
