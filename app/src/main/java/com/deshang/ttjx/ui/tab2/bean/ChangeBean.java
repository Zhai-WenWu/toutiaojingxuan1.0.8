package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/7/5.
 */

public class ChangeBean extends BaseResponse {

    private String change;
    private int gold;
    private String wallet_info;
    private String errinf;
    private int errcode;
    private List<ChangeOrGoldBean> Return;

    public String getWallet_info() {
        return wallet_info;
    }

    public void setWallet_info(String wallet_info) {
        this.wallet_info = wallet_info;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getErrinf() {
        return errinf;
    }

    public void setErrinf(String errinf) {
        this.errinf = errinf;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public List<ChangeOrGoldBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ChangeOrGoldBean> Return) {
        this.Return = Return;
    }
}
