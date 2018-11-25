package com.deshang.ttjx.ui.tab4.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.dialog.SaleTBDialog;
import com.deshang.ttjx.ui.mywidget.dialog.WalletDialog;
import com.deshang.ttjx.ui.mywidget.dialog.WalletGXDialog;
import com.deshang.ttjx.ui.mywidget.dialog.WalletSuccessDialog;
import com.deshang.ttjx.ui.tab4.bean.SaleOrWithdrawalData;
import com.deshang.ttjx.ui.tab4.persenter.SaleTBPresenter;
import com.deshang.ttjx.ui.tab4.view.SaleTBView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 红钻卖出及提现
 * Created by L on 2018/9/28.
 */

public class SaleTBActivity extends MvpSimpleActivity<SaleTBView, SaleTBPresenter> implements SaleTBView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tb_num)
    TextView tbNum;
    @BindView(R.id.money_num)
    TextView moneyNum;

    private SaleOrWithdrawalData data;
    private String canSaleNum, money, oneTB;
    private String saleTBNum; // 卖出的红钻数
    private String saleTBMoney; // 卖出红钻获得的钱数
    private String withdrawalNum; // 提现成功的钱数

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_sale_tb);
    }

    @Override
    public void initView() {
        titleBar.setTitle("卖出提现");
        titleBar.setBack(true);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "arial_narrow.ttf");
        tbNum.setTypeface(typeface);
//        tbNum.getPaint().setFakeBoldText(true);
        moneyNum.setTypeface(typeface);
//        moneyNum.getPaint().setFakeBoldText(true);
        getPresenter().getTBData();
    }

    @OnClick({R.id.sale, R.id.withdrawal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sale:
                // 卖出
                SaleTBDialog dialog = new SaleTBDialog(SaleTBActivity.this, canSaleNum, oneTB);
                dialog.setOnSureListener(new SaleTBDialog.OnSureListener() {
                    @Override
                    public void onSureClick(String tbNum) {
                        // 卖出红钻
                        saleTBNum = tbNum;
                        saleTBMoney = ProjectUtils.getTwo(Integer.valueOf(tbNum) * Double.valueOf(oneTB));
                        getPresenter().saleTB(tbNum);
                    }
                });
                dialog.show();
                break;

            case R.id.withdrawal:
                // 提现
                String minMoney;
                if (data != null) {
                    minMoney = data.getData().getLowest_cash();
                } else {
                    minMoney = "1";
                }
                WalletDialog dialog1 = new WalletDialog(SaleTBActivity.this, money, minMoney);
                dialog1.setOnSureListener(new WalletDialog.OnSureListener() {
                    @Override
                    public void onSureClick(String withdrawal) {
                        // TODO 提现
                        withdrawalNum = withdrawal;
                        getPresenter().withdrawal(withdrawal, 1);
                    }
                });
                dialog1.show();
                break;
        }
    }

    @Override
    public void saleTB(BaseBean bean) {
        if (bean.errcode == 0) {
            getPresenter().getTBData();
            WalletGXDialog dialog = new WalletGXDialog(this, saleTBNum, saleTBMoney);
            dialog.setOnSureListener(new WalletGXDialog.OnToWithdrawalListener() {
                @Override
                public void onWithdrawalClick(int type) {
                    if (type == 0) {
                        // 去提现
                        String minMoney;
                        if (data != null) {
                            minMoney = data.getData().getLowest_cash();
                        } else {
                            minMoney = "1";
                        }
                        WalletDialog dialog1 = new WalletDialog(SaleTBActivity.this, money, minMoney);
                        dialog1.setOnSureListener(new WalletDialog.OnSureListener() {
                            @Override
                            public void onSureClick(String withdrawal) {
                                // TODO 提现
                                getPresenter().withdrawal(withdrawal, 1);
                            }
                        });
                        dialog1.show();
                    } else {
                        // 去赚钱页面
                        Constants.turn_to_other_tab = 2;
                        finish();
                    }
                }
            });
            dialog.show();
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void getTBData(SaleOrWithdrawalData bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getData() == null)
                return;
            data = bean;
            canSaleNum = String.valueOf(bean.getData().getRed());
            money = bean.getData().getChange();
            oneTB = bean.getData().getGold_price();

            tbNum.setText(String.valueOf(bean.getData().getRed()));
            moneyNum.setText(bean.getData().getChange());
        } else {
            showToast(bean.getErrinf());
        }
    }

    @Override
    public void withdrawalSuccess(BaseBean bean) {
        if (bean.errcode == 0) {
            getPresenter().getTBData();
            WalletSuccessDialog dialog = new WalletSuccessDialog(this, withdrawalNum);
            dialog.setOnGetMoreClickListener(new WalletSuccessDialog.OnGetMoreClickListener() {
                @Override
                public void onWithdrawalClick(int type) {
                    if (type == 0) {
                        UIManager.turnToAct(SaleTBActivity.this, ShowIncomeActivity.class);
                    } else {
                        // 去赚钱页面
                        Constants.turn_to_other_tab = 2;
                        finish();
                    }
                }
            });
            dialog.show();
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public SaleTBPresenter createPresenter() {
        return new SaleTBPresenter();
    }
}
