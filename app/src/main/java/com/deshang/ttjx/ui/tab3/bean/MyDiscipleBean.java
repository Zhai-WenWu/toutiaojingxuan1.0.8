package com.deshang.ttjx.ui.tab3.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/13.
 */

public class MyDiscipleBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private int disciple_number;
    private double one_into;
    private List<ApprenticeOrDiscipleBean> Return;

    public int getDisciple_number() {
        return disciple_number;
    }

    public void setDisciple_number(int disciple_number) {
        this.disciple_number = disciple_number;
    }

    public double getOne_into() {
        return one_into;
    }

    public void setOne_into(double one_into) {
        this.one_into = one_into;
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

    public List<ApprenticeOrDiscipleBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ApprenticeOrDiscipleBean> Return) {
        this.Return = Return;
    }
}
