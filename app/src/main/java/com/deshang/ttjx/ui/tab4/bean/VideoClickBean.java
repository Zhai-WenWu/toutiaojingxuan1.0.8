package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/10/30.
 */

public class VideoClickBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private int volume_count;

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

    public int getVolume_count() {
        return volume_count;
    }

    public void setVolume_count(int volume_count) {
        this.volume_count = volume_count;
    }
}
