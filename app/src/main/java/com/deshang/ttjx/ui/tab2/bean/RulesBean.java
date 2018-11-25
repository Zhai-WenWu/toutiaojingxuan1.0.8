package com.deshang.ttjx.ui.tab2.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/10/10.
 */

public class RulesBean extends BaseResponse {

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
         * yesterday_mining : 121
         * yesterday_build : 223
         * yesterday_bonus : 411
         * frozen : 332
         * mining : 311
         * build : 232
         * ip_invest : 443
         * team : 233
         * bonus : 432
         * user_total : 45
         * article_total : 113752
         */

        private String yesterday_mining;
        private String yesterday_build;
        private String yesterday_bonus;
        private String frozen;
        private String mining;
        private String build;
        private String ip_invest;
        private String team;
        private String bonus;
        private int user_total;
        private int article_total;

        public String getYesterday_mining() {
            return yesterday_mining;
        }

        public void setYesterday_mining(String yesterday_mining) {
            this.yesterday_mining = yesterday_mining;
        }

        public String getYesterday_build() {
            return yesterday_build;
        }

        public void setYesterday_build(String yesterday_build) {
            this.yesterday_build = yesterday_build;
        }

        public String getYesterday_bonus() {
            return yesterday_bonus;
        }

        public void setYesterday_bonus(String yesterday_bonus) {
            this.yesterday_bonus = yesterday_bonus;
        }

        public String getFrozen() {
            return frozen;
        }

        public void setFrozen(String frozen) {
            this.frozen = frozen;
        }

        public String getMining() {
            return mining;
        }

        public void setMining(String mining) {
            this.mining = mining;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getIp_invest() {
            return ip_invest;
        }

        public void setIp_invest(String ip_invest) {
            this.ip_invest = ip_invest;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public int getUser_total() {
            return user_total;
        }

        public void setUser_total(int user_total) {
            this.user_total = user_total;
        }

        public int getArticle_total() {
            return article_total;
        }

        public void setArticle_total(int article_total) {
            this.article_total = article_total;
        }
    }
}
