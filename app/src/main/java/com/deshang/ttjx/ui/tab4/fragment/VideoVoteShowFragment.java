package com.deshang.ttjx.ui.tab4.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.ui.main.bean.NothingBean;
import com.deshang.ttjx.ui.mywidget.LazyLoadingFragment;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.WaveProgressView;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;
import com.deshang.ttjx.ui.tab3.bean.VideoVoteBean;
import com.deshang.ttjx.ui.tab4.activity.VideoVoteActivity;
import com.deshang.ttjx.ui.tab4.bean.VideoClickBean;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Observable;
import rx.Subscriber;

public class VideoVoteShowFragment extends LazyLoadingFragment {

    private static final int MAX_SECOND = SharedPrefHelper.getInstance().getVideoWatchTimeAddVote();

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
    @BindView(R.id.video_pause)
    ImageView video_pause;

    private int id;
    private String url, imageUrl, title;
    public static final String ID = "ID";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String URL = "VideoUrl";
    public static final String VIDEO_TITLE = "VIDEO_TITLE"; // 标题
    public static final String DIAMOND_NUMBER = "DIAMOND_NUMBER"; // 作品已赚
    private boolean isPlay = true;

    @Override
    protected void loadData() {
        LogUtils.d("isVisibleToUser：loadData()" + "  标题:" + title);
        video.start();
        // 记录用户观看次数
        videoClick();
        Constants.videoReadSecond = SharedPrefHelper.getInstance().getVideoWatchTime();
    }

    @Override
    protected void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_video);
    }

    @OnClick({R.id.rl_like, R.id.share, R.id.view_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_like:
                // 点赞
                showToast("已经点赞了！");
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
                } else {
                    video.start();
                    video_pause.setVisibility(View.GONE);
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
        vote_progress.setMax(MAX_SECOND);
        vote_progress.setText(String.valueOf(Constants.videoVoteNumber));
        if (Constants.videoVoteNumber > 0) {
            vote_progress.setProgress(MAX_SECOND);
            SharedPrefHelper.getInstance().setVideoWatchTime(0);
        } else {
            vote_progress.setProgress(SharedPrefHelper.getInstance().getVideoWatchTime());
        }
        Bundle bundle = getArguments();
        id = bundle.getInt(ID, 0);
        title = bundle.getString(VIDEO_TITLE);
        content.setText(title);
        diamond_num.setText("作品已赚   " + bundle.getString(DIAMOND_NUMBER));
        url = bundle.getString(URL);
        imageUrl = bundle.getString(IMAGE_URL);
//        video.setLooping(true);
        video.setAVOptions(new AVOptions());
        video.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        video.setOnInfoListener(infoListener);
        video.setOnErrorListener(errorListener);
        video.setOnCompletionListener(completionListener);
        video.setVideoPath(url);
    }

    private PLOnCompletionListener completionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
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
//            video_pause.setVisibility(View.GONE);
        } else {
//            image.setVisibility(View.VISIBLE);
            video_pause.setVisibility(View.GONE);
            video.stopPlayback();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        videoVoteData();
        LogUtils.d("isVisibleToUser：onResume()" + "  标题:" + title);
        GlideLoading.getInstance().loadImgUrlNyImgLoader(getActivity(), imageUrl, image);
        LogUtils.d("标题--" + title + "  " + video.isShown() + "  正在播放：" + video.isPlaying());
        if (video != null && isPause) {
            isPause = false;
            video.start();
        }
    }

    private boolean isPause = false;

    @Override
    public void onPause() {
        super.onPause();
        if (video.isPlaying())
            isPause = true;
        LogUtils.d("isVisibleToUser：onPause()" + "  标题:" + title);
        if (video != null) {
            video.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("isVisibleToUser：onDestroy()" + "  标题:" + title);
        if (video != null) {
            // true代表清除最后一帧画面
            video.stopPlayback();
        }
    }

    /**
     * 查询可用余票
     */
    public void videoVoteData() {
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

                    like_number.setText(String.valueOf(bean.getReturn().getArticle_vote_count()));
                    share.setText(String.valueOf(bean.getReturn().getShare_total()));
                    if (bean.getReturn().getVote_type() == 1) {
                        like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like_select));
                    } else {
                        like.setImageDrawable(getResources().getDrawable(R.mipmap.video_like));
                    }
                    ((VideoVoteActivity) getActivity()).setWalletUI(bean.getReturn().getEinnahmen_count());
                    vote_progress.setText(String.valueOf(bean.getReturn().getTotal_votes())); // 剩余票数
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
            }
        };
        request.subscribe(upDataSubscriber);
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
}
