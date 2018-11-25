package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by 13364 on 2018/10/27.
 */

public class MyVedioBean extends BaseResponse {


    /**
     * errinf :
     * errcode : 0
     * Return : [{"aid":40,"url":["http://alimov2.a.yximgs.com/upic/2018/10/12/09/BMjAxODEwMTIwOTExMzNfNjkzODc4OTIwXzg0Mjg3ODkxNDFfMl8z_b_Be0aabb1ee54dd5b3c5917f3d581e8b67.mp4?tag=1-1540610697-h-0-fvjkdoocen-7923e2ed4348bbef","http://txmov2.a.yximgs.com/upic/2018/10/12/09/BMjAxODEwMTIwOTExMzNfNjkzODc4OTIwXzg0Mjg3ODkxNDFfMl8z_b_Be0aabb1ee54dd5b3c5917f3d581e8b67.mp4?tag=1-1540610697-h-1-kebmq9ixfr-22db50ef557c5439"],"cover_img":"http://tx2.a.yximgs.com/upic/2018/10/12/09/BMjAxODEwMTIwOTExMzNfNjkzODc4OTIwXzg0Mjg3ODkxNDFfMl8z_low_Be2dae1432838bca4b62930aae34c2e4b.webp?tag=1-1540610697-h-1-rzjsl9eow4-bd63bf91e75b1c3a","gain_gold":"0.0000","article_vote_count":2}]
     */

    private String errinf;
    private int errcode;
    private List<ReturnBean> Return;

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

    public List<ReturnBean> getReturn() {
        return Return;
    }

    public void setReturn(List<ReturnBean> Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private int aid;
        private String cover_img;
        private String gain_gold;
        private int article_vote_count;
        private String title;
        private List<String> url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getGain_gold() {
            return gain_gold;
        }

        public void setGain_gold(String gain_gold) {
            this.gain_gold = gain_gold;
        }

        public int getArticle_vote_count() {
            return article_vote_count;
        }

        public void setArticle_vote_count(int article_vote_count) {
            this.article_vote_count = article_vote_count;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
