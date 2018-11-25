package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/6/20.
 */

public class ReceiveRedPacketBean extends BaseResponse {

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

        private int gold;

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
