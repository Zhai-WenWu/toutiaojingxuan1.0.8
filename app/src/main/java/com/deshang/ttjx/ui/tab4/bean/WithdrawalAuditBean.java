package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/15.
 */

public class WithdrawalAuditBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private List<ReturnBean> Return;

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

    public List<ReturnBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ReturnBean> Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private int userid;
        private String cash;
        private int statu;
        private String time;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public int getStatu() {
            return statu;
        }

        public void setStatu(int statu) {
            this.statu = statu;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
