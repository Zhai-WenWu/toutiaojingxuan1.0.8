package com.deshang.ttjx.ui.tab4.bean;

import com.deshang.ttjx.framework.base.BaseResponse;

/**
 * Created by L on 2018/6/13.
 */

public class WithdrawalBean extends BaseResponse {

    private int dollarNum;
    private int content;
    private boolean isSelector;

    public WithdrawalBean(int title, int content, boolean isSelector) {
        this.dollarNum = title;
        this.content = content;
        this.isSelector = isSelector;
    }

    public int getDollarNum() {
        return dollarNum;
    }

    public void setDollarNum(int dollarNum) {
        this.dollarNum = dollarNum;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public boolean isSelector() {
        return isSelector;
    }

    public void setSelector(boolean selector) {
        isSelector = selector;
    }
}
