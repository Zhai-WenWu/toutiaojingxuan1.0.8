package com.deshang.ttjx.framework.network.retrofit;

import com.deshang.ttjx.framework.base.BaseResponse;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.bean.LoginBean;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.bean.NothingBean;
import com.deshang.ttjx.ui.main.bean.UpdateBean;
import com.deshang.ttjx.ui.tab1.bean.ADImageBean;
import com.deshang.ttjx.ui.tab1.bean.GeeTestBean;
import com.deshang.ttjx.ui.tab1.bean.IsFirstTimeShareOnDayBean;
import com.deshang.ttjx.ui.tab1.bean.IsSignBean;
import com.deshang.ttjx.ui.tab1.bean.MessageBean;
import com.deshang.ttjx.ui.tab1.bean.NewsListBean;
import com.deshang.ttjx.ui.tab1.bean.NewsTypeBean;
import com.deshang.ttjx.ui.tab1.bean.ReadRewardBean;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;
import com.deshang.ttjx.ui.tab1.bean.RecommendNewsBean;
import com.deshang.ttjx.ui.tab1.bean.ShareMessageBean;
import com.deshang.ttjx.ui.tab1.bean.ShareUrlBean;
import com.deshang.ttjx.ui.tab1.bean.SignBean;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab1.bean.VoteBean;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.bean.ChangeBean;
import com.deshang.ttjx.ui.tab2.bean.GoldBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyBean;
import com.deshang.ttjx.ui.tab2.bean.MakeMoneyNewBean;
import com.deshang.ttjx.ui.tab2.bean.RulesBean;
import com.deshang.ttjx.ui.tab3.bean.MyApprenticeBean;
import com.deshang.ttjx.ui.tab3.bean.MyDiscipleBean;
import com.deshang.ttjx.ui.tab3.bean.Tab3Bean;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab4.bean.AccountInfoBean;
import com.deshang.ttjx.ui.tab4.bean.ApprenticeScrollMessageBean;
import com.deshang.ttjx.ui.tab4.bean.BallotBean;
import com.deshang.ttjx.ui.tab4.bean.GroupBean;
import com.deshang.ttjx.ui.tab4.bean.MeBean;
import com.deshang.ttjx.ui.tab4.bean.MyVedioBean;
import com.deshang.ttjx.ui.tab4.bean.QuestionListBean;
import com.deshang.ttjx.ui.tab4.bean.SaleOrWithdrawalData;
import com.deshang.ttjx.ui.tab4.bean.SettingBean;
import com.deshang.ttjx.ui.tab4.bean.UserInfoBean;
import com.deshang.ttjx.ui.tab4.bean.VideoClickBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalAuditBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalMoneyBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalTimeBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by L on 2018年6月08日
 */

public interface NetAPI {

    // 示例代码 无用
    @FormUrlEncoded
    @POST(NetUrl.login)
    Observable<NewLoginBean> login(@FieldMap Map<String, Object> map);

    // 手机号登录
    @FormUrlEncoded
    @POST("/api/login/apilogin")
    Observable<NewLoginBean> login(@Field("mobile") String mobile, @Field("code") String code, @Field("type") int type,
                                   @Field("id") String id, @Field("time") String time, @Field("device") String device/*, @Field("sign") String sign*/);

    // 获取验证码
    @FormUrlEncoded
    @POST("/api/login/code")
    Observable<CodeBean> getCode(@Field("mobile") String mobile, @Field("sign") String sign);

    // 手机号登陆  无用
    @FormUrlEncoded
    @POST("/api/login/apiLogin")
    Observable<LoginBean> toLogin(@Field("mobile") String mobile, @Field("code") String code, @Field("sign") String sign);

    // 获取版本信息
    @FormUrlEncoded
    @POST("/api/edition/edition")
    Observable<UpdateBean> getVersionData(@Field("version") String version, @Field("sign") String sign);

    // 绑定手机  无用
    @FormUrlEncoded
    @POST("/api/login/inputmobile")
    Observable<BaseBean> bindPhone(@Field("userid") String userid, @Field("mobile") String mobile, @Field("code") String code, @Field("sign") String sign);

