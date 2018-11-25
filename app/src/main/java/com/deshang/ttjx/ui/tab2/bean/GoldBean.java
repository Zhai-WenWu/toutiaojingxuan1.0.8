package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/7/5.
 */

public class GoldBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private List<ChangeOrGoldBean> Return;

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

    public List<ChangeOrGoldBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ChangeOrGoldBean> Return) {
        this.Return = Return;
    }

}
