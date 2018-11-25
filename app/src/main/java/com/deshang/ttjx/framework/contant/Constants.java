package com.deshang.ttjx.framework.contant;

import com.deshang.ttjx.framework.network.ServerConstants;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String GDT_APP_ID = "1107059936"; // 广点通AppID
    public static final String GDT_LEFT_IMAGE_ID = "4060334688475413"; // 广点通左图右文广告位ID
    public static final String GDT_BIG_IMAGE = "5020330798840480"; // 广点通大图广告位ID
    public static final String GDT_RIGHT_IMAGE_ID = "3090538864372870"; // 广点通右图左文广告位ID
    public static final String GDT_KAI_PING_ID = "8040530814878815"; // 广点通开屏广告位ID

    public static final String BAIDU_BANNER = "5873485"; // 百度Banner代码位ID
    public static final String BAIDU_KAI_PING = "5873089"; // 百度开屏代码位ID

    public static final String TOUTIAO_APP_ID = "5002470"; // 穿山甲应用ID
    public static final String TOUTIAO_AD_ID = "902470984"; // 穿山甲代码位ID

    public static final String APP_CONFIG_FILE_NAME = "AppConfig.json";

    // H5收徒Url
    public static final String APPRENTICE = ServerConstants.OUR + "/api/apprentice/detail";

    // 新闻网页地址
    public static final String NEWS_DETAIL = "/api/index/detail";

    // 文章分享Url
    public static final String NEWS_SHARE = "/api/tshare/info/userid/";

    // 视频分享Url
    public static final String VIDEO_SHARE = "/api/videos/detail/userid/";

    // 收徒分享 出去的Url
    public static final String SHARE_APPRENTICE = "/api/tshare/info/userid/";

    // 用户协议Url
    public static final String USER_AGREEMENT = "http://ozv2e5lmn.bkt.clouddn.com/privacyagreement.html";

    // 收徒分享标题
    public static final String SHARE_APPRENTICE_TITLE = "阅读分享就有钱赚，轻轻松松首日可提现，外快赚到手软！";

    public static int TabPosition = 0;

    public static int IS_LOGIN = 0; // 是否登录  0登录 1未登录
    public static float ROUND_ANGLE = 0f; // 新闻详情页的圆环角度

    public static int TIME_FIVE = 600; // 600秒 10分钟 红包雨计时

    public static boolean IS_BACKGROUND = false; // 是否处于后台状态

    public static boolean IS_CHOOSE_FIRST = false; // 用于退出登录时记录状态，返回主页面默认选中tab1

    public static boolean CAN_RECEIVE = false; // 当天第一次登陆是否可以领取红包

    public static int width = 0;

    public static int height = 0;

    public static String CHANNEL_TYPE = "TTJingXuan"; // YingYongBao  XiaoMi  HUAWEI TTJingXuan

    public static int turn_to_other_tab = 0; // 不等于0的时候跳转到其他tab页

    public static List<String> tab2ScrollText = new ArrayList<>();

    public static int redNewsCount = 0; // 记录阅读过的文章数

    /**-------------视频相关----------------*/
    public static List<Object> videoData = new ArrayList<>();// 视频数据源
    public static int videoPage = 1;// 视频页数
    public static int videoVoteNumber = 0; // 视频可用票数

    public static List<MyVedioBean.ReturnBean> videoVoteData = new ArrayList<>();// 投票视频数据源
    public static int videoVotePage = 1;// 投票视频页数

    public static int videoReadSecond = 0;// 视频观看秒数
    /**-------------视频相关----------------*/


}