    // 首页新闻分类列表
    @FormUrlEncoded
    @POST("/api/index/cat_list")
    Observable<NewsTypeBean> newsTypeData(@Field("sign") String sign);

    // 首页新闻列表
//    @FormUrlEncoded
//    @POST("/api/index/cat_article")
//    Observable<NewsListBean> newsListData(@Field("userid") String userid, @Field("cat_id") String cat_id, @Field("page") int page, @Field("sign") String sign);

    // 首页新闻列表
    @GET("/api/index/cat_article/userid/{userid}/cat_id/{cat_id}/page/{page}/sign/{sign}")
    Observable<NewsListBean> newsListData(@Path("userid") String userid, @Path("cat_id") String cat_id, @Path("page") int page);

    // 没有userID的首页新闻列表
    @GET("/api/index/cat_article/cat_id/{cat_id}/page/{page}/sign/{sign}")
    Observable<NewsListBean> noUserNewsListData(@Path("cat_id") String cat_id, @Path("page") int page);

    // 我的钱包/零钱列表
    @FormUrlEncoded
    @POST("/api/home/mywalletchange")
    Observable<ChangeBean> changeListData(@Field("userid") String userid, @Field("page") int page, @Field("sign") String sign);

    // 我的钱包/金币列表
    @FormUrlEncoded
    @POST("/api/home/mywalletgold")
    Observable<GoldBean> goldListData(@Field("userid") String userid, @Field("page") int page, @Field("sign") String sign);

    // 系统消息列表
    @FormUrlEncoded
    @POST("/api/message/index")
    Observable<MessageBean> systemMessageData(@Field("userid") String userid, @Field("page") int page, @Field("sign") String sign);

    // 清空系统消息列表
    @FormUrlEncoded
    @POST("/api/message/messagedel")
    Observable<BaseBean> clearMessage(@Field("userid") String userid, @Field("sign") String sign);

    // 删除单条系统消息列表
    @FormUrlEncoded
    @POST("/api/message/messagedelsingle")
    Observable<BaseBean> deleteMessage(@Field("userid") String userid, @Field("id") int id, @Field("sign") String sign);

    // 阅读单条消息
    @FormUrlEncoded
    @POST("/api/Message/messageDetail")
    Observable<BaseBean> readMessage(@Field("userid") String userid, @Field("id") int id, @Field("sign") String sign);

    // Tab4数据
    @FormUrlEncoded
    @POST("/api/home/index")
    Observable<MeBean> getMeInfo(@Field("userid") String userid, @Field("sign") String sign);

    // Tab4Bouns数据
    @FormUrlEncoded
    @POST("/api/bonus/index")
    Observable<BaseBean> getMeBouns(@Field("userid") String userid, @Field("sign") String sign);

    // 领取阅读奖励
    @FormUrlEncoded
    @POST("/api/article/readreward")
    Observable<ReadRewardBean> readReward(@Field("userid") String userid, @Field("id") String id, @Field("time") String time, @Field("device") String device, @Field("sign") String sign);

    // 记录用户内文点击次数
    @GET("/api/Article/articleClick")
    Observable<BaseBean> clickNewsDetail();

    // tab2任务列表
    @FormUrlEncoded
    @POST("/api/task/lists")
    Observable<MakeMoneyBean> getTaskData(@Field("userid") String userid, @Field("sign") String sign);

    @FormUrlEncoded
    @POST("/api/task/lists")
    Observable<MakeMoneyNewBean> getNewTaskData(@Field("userid") String userid, @Field("sign") String sign);

    // 常见问题
    @POST("/api/home/question")
    Observable<QuestionListBean> getQuestionData();

    // 账户信息
    @FormUrlEncoded
    @POST("/api/home/accountinformation")
    Observable<UserInfoBean> accountMessage(@Field("sign") String sign);

    // 提现操作
    @FormUrlEncoded
    @POST("/api/withdrawals/withdrawals")
    Observable<BaseBean> withdrawal(@Field("userid") String userid, @Field("change") String change, @Field("pay") int pay, @Field("sign") String sign);

