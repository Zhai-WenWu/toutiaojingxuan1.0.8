package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by 13364 on 2018/9/27.
 */

public class BallotBean extends BaseResponse {

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

        private String time;
        private List<DataBean> data;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * aid : 110667
             * title : “瓷娃娃”福原爱，花式秀恩爱，嫁到中国很幸福！
             * cat_id : 6
             * gain_gold : 0.0000
             */

            private int aid;
            private String title;
            private int cat_id;
            private String gain_gold;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCat_id() {
                return cat_id;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public String getGain_gold() {
                return gain_gold;
            }

            public void setGain_gold(String gain_gold) {
                this.gain_gold = gain_gold;
            }
        }
    }
}
