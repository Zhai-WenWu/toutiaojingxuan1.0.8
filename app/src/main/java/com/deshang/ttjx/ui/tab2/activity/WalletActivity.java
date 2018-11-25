package com.deshang.ttjx.ui.tab2.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.BubbleContainer;
import com.deshang.ttjx.ui.tab2.bean.BubbleBean;
import com.deshang.ttjx.ui.tab2.persenter.WalletPresenter;
import com.deshang.ttjx.ui.tab2.view.WalletView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by L on 2018/9/25.
 */

public class WalletActivity extends MvpSimpleActivity<WalletView, WalletPresenter> implements WalletView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.image_earth)
    ImageView earth;
    @BindView(R.id.container)
    BubbleContainer container;
//    @BindView(R.id.rl_production)
//    RelativeLayout rl_production;

    private int bubbleSize = 0;
    private List<BubbleBean.ReturnBean> data;
    private int bubbleWidth, bubbleHeight, bgWidth, bgHeight;

    {
        data = new ArrayList<>();
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_wallet);
    }

    @Override
    public void initView() {
        titleBar.setTitle("钱包");
        titleBar.setBack(true);
        getPresenter().getBubbleData();
        Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wallet_bubble_bg1);
        bubbleWidth = bg.getWidth();
        bubbleHeight = bg.getHeight();
        ViewTreeObserver vto = container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                bgWidth = container.getWidth();
                bgHeight = container.getHeight();
            }
        });
    }

    @OnClick({R.id.tv_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                if (data.size() == 0) {
                    showToast("已经没有可领取的红钻了");
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < data.size(); i++) {
                        stringBuffer.append(data.get(i).getId() + ",");
                    }
                    String strId = stringBuffer.toString().trim();
                    if (strId.length() > 0) {
                        getPresenter().receiveAllBubble(strId.substring(0, strId.length() - 1));
                    }
                }
                break;
        }
    }

    @Override
    public void getBubbleData(BubbleBean bean) {
//        bubbleBean = bean;
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() != null && bean.getReturn().size() > 0) {
                data.clear();
                data.addAll(bean.getReturn());
                bubbleSize = bean.getReturn().size();
                LogUtils.d("bgWidth:" + bgWidth + " bgHeight:" + bgHeight + " bubbleWidth:" + bubbleWidth + " bubbleHeight:" + bubbleHeight);
                for (int i = 0; i < bean.getReturn().size(); i++) {
                    int widthRandom = (int) (Math.random() * (bgWidth - bubbleWidth)); //
                    int heightRandom = (int) (Math.random() * (bgHeight - bubbleHeight)); //
                    addBubble(bean.getReturn().get(i).getId(), i, widthRandom, heightRandom, bean.getReturn().get(i).getGold());
                }
            } else {
                addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, 0, "正在生产中");
            }
        } else {
            showToast(bean.getErrinf());
            addBubble(0, 0, bgWidth / 2 - bubbleWidth / 2, 0, "正在生产中");
        }
    }

    @Override
    public void receiveBubble(BaseBean bean, View view, final int id) {
        if (bean.errcode == 0) {
//            container.removeData(index);
//            container.removeView(view);
            for (int i = 0; i < data.size(); i++) {
                if (id == data.get(i).getId()) {
                    data.remove(i);
                    break;
                }
            }
            bubbleClose(view);
            bubbleSize--;
            if (bubbleSize <= 0) {
                container.removeAllViews();
                container.clearData();
                getPresenter().getBubbleData();
            }
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void receiveAllBubble(BaseBean bean) {
        if (bean.errcode == 0) {
            container.removeAllViews();
            container.clearData();
            data.clear();
            getPresenter().getBubbleData();
        } else {
            showToast(bean.errinf);
        }
    }

    // 添加气泡
    private void addBubble(final int id, final int index, final int x, final int y, final String str) {
        LogUtils.d("x:" + x + " y:" + y);
        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                final View view = LayoutInflater.from(WalletActivity.this).inflate(R.layout.item_bubble, null);
                RelativeLayout item = (RelativeLayout) view.findViewById(R.id.rl_bubble);
                ImageView image1 = (ImageView) view.findViewById(R.id.image1);
                ImageView image2 = (ImageView) view.findViewById(R.id.image2);
                ImageView image3 = (ImageView) view.findViewById(R.id.image3);
                TextView tv_diamond = (TextView) view.findViewById(R.id.tv_diamond);
                tv_diamond.setText(str);
                rotatingAnimation(image1, 0);
                rotatingAnimation(image2, 1);
                rotatingStar(image3, 0);
                bubbleAnimation(item);
                container.setChildPosition(x, y, view);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (id != 0) {
                            getPresenter().receiveBubble(id, view);
                        } else {
                            showToast("阅读新闻可获取红钻");
                        }
                    }
                });
                container.addView(view);
            }
        }, (index) * 50);
    }

    // 气泡上下浮动动画
    private void bubbleAnimation(View view) {
        TranslateAnimation animation;
        if ((int) (Math.random() * 2) == 1) {
            animation = new TranslateAnimation(0, 0, 5, -5);
        } else {
            animation = new TranslateAnimation(0, 0, -5, 5);
        }
        animation.setInterpolator(new CycleInterpolator(1));
        animation.setDuration(3000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);
    }

    // 隐藏泡泡动画
    private void bubbleClose(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();

//        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
//        alphaAnimation.setDuration(500);//设置动画持续周期
//        alphaAnimation.setFillAfter(true);
//        view.startAnimation(alphaAnimation);
    }

    // 旋转动画
    private void rotatingAnimation(View view, int type) {
        RotateAnimation rotate;
        if (type == 1) {
            // 顺时针
            rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            // 逆时针
            rotate = new RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(5000);//设置动画持续周期
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.RESTART);
        view.startAnimation(rotate);
    }

    // 旋转动画
    private void rotatingStar(final View view, final int type) {
        RotateAnimation rotate;
        if (type == 1) {
            rotate = new RotateAnimation(90f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotate = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(3000);//设置动画持续周期
        rotate.setFillAfter(true);

        AlphaAnimation alphaAnimation;
        if (type == 1) {
            alphaAnimation = new AlphaAnimation(1f, 0f);
        } else {
            alphaAnimation = new AlphaAnimation(0f, 1f);
        }
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setDuration(2500);//设置动画持续周期

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotate);
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Message message = new Message();
                message.what = type == 1 ? 0 : 1;
                message.obj = view;
                handler.sendMessageDelayed(message, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(set);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rotatingStar((View) msg.obj, msg.what);
        }
    };

    @Override
    public WalletPresenter createPresenter() {
        return new WalletPresenter();
    }
}
