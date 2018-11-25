package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/10/19.
 */

public class GeeTestBean extends BaseResponse {

    /**
     * risk_level : 7
     * captcha : success
     * risk_code : ["4001","0"]
     * status :
     */

    private int risk_level;
    private String captcha;
    private String status;
    private List<String> risk_code;

    public int getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(int risk_level) {
        this.risk_level = risk_level;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRisk_code() {
        return risk_code;
    }

    public void setRisk_code(List<String> risk_code) {
        this.risk_code = risk_code;
    }
}
