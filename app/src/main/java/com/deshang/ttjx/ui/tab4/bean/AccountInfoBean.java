package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/20.
 */

public class AccountInfoBean extends BaseResponse {

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

        private String title;
        private String gold;
        private String create_time;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
