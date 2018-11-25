package com.deshang.ttjx.framework.spfs;


import android.content.Context;
import android.content.SharedPreferences;

import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.network.ServerConstants;
import com.deshang.ttjx.framework.utils.LogUtils;


public class SharedPrefHelper {
    /**
     * SharedPreferences的名字
     */
    private static final String SP_FILE_NAME = "APPLICATION_SP";
    private static SharedPrefHelper sharedPrefHelper = null;
    private static SharedPreferences sharedPreferences;

    private static final String LONGITUDE = "LONGITUDE";

    private static final String LATITUDE = "LATITUDE";
    private static final String USER = "data";
    private static final String HELP = "help";

    private boolean isDebug = false;
//    private boolean isDebug = true;

    public static synchronized SharedPrefHelper getInstance() {
        if (null == sharedPrefHelper) {
            sharedPrefHelper = new SharedPrefHelper();
        }
        return sharedPrefHelper;
    }

    private SharedPrefHelper() {
        sharedPreferences = SoftApplication.softApplication.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setPhoneNumber(String phoneNumber) {
        sharedPreferences.edit().putString("phoneNumber", phoneNumber).commit();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString("phoneNumber", "");
    }


    public void setIsClick(boolean isClick) {
        sharedPreferences.edit().putBoolean("isClick", isClick).commit();
    }

    public boolean getIsClick() {
        return sharedPreferences.getBoolean("isClick", false);
    }


    /**
     * 设置UID
     *
     * @param userId
     */
    public void setUserId(String userId) {
        sharedPreferences.edit().putString("userId", userId).commit();
    }

    public String getUserId() {
//        LogUtils.d("取到的UserID：" + sharedPreferences.getString("userId", ""));
        if (isDebug) {
            return "20730";
        }
        return sharedPreferences.getString("userId", "");
    }

    /**
     * 设置TOKEN
     *
     * @param token
     */
    public void setToken(String token) {
        sharedPreferences.edit().putString("token", token).commit();
    }

    public String getToken() {
//        LogUtils.d("取到的Token：" + sharedPreferences.getString("token", ""));
        if (isDebug) {
            return "fbff08e2b50b3e4c6c4e8369c386f9d1";
        }
        return sharedPreferences.getString("token", "");
    }

    /**
     * 设置用户名
     *
     * @param userName
     */
    public void setUserName(String userName) {
        sharedPreferences.edit().putString("userName", userName).commit();
    }

    public String getUserName() {
        return sharedPreferences.getString("userName", "");
    }

    /**
     * 设置文章分享Url域名
     *
     * @param shareUrl
     */
    public void setShareUrl(String shareUrl) {
        sharedPreferences.edit().putString("shareUrl", shareUrl).commit();
    }

    public String getShareUrl() {
        LogUtils.d("分享链接：" + sharedPreferences.getString("shareUrl", ServerConstants.OUR));
        return sharedPreferences.getString("shareUrl", ServerConstants.OUR);
    }

    /**
     * 设置文章分享Url域名
     *
     * @param videoShareUrl
     */
    public void setVideoShareUrl(String videoShareUrl) {
        sharedPreferences.edit().putString("videoShareUrl", videoShareUrl).commit();
    }

    public String getVideoShareUrl() {
        LogUtils.d("视频分享链接：" + sharedPreferences.getString("videoShareUrl", ServerConstants.OUR));
        return sharedPreferences.getString("videoShareUrl", ServerConstants.OUR);
    }

    /**
     * 设置收徒分享Url域名
     *
     * @param apprenticeUrl
     */
    public void setApprenticeUrl(String apprenticeUrl) {
        sharedPreferences.edit().putString("apprenticeUrl", apprenticeUrl).commit();
    }

    public String getApprenticeUrl() {
        LogUtils.d("收徒链接：" + sharedPreferences.getString("apprenticeUrl", ServerConstants.OUR));
        return sharedPreferences.getString("apprenticeUrl", ServerConstants.OUR);
    }

    /**
     * 收徒分享副标题
     *
     * @param subhead
     */
    public void setSubhead(String subhead) {
        sharedPreferences.edit().putString("subhead", subhead).commit();
    }

    public String getSubhead() {
        LogUtils.d("收徒分享副标题：" + sharedPreferences.getString("subhead", "精选时事头条，看文章、赚分享，免费红包拿不停！"));
        return sharedPreferences.getString("subhead", "精选时事头条，看文章、赚分享，免费红包拿不停！");
    }

    /**
     * 新闻分享副标题
     *
     * @param sharehead
     */
    public void setSharehead(String sharehead) {
        sharedPreferences.edit().putString("sharehead", sharehead).commit();
    }

    public String getSharehead() {
        LogUtils.d("新闻分享副标题：" + sharedPreferences.getString("sharehead", "阅读文章领取1元现金红包"));
        return sharedPreferences.getString("sharehead", "阅读文章领取1元现金红包");
    }

    /**
     * 设置头像
     *
     * @param avatar
     */
    public void setAvatar(String avatar) {
        sharedPreferences.edit().putString("avatar", avatar).commit();
    }

    public String getAvatar() {
        return sharedPreferences.getString("avatar", "");
    }

    /**
     * 设置新闻类型
     *
     * @param newsType
     */
    public void setNewsType(String newsType) {
        sharedPreferences.edit().putString("newsType", newsType).commit();
    }

    public String getNewsType() {
        return sharedPreferences.getString("newsType", "");
    }

    /**
     * 设置当日第一次登陆后是否点击过分享奖励 规则：当天第一次登陆，分享奖励80%概率出现  点击后当日不在出现（不点击每次都是80%概率出现）
     *
     * @param isFirstLogin
     */
    public void setLoginState(boolean isFirstLogin) {
        sharedPreferences.edit().putBoolean("isFirstLogin", isFirstLogin).commit();
    }

    public boolean getLoginState() {
        return sharedPreferences.getBoolean("isFirstLogin", false);
    }

    /**
     * 设置红包雨倒计时时间
     *
     * @param time
     */
    public void setTime(int time) {
        sharedPreferences.edit().putInt("time", time).commit();
    }

    public int getTime() {
        return sharedPreferences.getInt("time", 600);
    }

    /**
     * 设置页面的缓存
     *
     * @param settingData
     */
    public void setSettingData(String settingData) {
        sharedPreferences.edit().putString("settingData", settingData).commit();
    }

    public String getSettingData() {
        return sharedPreferences.getString("settingData", "");
    }

    /**
     * 设置广告图Url
     *
     * @param adImageUrl
     */
    public void setAdImageUrl(String adImageUrl) {
        sharedPreferences.edit().putString("adImageUrl", adImageUrl).commit();
    }

    public String getAdImageUrl() {
        return sharedPreferences.getString("adImageUrl", "");
    }

    /**
     * 是否是第一次安装
     *
     * @param firstTime
     */
    public void setFirst(int firstTime) {
        sharedPreferences.edit().putInt("isFirstTime", firstTime).commit();
    }

    public int getFirst() {
        return sharedPreferences.getInt("isFirstTime", 0);
    }

    /**
     * 新APP是否是第一次打开
     *
     * @param newAppIsFirstTime
     */
    public void setNewAppFirst(int newAppIsFirstTime) {
        sharedPreferences.edit().putInt("newAppIsFirstTime", newAppIsFirstTime).commit();
    }

    public int getNewAppFirst() {
        return sharedPreferences.getInt("newAppIsFirstTime", 0);
    }

    /**
     * 是否已经上线
     *
     * @param online
     */
    public void setOnLine(boolean online) {
        sharedPreferences.edit().putBoolean("online", online).commit();
    }

    public boolean getOnLine() {
        return sharedPreferences.getBoolean("online", false);
    }

    /**
     * 是否是新用户
     *
     * @param newUser
     */
    public void setNewUser(boolean newUser) {
        sharedPreferences.edit().putBoolean("newUser", newUser).commit();
    }

    public boolean getNewUser() {
        return sharedPreferences.getBoolean("newUser", false);
    }

    /**
     * 是否第一次安装 新闻内文页
     *
     * @param FirstTimeOpen
     */
    public void setFirstTimeOpen(boolean FirstTimeOpen) {
        sharedPreferences.edit().putBoolean("FirstTimeOpen", FirstTimeOpen).commit();
    }

    public boolean getFirstTimeOpen() {
        return sharedPreferences.getBoolean("FirstTimeOpen", true);
    }

    /**
     * 我知道了问号 新闻内文页
     *
     * @param WhyState
     */
    public void setWhyState(boolean WhyState) {
        sharedPreferences.edit().putBoolean("WhyState", WhyState).commit();
    }

    public boolean getWhyState() {
        return sharedPreferences.getBoolean("WhyState", true);
    }

    /**
     * 是否第一次安装 我的页面
     *
     * @param FirstTimeTab4
     */
    public void setFirstTimeTab4(boolean FirstTimeTab4) {
        sharedPreferences.edit().putBoolean("FirstTimeTab4", FirstTimeTab4).commit();
    }

    public boolean getFirstTimeTab4() {
        return sharedPreferences.getBoolean("FirstTimeTab4", true);
    }

    /**
     * 新闻内文页分享展示次数
     *
     * @param ShareShowTime
     */
    public void setShareShowTime(int ShareShowTime) {
        sharedPreferences.edit().putInt("ShareShowTime", ShareShowTime).commit();
    }

    public int getShareShowTime() {
        return sharedPreferences.getInt("ShareShowTime", 0);
    }

    /**
     * 新闻最短滚动计时时间
     *
     * @param newsDetailStartSecond
     */
    public void setStartSecond(int newsDetailStartSecond) {
        sharedPreferences.edit().putInt("newsDetailStartSecond", newsDetailStartSecond).commit();
    }

    public int getStartSecond() {
        return sharedPreferences.getInt("newsDetailStartSecond", 0);
    }

    /**
     * 新闻最长滚动计时时间
     *
     * @param newsDetailEndSecond
     */
    public void setEndSecond(int newsDetailEndSecond) {
        sharedPreferences.edit().putInt("newsDetailEndSecond", newsDetailEndSecond).commit();
    }

    public int getEndSecond() {
        return sharedPreferences.getInt("newsDetailEndSecond", 0);
    }

    /**
     * 视频观看多长时间加一张票
     *
     * @param videoWatchTimeAddVote
     */
    public void setVideoWatchTimeAddVote(int videoWatchTimeAddVote) {
        sharedPreferences.edit().putInt("videoWatchTimeAddVote", videoWatchTimeAddVote).commit();
    }

    public int getVideoWatchTimeAddVote() {
        return sharedPreferences.getInt("videoWatchTimeAddVote", 300);
    }

    /**
     * 视频观看时长
     *
     * @param videoWatchTime
     */
    public void setVideoWatchTime(int videoWatchTime) {
        sharedPreferences.edit().putInt("videoWatchTime", videoWatchTime).commit();
    }

    public int getVideoWatchTime() {
        return sharedPreferences.getInt("videoWatchTime", 0);
    }


    /**
     * 观看视频有效次数
     *
     */
    public void setVideoEffectiveNumber(int videoEffectiveNumber) {
        sharedPreferences.edit().putInt("videoEffectiveNumber", videoEffectiveNumber).commit();
    }

    public int getVideoEffectiveNumber() {
        return sharedPreferences.getInt("videoEffectiveNumber",0);
    }

}
