package com.deshang.ttjx.ui.tab3.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/7/11.
 */

public class ApprenticeOrDiscipleBean extends BaseResponse {

    private String img;
    private String name;
    private String mobile;
    private String finish_time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }
}
