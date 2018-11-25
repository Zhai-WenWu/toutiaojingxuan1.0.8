package com.deshang.ttjx.ui.login.bean;

import com.google.gson.annotations.Expose;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2017/12/5.
 */

public class CodeBean extends BaseResponse {
    @Expose
    public int errcode;
    @Expose
    public String errinf;
}
