package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/11.
 */

public class NewsTypeBean extends BaseResponse {

    private int errcode;
    private String errinfo;
    private List<DataBean> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrinfo() {
        return errinfo;
    }

    public void setErrinfo(String errinfo) {
        this.errinfo = errinfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 推荐
         * sdk1 : 4
         * sdk2 : 1
         * sdk3 : 1
         */

        private int id;
        private String name;
        private int sdk1;
        private int sdk2;
        private int sdk3;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSdk1() {
            return sdk1;
        }

        public void setSdk1(int sdk1) {
            this.sdk1 = sdk1;
        }

        public int getSdk2() {
            return sdk2;
        }

        public void setSdk2(int sdk2) {
            this.sdk2 = sdk2;
        }

        public int getSdk3() {
            return sdk3;
        }

        public void setSdk3(int sdk3) {
            this.sdk3 = sdk3;
        }
    }
}
