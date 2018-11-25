package com.deshang.ttjx.ui.mywidget.dialog;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.tab1.bean.SignBean;

/**
 * 签到 Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class SignDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int dayNum;
    private SignBean.ReturnBean bean;

    private ImageView bg_one, bg_two, bg_three, bg_four, bg_five, bg_six, bg_seven;
    private ImageView img_one, img_two, img_three, img_four, img_five, img_six, img_seven;
    private TextView tv_one, tv_two, tv_three, tv_four, tv_five, tv_six, tv_seven;
    private TextView tv_one1, tv_two1, tv_three1, tv_four1, tv_five1, tv_six1, tv_seven1;

    public SignDialog(@NonNull Context context, int dayNum, SignBean.ReturnBean bean) {
        super(context, R.style.Dialog);
        this.context = context;
        this.dayNum = dayNum;
        this.bean = bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(context, R.layout.dialog_sign, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        TextView tv_day = (TextView) view.findViewById(R.id.tv_day);
        tv_day.setText(dayNum + "");

        bg_one = (ImageView) view.findViewById(R.id.bg_one);
        img_one = (ImageView) view.findViewById(R.id.img_one);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_one1 = (TextView) view.findViewById(R.id.tv_one1);

        bg_two = (ImageView) view.findViewById(R.id.bg_two);
        img_two = (ImageView) view.findViewById(R.id.img_two);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        tv_two1 = (TextView) view.findViewById(R.id.tv_two1);

        bg_three = (ImageView) view.findViewById(R.id.bg_three);
        img_three = (ImageView) view.findViewById(R.id.img_three);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        tv_three1 = (TextView) view.findViewById(R.id.tv_three1);

        bg_four = (ImageView) view.findViewById(R.id.bg_four);
        img_four = (ImageView) view.findViewById(R.id.img_four);
        tv_four = (TextView) view.findViewById(R.id.tv_four);
        tv_four1 = (TextView) view.findViewById(R.id.tv_four1);

        bg_five = (ImageView) view.findViewById(R.id.bg_five);
        img_five = (ImageView) view.findViewById(R.id.img_five);
        tv_five = (TextView) view.findViewById(R.id.tv_five);
        tv_five1 = (TextView) view.findViewById(R.id.tv_five1);

        bg_six = (ImageView) view.findViewById(R.id.bg_six);
        img_six = (ImageView) view.findViewById(R.id.img_six);
        tv_six = (TextView) view.findViewById(R.id.tv_six);
        tv_six1 = (TextView) view.findViewById(R.id.tv_six1);

        bg_seven = (ImageView) view.findViewById(R.id.bg_seven);
        img_seven = (ImageView) view.findViewById(R.id.img_seven);
        tv_seven = (TextView) view.findViewById(R.id.tv_seven);
        tv_seven1 = (TextView) view.findViewById(R.id.tv_seven1);
        setUIGray();
        setCancelable(false);
    }

    // 设置签到天数
    private void setUIGray() {

        tv_one.setText(String.valueOf(bean.getSign_one()));
        tv_two.setText(String.valueOf(bean.getSign_two()));
        tv_three.setText(String.valueOf(bean.getSign_three()));
        tv_four.setText(String.valueOf(bean.getSign_four()));
        tv_five.setText(String.valueOf(bean.getSign_five()));
        tv_six.setText(String.valueOf(bean.getSign_six()));
        tv_seven.setText(String.valueOf(bean.getSign_seven()));
        switch (dayNum) {
            case 7:
                bg_seven.setVisibility(View.VISIBLE);
                startAnimation(bg_seven);
                break;
            case 6:
                bg_six.setVisibility(View.VISIBLE);
                startAnimation(bg_six);
                break;
            case 5:
                bg_five.setVisibility(View.VISIBLE);
                startAnimation(bg_five);
                break;
            case 4:
                bg_four.setVisibility(View.VISIBLE);
                startAnimation(bg_four);
                break;
            case 3:
                bg_three.setVisibility(View.VISIBLE);
                startAnimation(bg_three);
                break;
            case 2:
                bg_two.setVisibility(View.VISIBLE);
                startAnimation(bg_two);
                break;
            case 1:
                bg_one.setVisibility(View.VISIBLE);
                startAnimation(bg_one);
                break;
        }

        switch (dayNum) {
            case 7:
                img_seven.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_seven.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_seven1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 6:
                img_six.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_six.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_six1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 5:
                img_five.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_five.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_five1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 4:
                img_four.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_four.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_four1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 3:
                img_three.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_three.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_three1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 2:
                img_two.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_two.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_two1.setTextColor(context.getResources().getColor(R.color.orange1));
            case 1:
                img_one.setImageDrawable(context.getResources().getDrawable(R.mipmap.sign_gold_light));
                tv_one.setTextColor(context.getResources().getColor(R.color.orange1));
                tv_one1.setTextColor(context.getResources().getColor(R.color.orange1));
        }
    }

    private void startAnimation(View view) {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1000);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.RESTART);
        view.startAnimation(animation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        UserPathUtils.commitUserPath(23);
    }
}
