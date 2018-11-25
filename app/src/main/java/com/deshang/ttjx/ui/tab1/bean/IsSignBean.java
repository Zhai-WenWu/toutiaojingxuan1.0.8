package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/8/17.
 */

public class IsSignBean extends BaseResponse {

    private int signtype;
    private String errinf;
    private int errcode;

    public int getSigntype() {
        return signtype;
    }

    public void setSigntype(int signtype) {
        this.signtype = signtype;
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
}
