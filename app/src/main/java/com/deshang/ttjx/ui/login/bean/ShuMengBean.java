package com.deshang.ttjx.ui.login.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * 数盟返回bean
 * Created by L on 2018/7/26.
 */

public class ShuMengBean extends BaseResponse {

    /**
     * protocol : 2
     * err : -3
     * device_type : 0
     * ver : 0
     * normal_times : 0
     * duplicate_times : 0
     * update_times : 0
     * recall_times : 0
     */

    private int protocol;
    private int err;
    private int device_type;
    private String ver;
    private int normal_times;
    private int duplicate_times;
    private int update_times;
    private int recall_times;

    @Override
    public String toString() {
        return "ShuMengBean{" +
                "protocol=" + protocol +
                ", err=" + err +
                ", device_type=" + device_type +
                ", ver='" + ver + '\'' +
                ", normal_times=" + normal_times +
                ", duplicate_times=" + duplicate_times +
                ", update_times=" + update_times +
                ", recall_times=" + recall_times +
                '}';
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public int getNormal_times() {
        return normal_times;
    }

    public void setNormal_times(int normal_times) {
        this.normal_times = normal_times;
    }

    public int getDuplicate_times() {
        return duplicate_times;
    }

    public void setDuplicate_times(int duplicate_times) {
        this.duplicate_times = duplicate_times;
    }

    public int getUpdate_times() {
        return update_times;
    }

    public void setUpdate_times(int update_times) {
        this.update_times = update_times;
    }

    public int getRecall_times() {
        return recall_times;
    }

    public void setRecall_times(int recall_times) {
        this.recall_times = recall_times;
    }
}