    // 查询是否可以领红包雨
    @FormUrlEncoded
    @POST("/api/redrain/index")
    Observable<BaseBean> canReceiveRedPacket(@Field("userid") String userid, @Field("sign") String sign);

    // 获取推荐文章
    @POST("/api/index/redrainactivation")
    Observable<RecommendBean> getRecommendData();

    // 激活红包雨领取资格
    @FormUrlEncoded
    @POST("/api/redrain/activationRed")
    Observable<ReadRewardBean> activateRedPacket(@Field("userid") String userid, @Field("id") String id, @Field("time") String time, @Field("sign") String sign);

    // 领取红包雨
    @FormUrlEncoded
    @POST("/api/redrain/receiveredrain")
    Observable<ReceiveRedPacketBean> receiveRedPacket(@Field("userid") String userid, @Field("sign") String sign);

    // 账户信息列表
    @FormUrlEncoded
    @POST("/api/home/accountinformation")
    Observable<AccountInfoBean> accountInfoData(@Field("userid") String userid, @Field("page") int page, @Field("limit") String limit, @Field("sign") String sign);

    // 上传用户登录状态
    @FormUrlEncoded
    @POST("/api/login/loginrecord")
    Observable<BaseBean> commitLoginState(@Field("userid") String userid, @Field("sign") String sign);

    // 获取分享数据
    @FormUrlEncoded
    @POST("/api/share/generateshare")
    Observable<ShareMessageBean> getShareData(@Field("userid") String userid, @Field("id") String id, @Field("type") int type, @Field("sign") String sign);

    // 获取设置页面数据
    @GET("/api/home/setting")
    Observable<SettingBean> getSettingData();

    // 提现页面数据
    @FormUrlEncoded
    @POST("/api/withdrawals/putchange")
    Observable<WithdrawalMoneyBean> getWithdrawalMoneyData(@Field("userid") String userid, @Field("sign") String sign);

    // 获取提现列表
    @FormUrlEncoded
    @POST("/api/home/withdrawal")
    Observable<WithdrawalAuditBean> getWithdrawalAuditData(@Field("userid") String userid, @Field("sign") String sign);

    // 提交用户授权信息
    @FormUrlEncoded
    @POST("/api/withdrawals/authorize")
    Observable<BaseBean> commitAuthorization(@Field("userid") String userid, @Field("uuid") String uuid, @Field("open_id") String open_id,
                                             @Field("img") String img, @Field("name") String name, @Field("sign") String sign);

    // 获取闪屏页广告图
    @GET("/api/home/appstart")
    Observable<ADImageBean> getAdImageData();

    // 获取收徒上方滚动条信息
    @GET("/api/apprentice/apprenticename")
    Observable<ApprenticeScrollMessageBean> getApprenticeMessage();

    // 晒一晒前请求接口
    @FormUrlEncoded
    @POST("/api/bask/generatebask")
    Observable<BaseBean> getShowIncome(@Field("userid") String userid, @Field("sign") String sign);

    // 收徒Tab收徒奖励接口
    @FormUrlEncoded
    @POST("/api/home/apprenticemoney")
    Observable<Tab3Bean> getApprenticeChange(@Field("userid") String userid, @Field("sign") String sign);

    // 收徒Tab/我的徒弟接口
    @FormUrlEncoded
    @POST("/api/home/myApprentice")
    Observable<MyApprenticeBean> getMyApprentice(@Field("userid") String userid, @Field("sign") String sign);

    // 收徒Tab/我的徒孙接口
    @FormUrlEncoded
    @POST("/api/home/myDisciple")
    Observable<MyDiscipleBean> getMyDisciple(@Field("userid") String userid, @Field("sign") String sign);

    // 获取主推新闻链接
    @GET("/api/home/app_red_share")
    Observable<ShareUrlBean> getShareUrl();

    // 提交登录系统型号 Android/iOS
    @GET("/api/edition/appsystem/userid/{userid}/appsys/1")
    Observable<BaseBean> commitAndroid(@Path("userid") String userid);

