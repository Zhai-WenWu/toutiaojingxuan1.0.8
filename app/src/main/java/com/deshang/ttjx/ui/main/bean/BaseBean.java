package com.deshang.ttjx.ui.main.bean;

import com.google.gson.annotations.Expose;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 *
 * Created by L on 2017/12/11.
 */

public class BaseBean extends BaseResponse {

    @Expose
    public int errcode;
    @Expose
    public String errinf;

}
