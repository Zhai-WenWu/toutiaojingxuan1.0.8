package com.deshang.ttjx.ui.tab3.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/13.
 */

public class MyApprenticeBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private int apprentice_number;
    private double change;
    private double one_into;
    private List<ApprenticeOrDiscipleBean> Return;

    public int getApprentice_number() {
        return apprentice_number;
    }

    public void setApprentice_number(int apprentice_number) {
        this.apprentice_number = apprentice_number;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
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
