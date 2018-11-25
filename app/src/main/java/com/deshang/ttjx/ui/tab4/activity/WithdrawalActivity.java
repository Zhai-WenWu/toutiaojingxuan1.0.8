package com.deshang.ttjx.ui.tab4.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.NoScrollGridLayoutManager;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.dialog.WithdrawalDialog;
import com.deshang.ttjx.ui.mywidget.dialog.WithdrawalFirstDialog;
import com.deshang.ttjx.ui.mywidget.dialog.WithdrawalFiveMoneyDialog;
import com.deshang.ttjx.ui.tab4.adapter.WithdrawalListAdapter;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalMoneyBean;
import com.deshang.ttjx.ui.tab4.bean.WithdrawalTimeBean;
import com.deshang.ttjx.ui.tab4.persenter.WithdrawalPresenter;
import com.deshang.ttjx.ui.tab4.view.WithdrawalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Observable;
import rx.Subscriber;

/**
 * 提现
 * Created by L on 2018/6/12.
 */

public class WithdrawalActivity extends MvpSimpleActivity<WithdrawalView, WithdrawalPresenter> implements WithdrawalView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_dollar)
    TextView dollar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.withdrawal_now)
    TextView withdrawalNow;
    @BindView(R.id.ll_withdrawal_state)
    LinearLayout llWithdrawalState; // 提现说明
    @BindView(R.id.tv_dollar_num)
    TextView tvDollarNum; // 提现兑换说明标题
    @BindView(R.id.tv_dollar_content)
    TextView tvDollarContent;  // 提现兑换说明文本

    private WithdrawalListAdapter adapter;

    private List<WithdrawalBean> data;
    private double balanceMoney;
    private int selectPosition = 0;

    private WithdrawalMoneyBean bean;
    {
        data = new ArrayList<>();
        data.add(new WithdrawalBean(1, 1000, true));
        data.add(new WithdrawalBean(5, 5000, false));
        data.add(new WithdrawalBean(10, 10000, false));
        data.add(new WithdrawalBean(30, 30000, false));
        data.add(new WithdrawalBean(50, 50000, false));
        data.add(new WithdrawalBean(100, 100000, false));
        data.add(new WithdrawalBean(500, 500000, false));
    }

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_withdrawal);
    }

    @Override
    public void initView() {
        titleBar.setBack(true);
        titleBar.setTitle("提现");

        NoScrollGridLayoutManager gridLayoutManager = new NoScrollGridLayoutManager(this, 3);
        gridLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new WithdrawalListAdapter(this, data);

        adapter.setOnSelectListener(new WithdrawalListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                selectPosition = position;
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelector(false);
                }
                data.get(position).setSelector(true);
                adapter.notifyDataSetChanged();
                if (bean == null || bean.getReturn() == null) {
                    return;
                }
                if (bean.getReturn().getType() == 0) {
                    llWithdrawalState.setVisibility(View.GONE);
                } else {
                    switch (position) {
                        case 0:
                            if (bean.getReturn().getContinuity_days() >= 5) {
                                llWithdrawalState.setVisibility(View.GONE);
                            } else {
                                llWithdrawalState.setVisibility(View.VISIBLE);
                                tvDollarNum.setText("1元提现兑换说明");
                                tvDollarContent.setText("连续登陆5天的用户即可获得一次1元提现机会，但不可累积");
                            }
                            break;
                        default:
                            llWithdrawalState.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        recyclerView.setAdapter(adapter);
        getPresenter().getWithdrawalData();
    }

    @OnClick({R.id.withdrawal_list, R.id.withdrawal_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdrawal_list: // 提现列表
                UIManager.turnToAct(this, WithdrawalAuditListActivity.class);
                break;
            case R.id.withdrawal_now: // 立即提现
                if (bean == null || bean.getReturn() == null) {
                    break;
                }
                if (balanceMoney < data.get(selectPosition).getDollarNum()) {
                    showToast("零钱不足");
                } else if (bean.getReturn().getType() == 0 && data.get(selectPosition).getDollarNum() == 1 && bean.getReturn().getNewreg() == 0) {
                    // 新用户首次提现 而且提现金额是1元 且不满足阅读分享的条件
                    WithdrawalFirstDialog dialog = new WithdrawalFirstDialog(WithdrawalActivity.this);
                    dialog.setOnClickListener(new WithdrawalFirstDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            // 分享文章  去首页
                            Intent intent = new Intent(WithdrawalActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.show();
                } else if (data.get(selectPosition).getDollarNum() >= 5) {
                    // 提现5元或以上
                    getPresenter().getUserWithdrawalTime();
                } else {
                    withdrawalMoney();
                }
                break;
        }
    }

    // 提现操作
    private void withdrawalMoney() {
        if (bean != null && bean.getReturn() != null) {
            if (bean.getReturn().getType() != 0 && data.get(selectPosition).getDollarNum() == 1 && bean.getReturn().getContinuity_days() < 5) {
                showToast("继续连续登陆" + (5 - bean.getReturn().getContinuity_days()) + "天可提现此金额");
                return;
            }
            WithdrawalDialog dialog = new WithdrawalDialog(WithdrawalActivity.this, SharedPrefHelper.getInstance().getUserName(),
                    SharedPrefHelper.getInstance().getAvatar(), bean.getReturn().getAuthorize());
            dialog.setClickListener(new WithdrawalDialog.ClickListener() {
                @Override
                public void onClickListener(int type) {
                    if (type == 0) {
                        //  TODO 去授权
                        getWeChatData();
                    } else {
                        // TODO 去提现
                        getPresenter().withdrawal(data.get(selectPosition).getDollarNum(), 1);
                    }
                }
            });
            dialog.show();
        } else {
            showToast("数据异常请重新加载...");
            getPresenter().getWithdrawalData();
        }
    }

    // 获取微信信息
    private void getWeChatData() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (plat.isClientValid()) {
            // 判断是否存在客户端
            showProgressDialog();
        } else {
            ReceiveGoldToast.makeToast(this, "请安装微信客户端").show();
            return;
        }
        if (plat.isAuthValid()) {
            // 已授权  移除授权信息
            plat.removeAccount(true);
        }
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                dismissProgressDialog();
                PlatformDb db = platform.getDb();
//                LogUtils.d("unionid:" + db.get("unionid") + "  openid:" + db.get("openid") + " icon" + db.get("icon") + " nickname:" + db.get("nickname"));
                commitAuthorization(db.get("unionid"), db.get("openid"), db.get("icon"), db.get("nickname"));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                dismissProgressDialog();
                LogUtils.d("onError" + throwable.toString());
                ReceiveGoldToast.makeToast(WithdrawalActivity.this, "拉取授权失败，请重试").show();
                // 清除授权缓存数据
                platform.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                dismissProgressDialog();
                ReceiveGoldToast.makeToast(WithdrawalActivity.this, "取消授权").show();
                // 清除授权缓存数据
                platform.removeAccount(true);
            }
        });
        plat.showUser(null);
    }

    /**
     * 提交授权信息
     */
    public void commitAuthorization(String uuid, String open_id, final String img, final String name) {
        Observable request = RetrofitUtils.getInstance().commitAuthorization(uuid, open_id, img, name, SharedPrefHelper.getInstance().getToken());
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showToast("提交授权信息异常：" + e.toString());
            }

            @Override
            public void onNext(final BaseBean bean) {
                if (bean.errcode == 0) {
                    SharedPrefHelper.getInstance().setUserName(name);
                    SharedPrefHelper.getInstance().setAvatar(img);
                    WithdrawalDialog dialog = new WithdrawalDialog(WithdrawalActivity.this, SharedPrefHelper.getInstance().getUserName(),
                            SharedPrefHelper.getInstance().getAvatar(), 1);
                    dialog.setClickListener(new WithdrawalDialog.ClickListener() {
                        @Override
                        public void onClickListener(int type) {
                            // TODO 去提现
                            getPresenter().withdrawal(data.get(selectPosition).getDollarNum(), 1);
                        }
                    });
                    dialog.show();
                } else {
                    showToast("授权失败，请重试");
                }
            }
        };
        request.subscribe(upDataSubscriber);
    }

    @Override
    public void getWithdrawalData(WithdrawalMoneyBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;
            this.bean = bean;
            balanceMoney = Double.valueOf(bean.getReturn().getChange());
            dollar.setText("" + balanceMoney);
            if (bean.getReturn().getType() == 0) {
                llWithdrawalState.setVisibility(View.GONE);
                return;
            }
            if (selectPosition == 0 && bean.getReturn().getContinuity_days() >= 5) {
                llWithdrawalState.setVisibility(View.GONE);
            } else if (selectPosition > 2) {
                llWithdrawalState.setVisibility(View.GONE);
            } else {
                llWithdrawalState.setVisibility(View.VISIBLE);
            }

        } else {
            showToast(bean.getErrinf());
        }
    }

    // 提现
    @Override
    public void withdrawalSuccess(BaseBean bean) {
        if (bean.errcode == 0) {
            showToast("提现成功");
            getPresenter().getWithdrawalData();
        } else {
            showToast(bean.errinf);
        }
    }

    //  提交用户授权信息
    @Override
    public void commitAuthorization(BaseBean bean) {

    }

    @Override
    public void getUserWithdrawalTime(WithdrawalTimeBean bean) {
        if (bean.getOnce_type() == 0) {
            // 首日第一次提现
            final WithdrawalFiveMoneyDialog dialog = new WithdrawalFiveMoneyDialog(this);
            dialog.setOnClickListener(new WithdrawalFiveMoneyDialog.OnClickListener() {
                @Override
                public void onClick(int type) {
                    if (type == 1) {
                        // 分享文章  去首页
                        Intent intent = new Intent(WithdrawalActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 继续提现
                        withdrawalMoney();
                    }
                }
            });
            dialog.show();
        } else {
            // TODO 弹提现弹窗
            withdrawalMoney();
        }
    }

    @Override
    public WithdrawalPresenter createPresenter() {
        return new WithdrawalPresenter();
    }
}
