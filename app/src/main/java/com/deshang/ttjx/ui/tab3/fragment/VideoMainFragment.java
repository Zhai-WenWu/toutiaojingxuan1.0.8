package com.deshang.ttjx.ui.tab3.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ScreenUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.ui.login.activity.NewLoginActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.main.bean.NothingBean;
import com.deshang.ttjx.ui.mywidget.BezierEvaluator;
import com.deshang.ttjx.ui.mywidget.LazyLoadingFragment;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.WaveProgressView;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;
import com.deshang.ttjx.ui.mywidget.viewpager.MediaController;
import com.deshang.ttjx.ui.tab3.activity.VideoActivity;
import com.deshang.ttjx.ui.tab3.bean.VideoBean;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab3.bean.VoteMessage;
import com.deshang.ttjx.ui.tab4.bean.VideoClickBean;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Observable;
import rx.Subscriber;

public class VideoMainFragment extends LazyLoadingFragment {

    private static final int MAX_SECOND = SharedPrefHelper.getInstance().getVideoWatchTimeAddVote();
    private static final int START = SharedPrefHelper.getInstance().getVideoWatchTimeAddVote() / 5;

    @BindView(R.id.video)
    PLVideoTextureView video;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.diamond_num)
    TextView diamond_num;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.like_number)
    TextView like_number;
    @BindView(R.id.vote_progress)
    WaveProgressView vote_progress; // 剩余票数
    @BindView(R.id.view_bg)
    View view; // 背景
    @BindView(R.id.video_pause)
    ImageView video_pause;
    @BindView(R.id.main)
    RelativeLayout main;
    @BindView(R.id.rl_like)
    LinearLayout rl_like;

    private int id, likeNumber, shareNumber, position;
    private boolean haveVote = false;
    private String url, imageUrl, title;
    public static final String ID = "ID";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String URL = "VideoUrl";
    public static final String VIDEO_TITLE = "VIDEO_TITLE"; // 标题
    public static final String LIKE_NUMBER = "LIKE_NUMBER"; // 点赞数
    public static final String TURN_NUMBER = "TURN_NUMBER"; // 转发数
    public static final String DIAMOND_NUMBER = "DIAMOND_NUMBER"; // 作品已赚
    public static final String IS_VOTE = "IS_VOTE"; // 作品已赚
    private boolean isPlay = true;

    private int showCount = 0;

    private boolean canAddTime = false;
    private Timer timer;
    private TimerTask task;

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
                    LogUtils.d("视频计时：" + Constants.videoReadSecond);
                    if (Constants.videoReadSecond < MAX_SECOND) {
                        Constants.videoReadSecond++;
                        vote_progress.setProgress(Constants.videoReadSecond + START);
                    } else {
                        // TODO 请求加票接口
                        stopTimer();
                        addVideoVoteNumber();
                    }
                }
            };
            timer.schedule(task, 0, 1000);
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

    @Override
    protected void loadData() {
        LogUtils.d("isVisibleToUser：loadData()" + "  标题:" + title);
        LogUtils.d("当前的位置：" + position + "  标题:" + title);
        video.start();
        if (Constants.videoVoteNumber > 0) {
            vote_progress.setProgress(MAX_SECOND + START);
        } else {
            vote_progress.setProgress(Constants.videoReadSecond + START);
        }
        // 记录用户观看次数
        if (!"".equals(SharedPrefHelper.getInstance().getToken())) {
            videoClick();
        }
    }

    @Override
    protected void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_video);
    }


    @OnClick({R.id.rl_like, R.id.share, R.id.view_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_like:
                if ("".equals(SharedPrefHelper.getInstance().getToken())) {
                    startActivity(new Intent(getActivity(), NewLoginActivity.class));
                } else {
                    // 点赞
                    if (!haveVote) {
                        if (Constants.videoVoteNumber > 0) {
                            videoToVote();
                        }
                    } else {
                        showToast("已经点赞了！");
                    }
                }
                break;

            case R.id.share:
                // 记录次数
                ShareDialog dialog = new ShareDialog(getActivity(), R.style.dialog_style);
                dialog.setVisible(2);
                dialog.setClickListener(new ShareDialog.ClickListener() {
                    @Override
                    public void onClickListener(int type) {
                        if (type == 1) {
                            // 微信朋友圈
//                            UserPathUtils.commitUserPath(39);
                            ShareUtils.showShare(getActivity(), title, "", SharedPrefHelper.getInstance().getVideoShareUrl() + Constants.VIDEO_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                                    null, WechatMoments.NAME, 2);
                        } else if (type == 2) {
                            // 微信好友
                            ShareUtils.showShare(getActivity(), title, "", SharedPrefHelper.getInstance().getVideoShareUrl() + Constants.VIDEO_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                                    null, Wechat.NAME, 2);
                        } else if (type == 6) {
                            // QQ
                            ShareUtils.shareSDK(getActivity(), title, SharedPrefHelper.getInstance().getShareUrl() + Constants.VIDEO_SHARE + SharedPrefHelper.getInstance().getUserId() + "/id/" + id,
                                    null, QQ.NAME, 2);
                        }
                    }
                });
                dialog.show();
                commitShareNumber();
                break;

            case R.id.view_bg:
                if (isPlay) {
                    video.pause();
                    video_pause.setVisibility(View.VISIBLE);
                    showPause();
                    stopTimer();
                } else {
                    video.start();
                    video_pause.setVisibility(View.GONE);
                    if (canAddTime && Constants.videoVoteNumber == 0) {
                        startTimer();
                    }
                }
                isPlay = !isPlay;
                break;
        }
    }

    private void showPause() {
        ScaleAnimation animation = new ScaleAnimation(1.25f, 1.0f, 1.25f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        video_pause.setAnimation(animation);
        animation.start();
    }


    @Override
    protected void initView(View v) {
        if (!SharedPrefHelper.getInstance().getOnLine()) {
            diamond_num.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
        view.getParent().requestDisallowInterceptTouchEvent(false);

        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        id = bundle.getInt(ID, 0);
        title = bundle.getString(VIDEO_TITLE);
        content.setText(title);
        likeNumber = bundle.getInt(LIKE_NUMBER, 0);
        like_number.setText(String.valueOf(likeNumber));
        shareNumber = bundle.getInt(TURN_NUMBER, 0);
        share.setText(String.valueOf(shareNumber));
        diamond_num.setText("作品已赚   " + String.valueOf(bundle.getDouble(DIAMOND_NUMBER, 0)));
        haveVote = bundle.getBoolean(IS_VOTE, false);
        if (haveVote) {
            like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like_select));
        } else {
            like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like));
        }
        url = bundle.getString(URL);
        imageUrl = bundle.getString(IMAGE_URL);
