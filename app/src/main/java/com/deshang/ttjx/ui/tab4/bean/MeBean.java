package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/6/13.
 */

public class MeBean extends BaseResponse {

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
         * nickname : 美牛-苏瑜可
         * thumb : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK4tsu9z0efKIArH0PiaV5b2ub7iaJMTDsVFw2icVoYDwicetSUhZv2NxH7N5X2QkYL5c2dQYYn5jrNJw/132
         * total : 18.6492
         * bonus : 17.4455
         * receive_bonus : 0.0000
         * change : 0.12
         * gold_price : 0.05
         * my_vote : 查看投票文章的收益
         * problem : 头条精选怎么玩？
         * my_call : 有问题找客服
         * my_group : 跟老玩家学赚钱
         * white : 解读完整生态
         */

        private String nickname;
        private String thumb;
        private String total;
        private String bonus;
        private String receive_bonus;
        private String change;
        private String gold_price;
        private String my_vote;
        private String problem;
        private String my_call;
        private String my_group;
        private String white;

        public String getApprentice() {
            return apprentice;
        }

        public void setApprentice(String apprentice) {
            this.apprentice = apprentice;
        }

        private String apprentice;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getReceive_bonus() {
            return receive_bonus;
        }

        public void setReceive_bonus(String receive_bonus) {
            this.receive_bonus = receive_bonus;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getGold_price() {
            return gold_price;
        }

        public void setGold_price(String gold_price) {
            this.gold_price = gold_price;
        }

        public String getMy_vote() {
            return my_vote;
        }

        public void setMy_vote(String my_vote) {
            this.my_vote = my_vote;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getMy_call() {
            return my_call;
        }

        public void setMy_call(String my_call) {
            this.my_call = my_call;
        }

        public String getMy_group() {
            return my_group;
        }

        public void setMy_group(String my_group) {
            this.my_group = my_group;
        }

        public String getWhite() {
            return white;
        }

        public void setWhite(String white) {
            this.white = white;
        }
    }
}
