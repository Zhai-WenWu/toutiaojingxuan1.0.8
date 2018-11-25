package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/8/17.
 */

public class WithdrawalTimeBean extends BaseResponse {

    private int once_type;
    private String errinf;
    private int errcode;

    public int getOnce_type() {
        return once_type;
    }

    public void setOnce_type(int once_type) {
        this.once_type = once_type;
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
