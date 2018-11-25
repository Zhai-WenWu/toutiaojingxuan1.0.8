package com.deshang.ttjx.framework.network.retrofit;

import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.network.AppConstants;
import com.deshang.ttjx.framework.network.ServerConstants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.EncryptUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hh on 2017/5/12.
 */

public class RetrofitUtils implements AppConstants, ServerConstants {

    private static Retrofit retrofit, retrofit1;
    private static OkHttpClient okHttpClient;
    private static NetAPI api, api1;
    private static RetrofitUtils instance;
    private static String version = ProjectUtils.getVersion(SoftApplication.getInstance().getApplicationContext());

    private static RetrofitUtils createApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(OUR)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(NetAPI.class);
        instance = new RetrofitUtils();
        return instance;
    }

    public static RetrofitUtils getInstance() {
        if (instance == null) {
            createApi();
        }
        return instance;
    }

    private static RetrofitUtils createWalletApi() {
        retrofit1 = new Retrofit.Builder()
                .baseUrl(WALLET_HTTP)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api1 = retrofit1.create(NetAPI.class);
        return new RetrofitUtils();
    }

    // 红钻相关
    public static RetrofitUtils getWalletInstance() {
        return createWalletApi();
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    if (message.length() > 3000) {
                        for (int i = 0; i < message.length(); i += 3000) {
                            if (i + 3000 < message.length()) {
                                LogUtils.d("返回数据:" + message.substring(i, i + 3000));
                            } else {
                                LogUtils.d("返回数据:" + message.substring(i, message.length()));
                            }
                        }
                    } else {
                        LogUtils.d("返回数据:" + message);
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS).
                    connectTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS).
                    addInterceptor(loggingInterceptor).build();
        }
        return okHttpClient;
    }

    /*static Gson gson = new Gson();

    private static void addParams(Map<String, Object> paramsMap, Map<String, String> tempMap) {
        String biz = gson.toJson(tempMap);
        paramsMap.put("biz", biz);
    }

    private static void addParam(Map<String, Object> paramsMap, Map<String, Object> tempMap) {
        String qdata = gson.toJson(tempMap);
        paramsMap.put("qdata", qdata);
        LogUtils.d("biz请求参数：" + qdata);
    }*/

    // 请求参数拼接
    private static String encryptionStr(Map<String, String> tempMap) {
        // 按首字母排序
        Map<String, String> map = ProjectUtils.sortMapByKey(tempMap);

        String qdata = "";
        if (map == null) {

        } else {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                qdata += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        qdata = qdata + "key=toutiaoblock&token=" + SharedPrefHelper.getInstance().getToken();
        LogUtils.d("要加密的串：" + qdata);
        return qdata;
    }

    // 请求参数拼接
    private static String encryptionStr1(Map<String, String> tempMap, String token) {
        // 按首字母排序
        Map<String, String> map = ProjectUtils.sortMapByKey(tempMap);

        String qdata = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            qdata += entry.getKey() + "=" + entry.getValue() + "&";
        }
        qdata = qdata + "key=toutiaoblock&token=" + token;
        LogUtils.d("要加密的串：" + qdata);
        return qdata;
    }

    //-------------------------------------------请求内容----------------------------------------------

    /**
     * 登录接口
     *
     * @return
     */
    public static Observable login(String mobile, String code, int type, String id, String time, String device) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("mobile", mobile);
        tempMap.put("code", code);
        tempMap.put("type", String.valueOf(type));
        tempMap.put("id", id);
        tempMap.put("time", time);
        tempMap.put("device", device);

