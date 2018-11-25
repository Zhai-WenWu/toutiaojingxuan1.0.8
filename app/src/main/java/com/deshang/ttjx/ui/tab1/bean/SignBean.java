package com.deshang.ttjx.ui.tab1.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/8/17.
 */

public class SignBean extends BaseResponse {

    private String errinf;
    private int errcode;
    private int sign_day;
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

    public int getSign_day() {
        return sign_day;
    }

    public void setSign_day(int sign_day) {
        this.sign_day = sign_day;
    }

    public ReturnBean getReturn() {
        return Return;
    }

    public void setReturn(ReturnBean Return) {
        this.Return = Return;
    }

    public static class ReturnBean {

        private int sign_one_day;
        private int sign_one;
        private int sign_two_day;
        private int sign_two;
        private int sign_three_day;
        private int sign_three;
        private int sign_four_day;
        private int sign_four;
        private int sign_five_day;
        private int sign_five;
        private int sign_six_day;
        private int sign_six;
        private int sign_seven_day;
        private int sign_seven;

        public int getSign_one_day() {
            return sign_one_day;
        }

        public void setSign_one_day(int sign_one_day) {
            this.sign_one_day = sign_one_day;
        }

        public int getSign_one() {
            return sign_one;
        }

        public void setSign_one(int sign_one) {
            this.sign_one = sign_one;
        }

        public int getSign_two_day() {
            return sign_two_day;
        }

        public void setSign_two_day(int sign_two_day) {
            this.sign_two_day = sign_two_day;
        }

        public int getSign_two() {
            return sign_two;
        }

        public void setSign_two(int sign_two) {
            this.sign_two = sign_two;
        }

        public int getSign_three_day() {
            return sign_three_day;
        }

        public void setSign_three_day(int sign_three_day) {
            this.sign_three_day = sign_three_day;
        }

        public int getSign_three() {
            return sign_three;
        }

        public void setSign_three(int sign_three) {
            this.sign_three = sign_three;
        }

        public int getSign_four_day() {
            return sign_four_day;
        }

        public void setSign_four_day(int sign_four_day) {
            this.sign_four_day = sign_four_day;
        }

        public int getSign_four() {
            return sign_four;
        }

        public void setSign_four(int sign_four) {
            this.sign_four = sign_four;
        }

        public int getSign_five_day() {
            return sign_five_day;
        }

        public void setSign_five_day(int sign_five_day) {
            this.sign_five_day = sign_five_day;
        }

        public int getSign_five() {
            return sign_five;
        }

        public void setSign_five(int sign_five) {
            this.sign_five = sign_five;
        }

        public int getSign_six_day() {
            return sign_six_day;
        }

        public void setSign_six_day(int sign_six_day) {
            this.sign_six_day = sign_six_day;
        }

        public int getSign_six() {
            return sign_six;
        }

        public void setSign_six(int sign_six) {
            this.sign_six = sign_six;
        }

        public int getSign_seven_day() {
            return sign_seven_day;
        }

        public void setSign_seven_day(int sign_seven_day) {
            this.sign_seven_day = sign_seven_day;
        }

        public int getSign_seven() {
            return sign_seven;
        }

        public void setSign_seven(int sign_seven) {
            this.sign_seven = sign_seven;
        }
    }
}