    // 提交是否是模拟器
    @GET("/api/edition/virtual/userid/{userid}/virtual/{virtual}")
    Observable<BaseBean> commitSimulator(@Path("userid") String userid, @Path("virtual") String virtual);

    // 模拟器获取验证码请求接口 上传手机号
    @GET("/api/login/virtuals/mobile/{mobile}")
    Observable<BaseBean> commitSimulatorPhone(@Path("mobile") String mobile);

    // 上传每天第一次的登录时间
    @FormUrlEncoded
    @POST("/api/home/user_update_time")
    Observable<BaseBean> commitLoginTime(@Field("userid") String userid, @Field("sign") String sign);

    // 新闻详情页下方推荐列表
    @FormUrlEncoded
    @POST("/api/index/ajax_detail")
    Observable<RecommendNewsBean> getRecommendNews(@Field("userid") String userid, @Field("cat_id") String cat_id, @Field("id") String id, @Field("page") int page, @Field("sign") String sign);

    // 记录用户轨迹  无用
//    @FormUrlEncoded
    @GET("/api/push/add_push/userid/{userid}/type/{type}/app/1")
    Observable<BaseBean> commitUserPath(@Path("userid") String userid, @Path("type") int type);

    // 获取用户当日是否是第一次提现
    @FormUrlEncoded
    @POST("/api/Withdrawals/onceday")
    Observable<WithdrawalTimeBean> getUserWithdrawalTime(@Field("userid") String userid, @Field("sign") String sign);

    // 签到
//    @FormUrlEncoded
    @GET("/api/sign/index/userid/{userid}")
    Observable<SignBean> getSignData(@Path("userid") String userid/*, @Path("sign") String sign*/);

    // 是否已经签到
//    @FormUrlEncoded
    @GET("/api/sign/whetherSign/userid/{userid}")
    Observable<IsSignBean> getIsSign(@Path("userid") String userid/*, @Path("sign") String sign*/);

    // 是否已经分享过
    @GET("/api/share/whetherShare/userid/{userid}")
    Observable<IsFirstTimeShareOnDayBean> getIsFirstTimeShareOnDay(@Path("userid") String userid);

    // 统计用户点击次数
    @GET("/api/apprentice/userfunnelapprenticeopen/userid/{userid}")
    Observable<BaseResponse> statsUserClick(@Path("userid") String userid);

    // 统计用户点击收徒分享次数
    @GET("/api/apprentice/userFunnelStationShare/userid/{userid}/share_type/{share_type}/type/{type}")
    Observable<BaseResponse> apprenticeClickNum(@Path("userid") String userid, @Path("share_type") int share_type, @Path("type") int type);

    // 统计用户点击内文页立即分享次数
    @GET("/api/apprentice/userFunnelShare/userid/{userid}/share_type/{share_type}")
    Observable<BaseResponse> shareClickNnm(@Path("userid") String userid, @Path("share_type") int share_type);

    /*-----------------------------------钱包相关 start-------------------------------------- */

    // 钱包泡泡列表
    @FormUrlEncoded
    @POST("/api/wallet/index")
    Observable<BubbleBean> walletBubbleData(@Field("userid") String userid, @Field("sign") String sign);

    // 钱包 - 领取红钻（点泡泡）
    @FormUrlEncoded
    @POST("/api/wallet/receive")
    Observable<BaseBean> receiveBubble(@Field("userid") String userid, @Field("id") String id, @Field("sign") String sign);

    // 钱包 - 一键领取红钻
    @FormUrlEncoded
    @POST("/api/wallet/onekeyReceiveGold")
    Observable<BaseBean> receiveAllBubble(@Field("userid") String userid, @Field("id") String id, @Field("sign") String sign);

    // 出售红钻
    @FormUrlEncoded
    @POST("/api/exchange/index")
    Observable<BaseBean> saleTB(@Field("userid") String userid, @Field("gold") String gold, @Field("sign") String sign);

    // 获取红钻和余额
    @FormUrlEncoded
    @POST("/api/exchange/sell_red")
    Observable<SaleOrWithdrawalData> getTBData(@Field("userid") String userid, @Field("sign") String sign);

