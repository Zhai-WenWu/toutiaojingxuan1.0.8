package com.deshang.ttjx.ui.main.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by 13364 on 2017/11/30.
 * <p>
 * 更新接口
 * "ErrCode":0,"force":1,"version":"1.0","url":""
 */

public class UpdateBean extends BaseResponse {

    private int ErrCode;
    private int force;
    private String version;
    private String url;
    private String share_url;
    private String video_url;
    private String apprentice_url;
    private String desc;
    private String appurl;
    private String subhead;
    private String sharehead;
    private int isOnLine; // 应用宝 1
    private int isOnLine1; // 应用宝 2  线上版
    private int isOnLine2; // 应用宝 3
    private int SmallChannelOnLine; // 小渠道上线开关
    private int UnionOnLine; // 硬核联盟上线开关
    private int read_slide1;
    private int read_slide2;
    private int read_video_vote; // 视频计时多长时间加一张票
    private int video_effective_number; //观看视频有效次数

    public int getVideo_effective_number() {
        return video_effective_number;
    }

    public void setVideo_effective_number(int video_effective_number) {
        this.video_effective_number = video_effective_number;
    }




    public int getRead_video_vote() {
        return read_video_vote;
    }

    public void setRead_video_vote(int read_video_vote) {
        this.read_video_vote = read_video_vote;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getRead_slide1() {
        return read_slide1;
    }

    public void setRead_slide1(int read_slide1) {
        this.read_slide1 = read_slide1;
    }

    public int getRead_slide2() {
        return read_slide2;
    }

    public void setRead_slide2(int read_slide2) {
        this.read_slide2 = read_slide2;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public int getSmallChannelOnLine() {
        return SmallChannelOnLine;
    }

    public void setSmallChannelOnLine(int smallChannelOnLine) {
        SmallChannelOnLine = smallChannelOnLine;
    }

    public int getUnionOnLine() {
        return UnionOnLine;
    }

    public void setUnionOnLine(int unionOnLine) {
        UnionOnLine = unionOnLine;
    }

    public int getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(int isOnLine) {
        this.isOnLine = isOnLine;
    }

    public int getIsOnLine1() {
        return isOnLine1;
    }

    public void setIsOnLine1(int isOnLine1) {
        this.isOnLine1 = isOnLine1;
    }

    public int getIsOnLine2() {
        return isOnLine2;
    }

    public void setIsOnLine2(int isOnLine2) {
        this.isOnLine2 = isOnLine2;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getSharehead() {
        return sharehead;
    }

    public void setSharehead(String sharehead) {
        this.sharehead = sharehead;
    }

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getApprentice_url() {
        return apprentice_url;
    }

    public void setApprentice_url(String apprentice_url) {
        this.apprentice_url = apprentice_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
