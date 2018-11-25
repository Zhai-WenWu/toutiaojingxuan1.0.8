package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/9/25.
 */

public class BubbleBean extends BaseResponse {

    private String total;
    private String today_gold;
    private String gold_price;
    private String errinf;
    private int errcode;
    private int receive_statu;
    private List<ReturnBean> Return;

    public int getReceive_statu() {
        return receive_statu;
    }

    public void setReceive_statu(int receive_statu) {
        this.receive_statu = receive_statu;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getToday_gold() {
        return today_gold;
    }

    public void setToday_gold(String today_gold) {
        this.today_gold = today_gold;
    }

    public String getGold_price() {
        return gold_price;
    }

    public void setGold_price(String gold_price) {
        this.gold_price = gold_price;
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

    public List<ReturnBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ReturnBean> Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private int id;
        private int userid;
        private String gold;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }
    }
}
