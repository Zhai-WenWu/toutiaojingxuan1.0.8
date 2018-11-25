package com.deshang.ttjx.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.deshang.ttjx.framework.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13364 on 2017/4/18.
 */

public class SendUtil {
    private static final String today = "com.ss.android.article.news";
    private static final String tencentnews = "com.tencent.news";
    private static final String dayday = "com.tencent.reading";
    private static final String qq = "com.tencent.mobileqq";
    private static final String sina = "com.sina.weibo";
    private static final String baidu = "com.baidu.searchbox";
    private static final String qq_browser = "com.tencent.mtt";
    private static final String uc_browser = "com.UCMobile";
    private static final String oppo_browser = "com.android.browser"; // 需要判断手机是否是oppo
    private static final String vivo_browser = "com.vivo.browser";

    public static List<String> shareList;
    private static List<String> defaultShareList;

    /**
     * @return 分享源
     */
    public static String getToWho(Context context) {

        setDefault();

        if (shareList == null) {
            shareList = new ArrayList<>();
        }
        if (shareList.size() == 0) {
            shareList.addAll(defaultShareList);
        }

        String pck = "";

        for (String share : shareList) {
            if (isHave(context, share)) {
                pck = share;
                break;
            } else {
                continue;
            }
        }

        if (null == pck || "".equals(pck)) {
            pck = "";
        }

//        String pck = "";
//        if (isHave(context, today)) {
//            pck = today;
//        } else if (isHave(context, tencentnews)) {
//            pck = tencentnews;
//        } else if (isHave(context, dayday)) {
//            pck = dayday;
//        } else if (isHave(context, qq)) {
//            pck = qq;
//        } else if (isHave(context,sina )) {
//            pck = sina;
//        } else if (isHave(context, baidu)) {
//            pck = baidu;
//        } else if (isHave(context, qq_browser)) {
//            pck = qq_browser;
//        } else if (isHave(context, uc_browser)) {
//            pck = uc_browser;
//        }  else if (isHave(context, oppo_browser)) {
//            pck = oppo_browser;
//        } else if (isHave(context, vivo_browser)) {
//            pck = vivo_browser;
//        }  else {
//            pck = "";
//        }

        return pck;
    }


    private static boolean isHave(Context context, String pck) {


        boolean isHave = false;
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> list = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : list) {
            if (pck.equals(packageInfo.packageName)) {
                if ("com.android.browser".equals(pck)) {
                    if (ProjectUtils.getDeviceBrand().equalsIgnoreCase("oppo")) {
                        isHave = true;
                    } else {
                        continue;
                    }
                } else {
                    isHave = true;
                }
                break;
            } else {
                continue;
            }
        }

        return isHave;

    }

    private static void setDefault() {
        if (null == defaultShareList) {
            defaultShareList = new ArrayList<>();
        } else {
            defaultShareList.clear();
        }

        defaultShareList.add(qq);
        defaultShareList.add(tencentnews);
        defaultShareList.add(dayday);
        defaultShareList.add(today);
        defaultShareList.add(uc_browser);
        defaultShareList.add(qq_browser);
        defaultShareList.add(vivo_browser);
        defaultShareList.add(oppo_browser);
        defaultShareList.add(sina);
        defaultShareList.add(baidu);

    }

}