//        video.setLooping(true);
        AVOptions options = new AVOptions();
//        options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
        video.setAVOptions(options);
        video.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        video.setOnInfoListener(infoListener);
        video.setOnErrorListener(errorListener);
        video.setOnCompletionListener(completionListener);
        video.setVideoPath(url);
    }

    private PLOnCompletionListener completionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            showCount++;
            if (showCount > 1) {
                // 不加时间
                stopTimer();
                canAddTime = false;
            }
            LogUtils.d("播放完成！");
            recordVideoFinish();
            video.start();
        }
    };

    private PLOnInfoListener infoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            if (what == 3) {
                image.setVisibility(View.GONE);
            }
        }
    };

    private PLOnErrorListener errorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            /*if (errorCode == -3) {
                ReceiveGoldToast.makeToast(getActivity(), "网络异常！").show();
            } else */
            if (errorCode == -2 && getIsUIVisible()) {
                ReceiveGoldToast.makeToast(getActivity(), "视频加载失败！").show();
            }
            LogUtils.d("errorCode:" + errorCode);
            return false;
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (video == null) {
            return;
        }
        LogUtils.d("isVisibleToUser：" + isVisibleToUser + "  标题:" + title);
        if (isVisibleToUser) {
            video.setVideoPath(url);
            video.start();
        } else {
            stopTimer();
            video_pause.setVisibility(View.GONE);
            video.stopPlayback();
            SharedPrefHelper.getInstance().setVideoWatchTime(Constants.videoReadSecond);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("isVisibleToUser：onResume()" + "  标题:" + title);
        vote_progress.setText(String.valueOf(Constants.videoVoteNumber));
        GlideLoading.getInstance().loadImgUrlNyImgLoader(getActivity(), imageUrl, image);
        LogUtils.d("标题--" + title + "  " + video.isShown() + "  正在播放：" + video.isPlaying());
        if (video != null && isPause && isPlay) {
            isPause = false;
            video.start();
            if (canAddTime && Constants.videoVoteNumber == 0) {
                startTimer();
            }
        }
    }

    private boolean isPause = false;

    @Override
    public void onPause() {
        super.onPause();
        if (video.isPlaying()) {
            isPause = true;
            stopTimer();
        }
        LogUtils.d("isVisibleToUser：onPause()" + "  标题:" + title);
        if (video != null) {
            video.pause();
        }
    }

    //ui主线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(VoteMessage message) {
        LogUtils.d("收到通知。");
        vote_progress.setText(String.valueOf(Constants.videoVoteNumber));
        if (message.getVoteNumber() > 0) {
            vote_progress.setProgress(MAX_SECOND + START);
        } else {
            vote_progress.setProgress(Constants.videoReadSecond + START);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除注册
        stopTimer();
        SharedPrefHelper.getInstance().setVideoWatchTime(Constants.videoReadSecond);
        LogUtils.d("isVisibleToUser：onDestroy()" + "  标题:" + title);
        if (video != null) {
            // true代表清除最后一帧画面
            video.stopPlayback();
        }
    }

    /**
     * 查询可用余票
     */
    public void videoVoteData(final int count) {
        Observable request = RetrofitUtils.getInstance().videoVoteData(String.valueOf(id));
        Subscriber<VideoVoteBean> upDataSubscriber = new Subscriber<VideoVoteBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final VideoVoteBean bean) {
                if (bean.getErrcode() == 0) {
                    if (bean.getReturn() == null)
                        return;
                    ((VideoActivity) getActivity()).setWalletUI(bean.getReturn().getEinnahmen_count());
                    Constants.videoVoteNumber = bean.getReturn().getTotal_votes();

                    vote_progress.setMax(MAX_SECOND + START); // 水波纹最大值
                    vote_progress.setText(String.valueOf(bean.getReturn().getTotal_votes())); // 剩余票数
                    if (bean.getReturn().getTotal_votes() > 0) {
                        // 已经有票了
                        stopTimer();
                        vote_progress.setProgress(MAX_SECOND + START);// 水波纹设置填充满
                        SharedPrefHelper.getInstance().setVideoWatchTime(0);
                    } else {
                        Constants.videoReadSecond = SharedPrefHelper.getInstance().getVideoWatchTime();
                        vote_progress.setProgress(SharedPrefHelper.getInstance().getVideoWatchTime() + START); // 水波纹当前值
                        if (count <= SharedPrefHelper.getInstance().getVideoEffectiveNumber() && count != -1) {
                            // 收益次数小于2次
                            startTimer();
                        }
                    }
                    diamond_num.setText("作品已赚   " + bean.getReturn().getArticle_vote_sum());
                    VoteMessage message = new VoteMessage();
                    message.setVoteNumber(bean.getReturn().getTotal_votes());
                    EventBus.getDefault().post(message);
                    haveVote = bean.getReturn().getVote_type() == 1;
                    if (bean.getReturn().getVote_type() == 1) {
                        like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like_select));
                    } else {
                        like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like));
                    }
                    likeNumber = bean.getReturn().getArticle_vote_count();
                    like_number.setText(String.valueOf(bean.getReturn().getArticle_vote_count())); // 点赞数
                    share.setText(String.valueOf(bean.getReturn().getShare_total())); // 分享数
                    for (int i = 0; i < Constants.videoData.size(); i++) {
                        if (Constants.videoData.get(i) instanceof VideoBean.ReturnBean) {
                            VideoBean.ReturnBean bean1 = (VideoBean.ReturnBean) Constants.videoData.get(i);
                            if (id == bean1.getId()) {
                                bean1.setShare_total(bean.getReturn().getShare_total());
                                bean1.setVote_type(bean.getReturn().getVote_type());
                                bean1.setArticle_vote_count(bean.getReturn().getArticle_vote_count());
                                break;
                            }
                        }
                    }
                } else {
                    showToast(bean.getErrinf());
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 分享视频请求接口  统计数量
     */
    public void commitShareNumber() {
        Observable request = RetrofitUtils.getInstance().commitShareNumber(String.valueOf(id));
        Subscriber<NothingBean> upDataSubscriber = new Subscriber<NothingBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("分享视频请求接口异常" + e.toString());
            }

            @Override
            public void onNext(NothingBean bean) {
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 视频记录点击次数  统计数量
     */
    public void videoClick() {
        Observable request = RetrofitUtils.getInstance().videoClick(String.valueOf(id));
        Subscriber<VideoClickBean> upDataSubscriber = new Subscriber<VideoClickBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("视频记录点击次数异常" + e.toString());
            }

            @Override
            public void onNext(VideoClickBean bean) {
                if (bean.getVolume_count() <= SharedPrefHelper.getInstance().getVideoEffectiveNumber()) {
                    canAddTime = true;
                } else {
                    canAddTime = false;
                }
                showCount = 0;
                videoVoteData(bean.getVolume_count());
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 视频投票
     */
    public void videoToVote() {
        Observable request = RetrofitUtils.getInstance().videoToVote(String.valueOf(id));
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("视频投票异常" + e.toString());
            }

            @Override
            public void onNext(BaseBean bean) {
                if (bean.errcode == 0) {
                    like_number.setText(String.valueOf(likeNumber + 1));
                    if (SharedPrefHelper.getInstance().getOnLine()) {
                        diamondAnim();
                    }
                    if (canAddTime) {
                        // 次数可以增加
                        videoVoteData(1);
                    } else {
                        videoVoteData(-1);
                    }
                } else {
                    showToast(bean.errinf);
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    // 红钻动画
    private void diamondAnim() {
        int[] location = new int[2];
        rl_like.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.wallet_diamond));
        imageView.setX(x + rl_like.getWidth() / 2 - ((VideoActivity) getActivity()).getBitmapWidth() / 2);
        imageView.setY(y - ScreenUtils.getStatusBarHeight(getActivity()));
//                LogUtils.d("pointF.123123123:" + (x + see.getWidth() / 2 - bitmapWidth / 2) + " pointF.123123123:" + (y - ScreenUtils.getStatusBarHeight(this)));
        main.addView(imageView);
        getBezierValueAnimator(new PointF(x, y - ScreenUtils.getStatusBarHeight(getActivity())),
                new PointF(((VideoActivity) getActivity()).getLocation1()[0], ((VideoActivity) getActivity()).getLocation1()[1]), imageView).start();
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

    /**
     * 视频播放完成次数
     */
    public void recordVideoFinish() {
        Observable request = RetrofitUtils.getInstance().recordVideoFinish(String.valueOf(id));
        Subscriber<NothingBean> upDataSubscriber = new Subscriber<NothingBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("视频播放完成次数异常" + e.toString());
            }

            @Override
            public void onNext(NothingBean bean) {
            }
        };
        request.subscribe(upDataSubscriber);
    }

    /**
     * 视频添加票数
     */
    public void addVideoVoteNumber() {
        Observable request = RetrofitUtils.getInstance().addVideoVoteNumber();
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("视频添加票数异常" + e.toString());
            }

            @Override
            public void onNext(BaseBean bean) {
                if (bean.errcode == 0) {
//                    videoClick();
                    videoVoteData(-1);
                    Constants.videoReadSecond = 0;
                    SharedPrefHelper.getInstance().setVideoWatchTime(0);
                } else {
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }
}
