package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/9/26.
 */

public class VoteBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private ReturnBean Return;

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

    public ReturnBean getReturn() {
        return Return;
    }

    public void setReturn(ReturnBean Return) {
        this.Return = Return;
    }

    public static class ReturnBean {
        /**
         * vote_number_type : 0
         * vote_type : 0
         * total_votes : 3
         * cast_votes : 0
         * read_number : 0
         * read_vote : 3
         * maximum_vote : 30
         * einnahmen_count : 0
         * article_vote_count : 0
         */

        private int vote_number_type;
        private int vote_type;
        private int total_votes;
        private int cast_votes;
        private int read_number;
        private int read_vote;
        private int maximum_vote;
        private int einnahmen_count;
        private int article_vote_count;
        private double article_vote_sum;

        public double getArticle_vote_sum() {
            return article_vote_sum;
        }

        public void setArticle_vote_sum(double article_vote_sum) {
            this.article_vote_sum = article_vote_sum;
        }

        public int getVote_number_type() {
            return vote_number_type;
        }

        public void setVote_number_type(int vote_number_type) {
            this.vote_number_type = vote_number_type;
        }

        public int getVote_type() {
            return vote_type;
        }

        public void setVote_type(int vote_type) {
            this.vote_type = vote_type;
        }

        public int getTotal_votes() {
            return total_votes;
        }

        public void setTotal_votes(int total_votes) {
            this.total_votes = total_votes;
        }

        public int getCast_votes() {
            return cast_votes;
        }

        public void setCast_votes(int cast_votes) {
            this.cast_votes = cast_votes;
        }

        public int getRead_number() {
            return read_number;
        }

        public void setRead_number(int read_number) {
            this.read_number = read_number;
        }

        public int getRead_vote() {
            return read_vote;
        }

        public void setRead_vote(int read_vote) {
            this.read_vote = read_vote;
        }

        public int getMaximum_vote() {
            return maximum_vote;
        }

        public void setMaximum_vote(int maximum_vote) {
            this.maximum_vote = maximum_vote;
        }

        public int getEinnahmen_count() {
            return einnahmen_count;
        }

        public void setEinnahmen_count(int einnahmen_count) {
            this.einnahmen_count = einnahmen_count;
        }

        public int getArticle_vote_count() {
            return article_vote_count;
        }

        public void setArticle_vote_count(int article_vote_count) {
            this.article_vote_count = article_vote_count;
        }
    }
}
