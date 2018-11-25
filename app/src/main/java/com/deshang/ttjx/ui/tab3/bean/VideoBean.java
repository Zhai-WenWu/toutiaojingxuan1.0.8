package com.deshang.ttjx.ui.tab3.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

import java.util.List;

/**
 * Created by L on 2018/6/26.
 */

public class VideoBean extends BaseResponse {

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

        private int id;
        private int is_recomm;
        private int is_top;
        private String title;
        private String cover_img;


        private String clicks;
        private int comment_count;
        private String create_time;
        private int is_display;
        private int article_vote_count;
        private double article_vote_sum;
        private int share_total;
        private int vote_type;
        private List<String> url;

        public String getClicks() {
            return clicks;
        }

        public void setClicks(String clicks) {
            this.clicks = clicks;
        }

        public int getVote_type() {
            return vote_type;
        }

        public void setVote_type(int vote_type) {
            this.vote_type = vote_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_recomm() {
            return is_recomm;
        }

        public void setIs_recomm(int is_recomm) {
            this.is_recomm = is_recomm;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }


        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_display() {
            return is_display;
        }

        public void setIs_display(int is_display) {
            this.is_display = is_display;
        }

        public int getArticle_vote_count() {
            return article_vote_count;
        }

        public void setArticle_vote_count(int article_vote_count) {
            this.article_vote_count = article_vote_count;
        }

        public double getArticle_vote_sum() {
            return article_vote_sum;
        }

        public void setArticle_vote_sum(double article_vote_sum) {
            this.article_vote_sum = article_vote_sum;
        }

        public int getShare_total() {
            return share_total;
        }

        public void setShare_total(int share_total) {
            this.share_total = share_total;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