    /*-----------------------------------钱包相关 end-------------------------------------- */

    // 新闻投票数据
    @FormUrlEncoded
    @POST("/api/vote/whethervote")
    Observable<VoteBean> getVoteData(@Field("userid") String userid, @Field("aid") String aid, @Field("sign") String sign);

    // 投票
    @FormUrlEncoded
    @POST("/api/vote/index")
    Observable<BaseBean> voteData(@Field("userid") String userid, @Field("aid") String aid, @Field("sign") String sign);

    // 我的投票
    @FormUrlEncoded
    @POST("/api/vote/myVote")
    Observable<BallotBean> myBallot(@Field("userid") String userid, /*@Field("page") int page,*/ @Field("sign") String sign);

    // 挖矿规则
//    @FormUrlEncoded
    @POST("/api/wallet/ruleIntroduce")
    Observable<RulesBean> rulesTB(/*@Field("sign") String sign*/);

    // 极验验证
    @FormUrlEncoded
    @POST("/api/jiyan/index")
    Observable<GeeTestBean> geetestCommit(@Field("username") String username, @Field("challenge") String challenge, @Field("sign") String sign);

    // 极验提交
    @GET("/api/jiyan/cheat/phone/{phone}/risk_code/{risk_code}")
    Observable<BaseBean> commitGeeTest(@Path("phone") String phone, @Path("risk_code") String risk_code);

    // 极验验证 （新闻内文）
    @FormUrlEncoded
    @POST("/api/jiyan/index")
    Observable<GeeTestBean> geeTestCommitDetail(@Field("username") String userid, @Field("challenge") String challenge, @Field("article") String article, @Field("sign") String sign);

    // 极验提交 （新闻内文）
    @GET("/api/jiyan/cheat/userid/{userid}/risk_code/{risk_code}/id/{id}")
    Observable<BaseBean> commitGeeTestDetail(@Path("userid") String userid, @Path("risk_code") String risk_code, @Path("id") String id);

    //我的社群
    @GET("/api/home/mygroup")
    Observable<GroupBean> getGroup();

    @GET("/api/Article/readtest")
    Observable<BaseBean> test();

    /**
     * -------------------------------------------视频----------------------------------------------
     */

    // 视频列表
    @FormUrlEncoded
    @POST("/api/videos/index")
    Observable<VideoBean> getVideoList(@Field("page") int page, @Field("userid") String userid, @Field("sign") String sign);

    // 视频投票
    @FormUrlEncoded
    @POST("/api/vote/videovotes")
    Observable<BaseBean> videoToVote(@Field("userid") String userid, @Field("aid") String aid, @Field("sign") String sign);

    // 分享视频请求接口
    @FormUrlEncoded
    @POST("/api/video/sharevideonunber")
    Observable<NothingBean> commitShareNumber(@Field("userid") String userid, @Field("id") String id, @Field("sign") String sign);

    // 视频可用票数查询
    @FormUrlEncoded
    @POST("/api/vote/videowhethervote")
    Observable<VideoVoteBean> videoVoteData(@Field("userid") String userid, @Field("aid") String aid, @Field("sign") String sign);

    // 视频记录点击次数
    @FormUrlEncoded
    @POST("/api/Video/videoClick")
    Observable<VideoClickBean> videoClick(@Field("userid") String userid, @Field("id") String id, @Field("sign") String sign);

    //我的视频投票
    @FormUrlEncoded
    @POST("/api/vote/myVideoVote")
    Observable<MyVedioBean> getVideoItem(@Field("userid") String userid, @Field("sign") String sign, @Field("page") int page);

    // 视频播放完成次数
    @FormUrlEncoded
    @POST("/api/Video/readreward")
    Observable<NothingBean> recordVideoFinish(@Field("userid") String userid, @Field("aid") String aid, @Field("sign") String sign);

    // 视频添加票数
    @FormUrlEncoded
    @POST("/api/Video/readGainVotes")
    Observable<BaseBean> addVideoVoteNumber(@Field("userid") String userid, @Field("sign") String sign);
}
