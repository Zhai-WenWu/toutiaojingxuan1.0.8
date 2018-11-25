package com.deshang.ttjx.ui.mywidget.mychart;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by loro on 2017/2/8.
 */
public class KLineBean {

    private int ErrCode;
    private String ErrInf;
    private String StockID;
    private String StockName;
    private List<KLineAyBean> KLineAy;

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getErrInf() {
        return ErrInf;
    }

    public void setErrInf(String ErrInf) {
        this.ErrInf = ErrInf;
    }

    public String getStockID() {
        return StockID;
    }

    public void setStockID(String StockID) {
        this.StockID = StockID;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String StockName) {
        this.StockName = StockName;
    }

    public List<KLineAyBean> getKLineAy() {
        return KLineAy;
    }

    public void setKLineAy(List<KLineAyBean> KLineAy) {
        this.KLineAy = KLineAy;
    }

    public static class KLineAyBean {

        @Expose
        public double Close;
        @Expose
        public double Open;
        @Expose
        public double High;
        @Expose
        public double Low;
        @Expose
        public long Volume;
        @Expose
        public double Amount;
        @Expose
        public int Time;
    }
}
