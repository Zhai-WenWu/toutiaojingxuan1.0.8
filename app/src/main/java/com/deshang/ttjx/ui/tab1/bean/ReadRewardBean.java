package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * 阅读奖励
 * Created by L on 2018/6/13.
 */

public class ReadRewardBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private ReturnBean Return;

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

    public ReturnBean getReturn() {
        return Return;
    }

    public void setReturn(ReturnBean Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private String gold;

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }
    }
}
