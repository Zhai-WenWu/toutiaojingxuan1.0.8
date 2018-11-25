package com.deshang.ttjx.ui.mywidget;

import android.os.CountDownTimer;

/**
 * Created by L on 2017/12/15.
 */

public abstract class TimeCount extends CountDownTimer {

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {// 计时完毕
        finish();
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        tick(millisUntilFinished);
    }

    public abstract void finish();

    public abstract void tick(long millisUntilFinished);
}