//        String qdata = encryptionStr(tempMap);

        return api.login(mobile, code, type, id, time, device/*, EncryptUtils.md5Encrypt(qdata).toUpperCase()*/)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static Observable getCode(String mobile) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("mobile", mobile);
        String qdata = encryptionStr(tempMap);
        return api.getCode(mobile, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 手机号登录
     *
     * @return
     */
    public static Observable phoneToLogin(String mobile, String code) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("mobile", mobile);
        tempMap.put("code", code);
        String qdata = encryptionStr(tempMap);

        return api.toLogin(mobile, code, EncryptUtils.md5Encrypt(qdata).toUpperCase());
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public static Observable getVersionData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("version", version);
        String qdata = encryptionStr(tempMap);

        return api.getVersionData(version, EncryptUtils.md5Encrypt(qdata).toUpperCase()).
                subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 绑定手机号
     *
     * @return
     */
    public static Observable bindPhone(String mobile, String code) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("mobile", mobile);
        tempMap.put("code", code);

        String qdata = encryptionStr(tempMap);

        return api.bindPhone(SharedPrefHelper.getInstance().getUserId(), mobile, code, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取首页新闻列表类型
     *
     * @return
     */
    public static Observable getNewsType() {
        String qdata = "key=toutiaoblock";

        return api.newsTypeData(EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程;
    }

    /**
     * 获取首页新闻列表
     *
     * @return
     */
    public static Observable getNewsListData(String cat_id, int page) {
        if ("".equals(SharedPrefHelper.getInstance().getUserId())) {
            return api.noUserNewsListData(cat_id, page)
                    .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                    .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
        } else {
            return api.newsListData(SharedPrefHelper.getInstance().getUserId(), cat_id, page)
                    .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                    .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
        }
    }

    /**
     * 我的钱包/零钱列表
     *
     * @return
     */
    public static Observable getChangeListData(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("page", page + "");
        String qdata = encryptionStr(tempMap);
        return api.changeListData(SharedPrefHelper.getInstance().getUserId(), page, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 我的钱包/金币列表
     *
     * @return
     */
    public static Observable getGoldListData(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("page", page + "");
        String qdata = encryptionStr(tempMap);
        return api.goldListData(SharedPrefHelper.getInstance().getUserId(), page, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取系统消息列表
     *
     * @return
     */
    public static Observable systemMessageData(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("page", page + "");

        String qdata = encryptionStr(tempMap);

        return api.systemMessageData(SharedPrefHelper.getInstance().getUserId(), page, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 清空系统消息
     *
     * @return
     */
    public static Observable clearMessage() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.clearMessage(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 清空系统消息
     *
     * @return
     */
    public static Observable deleteMessage(int id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id + "");

        String qdata = encryptionStr(tempMap);

        return api.deleteMessage(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 阅读单条系统消息
     *
     * @return
     */
    public static Observable readMessage(int id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id + "");

        String qdata = encryptionStr(tempMap);

        return api.readMessage(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 我的Tab
     *
     * @return
     */
    public static Observable getMeInfo() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);

        return api.getMeInfo(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 我的TabBouns
     * 領取分紅
     *
     * @return
     */
    public static Observable getMeBouns() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.getMeBouns(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 领取阅读奖励
     *
     * @return
     */
    public static Observable readReward(String id, String time,String device) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id);
        tempMap.put("time", time);
        tempMap.put("device",device);
        String qdata = encryptionStr(tempMap);

        return api.readReward(SharedPrefHelper.getInstance().getUserId(), id, time,device, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 记录用户内文点击次数
     *
     * @return
     */
    public static Observable clickNewsDetail() {
        return api.clickNewsDetail()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * tab2任务列表
     *
     * @return
     */
    public static Observable getTaskData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.getTaskData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 新版tab2任务列表
     *
     * @return
     */
    public static Observable getNewTaskData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.getNewTaskData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 常见问题
     *
     * @return
     */
    public static Observable getQuestionData() {
        return api.getQuestionData()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提现操作
     *
     * @return
     */
    public static Observable withdrawal(String change, int pay) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("change", change);
        tempMap.put("pay", String.valueOf(pay));

        String qdata = encryptionStr(tempMap);

        return api.withdrawal(SharedPrefHelper.getInstance().getUserId(), change, pay, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 查询是否可以领红包雨
     *
     * @return
     */
    public static Observable canReceiveRedPacket() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);

        return api.canReceiveRedPacket(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取推荐文章
     *
     * @return
     */
    public static Observable getRecommendData() {
        return api.getRecommendData()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 激活红包雨领取资格
     *
     * @return
     */
    public static Observable activateRedPacket(String id, String time) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id);
        tempMap.put("time", time);

        String qdata = encryptionStr(tempMap);

        return api.activateRedPacket(SharedPrefHelper.getInstance().getUserId(), id, time, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 领取红包雨
     *
     * @return
     */
    public static Observable receiveRedPacket() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.receiveRedPacket(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 账户信息列表
     *
     * @return
     */
    public static Observable accountData(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("page", String.valueOf(page));
        tempMap.put("limit", "20");

        String qdata = encryptionStr(tempMap);

        return api.accountInfoData(SharedPrefHelper.getInstance().getUserId(), page, "20", EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 上传登录状态 用于判断连续登录和当日第一次登录
     *
     * @return
     */
    public static Observable commitLoginState() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.commitLoginState(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取分享数据
     *
     * @return
     */
    public static Observable getShareData(String id, int type) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("type", String.valueOf(type));
        tempMap.put("id", id);

        String qdata = encryptionStr(tempMap);

        return api.getShareData(SharedPrefHelper.getInstance().getUserId(), id, type, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 设置页面数据
     *
     * @return
     */
    public static Observable getSettingData() {
        return api.getSettingData()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提现页面数据
     *
     * @return
     */
    public static Observable getWithdrawalMoneyData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.getWithdrawalMoneyData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提现列表数据
     *
     * @return
     */
    public static Observable getWithdrawalAuditData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);

        return api.getWithdrawalAuditData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提交用户授权信息
     *
     * @return
     */
    public static Observable commitAuthorization(String uuid, String open_id, String img, String name, String token) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("uuid", uuid);
        tempMap.put("open_id", open_id);
        tempMap.put("img", img);
        tempMap.put("name", name);

        String qdata = encryptionStr1(tempMap, token);
        return api.commitAuthorization(SharedPrefHelper.getInstance().getUserId(), uuid, open_id, img, name, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 闪屏页广告图
     *
     * @return
     */
    public static Observable getAdImageData() {
        return api.getAdImageData()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取主推新闻链接
     *
     * @return
     */
    public Observable getShareUrl() {
        return api.getShareUrl()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取收徒上方滚动条信息
     *
     * @return
     */
    public static Observable getApprenticeMessage() {
        return api.getApprenticeMessage()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 晒一晒前请求接口
     *
     * @return
     */
    public static Observable getShowIncome() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);
        return api.getShowIncome(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 收徒Tab收徒奖励接口
     *
     * @return
     */
    public static Observable getApprenticeChange() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);
        return api.getApprenticeChange(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 收徒Tab/我的徒弟接口
     *
     * @return
     */
    public static Observable getMyApprentice() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);
        return api.getMyApprentice(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 收徒Tab/我的徒孙接口
     *
     * @return
     */
    public static Observable getMyDisciple() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);
        return api.getMyDisciple(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提交登录系统型号 Android/iOS
     *
     * @return
     */
    public Observable commitAndroid() {
        return api.commitAndroid(SharedPrefHelper.getInstance().getUserId())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 提交是否是模拟器
     *
     * @return
     */
    public Observable commitSimulator(String virtual) {
        return api.commitSimulator(SharedPrefHelper.getInstance().getUserId(), virtual)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 模拟器上传手机号
     *
     * @return
     */
    public Observable commitSimulatorPhone(String phoneNumber) {
        return api.commitSimulatorPhone(phoneNumber)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 上传每天第一次登录的时间
     *
     * @return
     */
    public static Observable commitLoginTime() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());

        String qdata = encryptionStr(tempMap);
        return api.commitLoginTime(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取新闻内页推荐列表
     *
     * @return
     */
    public static Observable getRecommendNews(String cat_id, String id, int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("cat_id", cat_id);
        tempMap.put("id", id);
        tempMap.put("page", String.valueOf(page));

        String qdata = encryptionStr(tempMap);
        return api.getRecommendNews(SharedPrefHelper.getInstance().getUserId(), cat_id, id, page, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 获取用户当日是否是第一次提现
     *
     * @return
     */
    public static Observable getUserWithdrawalTime() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.getUserWithdrawalTime(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 签到
     *
     * @return
     */
    public static Observable getSignData() {
//        Map<String, String> tempMap = new HashMap<>();
//        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
//        String qdata = encryptionStr(tempMap);
        return api.getSignData(SharedPrefHelper.getInstance().getUserId()/*, EncryptUtils.md5Encrypt(qdata).toUpperCase()*/);
    }

    /**
     * 是否已经签到
     *
     * @return
     */
    public static Observable getIsSignData() {
        return api.getIsSign(SharedPrefHelper.getInstance().getUserId());
    }

    /**
     * 当天是否是第一次分享
     *
     * @return
     */
    public static Observable getIsFirstTimeShareOnDay() {
        return api.getIsFirstTimeShareOnDay(SharedPrefHelper.getInstance().getUserId());
    }

    /**
     * 统计用户点击收徒轮播次数
     *
     * @return
     */
    public static Observable statsUserClick() {
        return api.statsUserClick(SharedPrefHelper.getInstance().getUserId());
    }

    /**
     * 统计用户点击收徒分享次数
     *
     * @return
     */
    public static Observable apprenticeClickNum(int share_type, int type) {
        return api.apprenticeClickNum(SharedPrefHelper.getInstance().getUserId(), share_type, type);
    }

    /**
     * 统计用户点击内文立即分享次数
     *
     * @return
     */
    public static Observable shareClickNnm(int share_type) {
        return api.shareClickNnm(SharedPrefHelper.getInstance().getUserId(), share_type);
    }

    /**
     * 钱包泡泡列表
     *
     * @return
     */
    public static Observable walletBubbleData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.walletBubbleData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 钱包-领取红钻（点泡泡）
     *
     * @return
     */
    public static Observable receiveBubble(String id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id);
        String qdata = encryptionStr(tempMap);
        return api.receiveBubble(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 钱包-一键领取红钻
     *
     * @return
     */
    public static Observable receiveAllBubble(String id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", id);
        String qdata = encryptionStr(tempMap);
        return api.receiveAllBubble(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 出售或提现页面红钻数据
     *
     * @return
     */
    public static Observable getTBData() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.getTBData(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 出售红钻
     *
     * @return
     */
    public static Observable saleTB(String gold) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("gold", gold);
        String qdata = encryptionStr(tempMap);
        return api.saleTB(SharedPrefHelper.getInstance().getUserId(), gold, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 新闻投票数据
     *
     * @return
     */
    public static Observable getVoteData(String id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("aid", id);
        String qdata = encryptionStr(tempMap);
        return api.getVoteData(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 投票
     *
     * @return
     */
    public static Observable voteData(String id) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("aid", id);
        String qdata = encryptionStr(tempMap);
        return api.voteData(SharedPrefHelper.getInstance().getUserId(), id, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 我的投票列表
     *
     * @return
     */
    public static Observable myBallot(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
//        tempMap.put("page", String.valueOf(page));
        String qdata = encryptionStr(tempMap);
        return api.myBallot(SharedPrefHelper.getInstance().getUserId(), /*page, */EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 挖矿规则
     *
     * @return
     */
    public static Observable rulesTB() {
        /*Map<String, String> tempMap = new HashMap<>();
        String qdata = encryptionStr(tempMap);*/
        return api.rulesTB(/*EncryptUtils.md5Encrypt(qdata).toUpperCase()*/)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }


    /**
     * 极验验证
     *
     * @return
     */
    public static Observable geetestCommit(String username, String challenge) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("username", username);
        tempMap.put("challenge", challenge);
        String qdata = encryptionStr(tempMap);
        return api1.geetestCommit(username, challenge, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 极验提交数据
     *
     * @return
     */
    public static Observable commitGeeTest(String phone, String risk_code) {
        return api1.commitGeeTest(phone, risk_code)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 极验验证 新闻内文页
     *
     * @return
     */
    public static Observable geeTestCommitDetail(String article, String challenge) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("username", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("article", article);
        tempMap.put("challenge", challenge);
        String qdata = encryptionStr(tempMap);
        return api1.geeTestCommitDetail(SharedPrefHelper.getInstance().getUserId(), challenge, article, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 极验提交数据 新闻内文页
     *
     * @return
     */
    public static Observable commitGeeTestDetail(String id, String risk_code) {
        return api1.commitGeeTestDetail(SharedPrefHelper.getInstance().getUserId(), risk_code, id)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 我的社群
     *
     * @return
     */
    public static Observable getGroup() {
        return api.getGroup()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    public static Observable test() {
        return api.test()
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**-------------------------------------------视频----------------------------------------------*/
    /**
     * 我的视频列表
     *
     * @return
     */
    public static Observable getVideoList(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.getVideoList(page, SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 视频投票
     *
     * @return
     */
    public static Observable videoToVote(String aid) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("aid", aid);
        String qdata = encryptionStr(tempMap);
        return api.videoToVote(SharedPrefHelper.getInstance().getUserId(), aid, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 分享视频请求接口
     *
     * @return
     */
    public static Observable commitShareNumber(String aid) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", aid);
        String qdata = encryptionStr(tempMap);
        return api.commitShareNumber(SharedPrefHelper.getInstance().getUserId(), aid, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 视频可用票数查询
     *
     * @return
     */
    public static Observable videoVoteData(String aid) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("aid", aid);
        String qdata = encryptionStr(tempMap);
        return api.videoVoteData(SharedPrefHelper.getInstance().getUserId(), aid, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 视频记录点击次数
     *
     * @return
     */
    public static Observable videoClick(String aid) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("id", aid);
        String qdata = encryptionStr(tempMap);
        return api.videoClick(SharedPrefHelper.getInstance().getUserId(), aid, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    // 投过票的视频列表
    public static Observable myVideoItem(int page) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("page", "" + page);
        String qdata = encryptionStr(tempMap);
        return api.getVideoItem(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase(), page)
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 视频播放完成次数
     *
     * @return
     */
    public static Observable recordVideoFinish(String aid) {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        tempMap.put("aid", aid);
        String qdata = encryptionStr(tempMap);
        return api.recordVideoFinish(SharedPrefHelper.getInstance().getUserId(), aid, EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

    /**
     * 视频添加票数
     *
     * @return
     */
    public static Observable addVideoVoteNumber() {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("userid", SharedPrefHelper.getInstance().getUserId());
        String qdata = encryptionStr(tempMap);
        return api.addVideoVoteNumber(SharedPrefHelper.getInstance().getUserId(), EncryptUtils.md5Encrypt(qdata).toUpperCase())
                .subscribeOn(Schedulers.io())//订阅在io线程，那么网络访问就在io线程中进行
                .observeOn(AndroidSchedulers.mainThread());//数据展示工作在main线程
    }

}
