package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/7/5.
 */

public class WithdrawalMoneyBean extends BaseResponse {

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

        private String change;
        private int continuity_days;
        private int type;
        private int authorize;
        private int newreg;

        public int getNewreg() {
            return newreg;
        }

        public void setNewreg(int newreg) {
            this.newreg = newreg;
        }

        public int getAuthorize() {
            return authorize;
        }

        public void setAuthorize(int authorize) {
            this.authorize = authorize;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public int getContinuity_days() {
            return continuity_days;
        }

        public void setContinuity_days(int continuity_days) {
            this.continuity_days = continuity_days;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
