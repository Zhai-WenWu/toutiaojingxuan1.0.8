package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by 13364 on 2018/9/27.
 */

public class GroupBean extends BaseResponse {


    /**
     * errinf :
     * errcode : 0
     * Return : [{"title":"官方群1","qq":"111111","people":"500","type":0},{"title":"111","qq":"222","people":"44","type":0}]
     */

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
        /**
         * title : 官方群1
         * qq : 111111
         * people : 500
         * type : 0
         */

        private String title;
        private String qq;
        private String people;
        private int type;
        private String keyaz;
        private String key;

        public String getKeyaz() {
            return keyaz;
        }

        public void setKeyaz(String keyaz) {
            this.keyaz = keyaz;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
