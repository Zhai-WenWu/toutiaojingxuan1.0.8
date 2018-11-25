package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2017/12/8.
 */

public class StockDetailBean extends BaseResponse {

    private int ErrCode;
    private String ErrInf;
    private String StockID;
    private String StockName;
    private double LastClose;
    private double Close;
    private double Open;
    private double High;
    private double Low;
    private double NewPrice;
    private int Volume;
    private double BuyPrice1;
    private int BuyVolume1;
    private double SellPrice1;
    private int SellVolume1;
    private int Time;
    private int OperationTime;
    private double BeforeHigh;
    private double BeforeLow;
    private int BeforeVolumn;
    private double EndHigh;
    private double EndLow;
    private int EndVolume;
    private int BeforeTime;
    private int EndTime;
    private double BeforePrice;
    private int SystemStockState;
    private List<TickAyBean> TickAy;

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

    public double getLastClose() {
        return LastClose;
    }

    public void setLastClose(double LastClose) {
        this.LastClose = LastClose;
    }

    public double getClose() {
        return Close;
    }

    public void setClose(double Close) {
        this.Close = Close;
    }

    public double getOpen() {
        return Open;
    }

    public void setOpen(double Open) {
        this.Open = Open;
    }

    public double getHigh() {
        return High;
    }

    public void setHigh(double High) {
        this.High = High;
    }

    public double getLow() {
        return Low;
    }

    public void setLow(double Low) {
        this.Low = Low;
    }

    public double getNewPrice() {
        return NewPrice;
    }

    public void setNewPrice(double NewPrice) {
        this.NewPrice = NewPrice;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int Volume) {
        this.Volume = Volume;
    }

    public double getBuyPrice1() {
        return BuyPrice1;
    }

    public void setBuyPrice1(double BuyPrice1) {
        this.BuyPrice1 = BuyPrice1;
    }

    public int getBuyVolume1() {
        return BuyVolume1;
    }

    public void setBuyVolume1(int BuyVolume1) {
        this.BuyVolume1 = BuyVolume1;
    }

    public double getSellPrice1() {
        return SellPrice1;
    }

    public void setSellPrice1(double SellPrice1) {
        this.SellPrice1 = SellPrice1;
    }

    public int getSellVolume1() {
        return SellVolume1;
    }

    public void setSellVolume1(int SellVolume1) {
        this.SellVolume1 = SellVolume1;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int Time) {
        this.Time = Time;
    }

    public int getOperationTime() {
        return OperationTime;
    }

    public void setOperationTime(int OperationTime) {
        this.OperationTime = OperationTime;
    }

    public double getBeforeHigh() {
        return BeforeHigh;
    }

    public void setBeforeHigh(double BeforeHigh) {
        this.BeforeHigh = BeforeHigh;
    }

    public double getBeforeLow() {
        return BeforeLow;
    }

    public void setBeforeLow(double BeforeLow) {
        this.BeforeLow = BeforeLow;
    }

    public int getBeforeVolumn() {
        return BeforeVolumn;
    }

    public void setBeforeVolumn(int BeforeVolumn) {
        this.BeforeVolumn = BeforeVolumn;
    }

    public double getEndHigh() {
        return EndHigh;
    }

    public void setEndHigh(double EndHigh) {
        this.EndHigh = EndHigh;
    }

    public double getEndLow() {
        return EndLow;
    }

    public void setEndLow(double EndLow) {
        this.EndLow = EndLow;
    }

    public int getEndVolume() {
        return EndVolume;
    }

    public void setEndVolume(int EndVolume) {
        this.EndVolume = EndVolume;
    }

    public int getBeforeTime() {
        return BeforeTime;
    }

    public void setBeforeTime(int BeforeTime) {
        this.BeforeTime = BeforeTime;
    }

    public int getEndTime() {
        return EndTime;
    }

    public void setEndTime(int EndTime) {
        this.EndTime = EndTime;
    }

    public double getBeforePrice() {
        return BeforePrice;
    }

    public void setBeforePrice(double BeforePrice) {
        this.BeforePrice = BeforePrice;
    }

    public int getSystemStockState() {
        return SystemStockState;
    }

    public void setSystemStockState(int SystemStockState) {
        this.SystemStockState = SystemStockState;
    }

    public List<TickAyBean> getTickAy() {
        return TickAy;
    }

    public void setTickAy(List<TickAyBean> TickAy) {
        this.TickAy = TickAy;
    }

    public static class TickAyBean {
        /**
         * NewPrice : 174.36
         * Time : 1512610259
         * Volume : 457896
         */

        private double NewPrice;
        private int Time;
        private int Volume;

        public double getNewPrice() {
            return NewPrice;
        }

        public void setNewPrice(double NewPrice) {
            this.NewPrice = NewPrice;
        }

        public int getTime() {
            return Time;
        }

        public void setTime(int Time) {
            this.Time = Time;
        }

        public int getVolume() {
            return Volume;
        }

        public void setVolume(int Volume) {
            this.Volume = Volume;
        }
    }
}
