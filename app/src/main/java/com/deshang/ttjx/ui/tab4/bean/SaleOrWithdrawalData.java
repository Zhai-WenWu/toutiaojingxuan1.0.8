package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/9/29.
 */

public class SaleOrWithdrawalData extends BaseResponse {

    private int errcode;
    private String errinf;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private double red;
        private String change;
        private String gold_price;
        private String lowest_cash;


        public String getTotal_income() {
            return total_income;
        }

        public void setTotal_income(String total_income) {
            this.total_income = total_income;
        }

        private String total_income;

        public double getRed() {
            return red;
        }

        public void setRed(double red) {
            this.red = red;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getGold_price() {
            return gold_price;
        }

        public void setGold_price(String gold_price) {
            this.gold_price = gold_price;
        }

        public String getLowest_cash() {
            return lowest_cash;
        }

        public void setLowest_cash(String lowest_cash) {
            this.lowest_cash = lowest_cash;
        }
    }
}
