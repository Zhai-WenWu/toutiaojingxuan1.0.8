package com.deshang.ttjx.ui.login.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2017/11/9.
 */

public class NewLoginBean extends BaseResponse {

    private int errcode;
    private String errinf;
    private ReturnBean Return;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrinf() {
        return errinf;
    }

    public void setErrinf(String errinf) {
        this.errinf = errinf;
    }

    public ReturnBean getReturn() {
        return Return;
    }

    public void setReturn(ReturnBean Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private int userid;
        private String mobile;
        private String token;
        private String create_time;
        private String update_time;
        private int continuity_days;
        private int first;
        private int new_type;
        private int new_wx;

        public int getNew_wx() {
            return new_wx;
        }

        public void setNew_wx(int new_wx) {
            this.new_wx = new_wx;
        }

        public int getNew_type() {
            return new_type;
        }

        public void setNew_type(int new_type) {
            this.new_type = new_type;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getContinuity_days() {
            return continuity_days;
        }

        public void setContinuity_days(int continuity_days) {
            this.continuity_days = continuity_days;
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }
    }
}
