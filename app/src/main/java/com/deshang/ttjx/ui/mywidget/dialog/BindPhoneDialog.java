package com.deshang.ttjx.ui.mywidget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.widget.CustomerDialog;
import com.deshang.ttjx.ui.login.activity.BindPhoneActivity;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab1.adapter.RecommendNewsAdapter;
import com.deshang.ttjx.ui.tab1.bean.RecommendBean;

import java.util.ArrayList;
import java.util.List;

import mvp.cn.util.VerifyCheck;
import rx.Observable;
import rx.Subscriber;


/**
 * 激活Dialog
 * Created by L on 2018年6月19日15:26:03
 */

public class BindPhoneDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private View view;

    private ImageView close;
    private TextView finish;
    private EditText editPhone, editCode;
    private TextView tvCode;
    private TextView tvJump;
    private ImageView clearPhone, clearCode;

    private CustomerDialog progressDialog;

    private CountDownTimer time; // 获取验证码倒计时

    public BindPhoneDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(context, R.layout.dialog_bind_phone, null);
        setContentView(view);

        initView(view);
    }

    /***
     * 初始化组件
     * @param view
     */
    private void initView(View view) {
        progressDialog = new CustomerDialog(context, R.style.dialog_style);
        progressDialog.setMessage("加载中...");
        // 关闭
        close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(this);
        // 跳过
        tvJump = (TextView) view.findViewById(R.id.tv_jump);
        tvJump.setOnClickListener(this);
        // 获取验证码
        tvCode = (TextView) view.findViewById(R.id.get_code);
        tvCode.setOnClickListener(this);
        // 完成
        finish = (TextView) view.findViewById(R.id.finish);
        finish.setOnClickListener(this);
        // 清除手机号
        clearPhone = (ImageView) view.findViewById(R.id.clear_phone);
        clearPhone.setOnClickListener(this);
        // 清除验证码
        clearCode = (ImageView) view.findViewById(R.id.clear_code);
        clearCode.setOnClickListener(this);

        editPhone = (EditText) view.findViewById(R.id.edit_phone);
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clearPhone.setVisibility(View.VISIBLE);
                } else {
                    clearPhone.setVisibility(View.INVISIBLE);
                }
            }
        });

        editCode = (EditText) view.findViewById(R.id.edit_code);
        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clearCode.setVisibility(View.VISIBLE);
                } else {
                    clearCode.setVisibility(View.INVISIBLE);
                }
            }
        });

        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setClickable(false);//防止重复点击
                tvCode.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                tvCode.setText("重新发送");
                tvCode.setClickable(true);
            }
        };
        handler.sendEmptyMessageDelayed(0, 300);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProjectUtils.showSoftInputFromWindow(context, editPhone);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;

            case R.id.tv_jump:
                dismiss();
                break;

            case R.id.get_code:
                //  判断手机号格式
                if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    ReceiveGoldToast.makeToast(context, "手机号码格式不正确").show();
                    break;
                }
                getCode();
                break;

            case R.id.finish:
                //  判断手机号验证码是否空
                if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    ReceiveGoldToast.makeToast(context, "手机号码格式不正确").show();
                    break;
                }
                if (editCode.getText().toString().trim().length() == 0) {
                    ReceiveGoldToast.makeToast(context, "验证码格式不正确").show();
                    break;
                }
                bindPhone();
                break;

            case R.id.clear_phone:
                editPhone.setText("");
                break;

            case R.id.clear_code:
                editCode.setText("");
                break;
        }
    }

    // 获取验证码
    private void getCode() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        Observable observable = RetrofitUtils.getInstance().getCode(editPhone.getText().toString().trim());
        Subscriber<CodeBean> subscriber = new Subscriber<CodeBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                LogUtils.d("获取验证码异常" + e.toString());
            }

            @Override
            public void onNext(CodeBean bean) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (bean.errcode == 0) {
                    ReceiveGoldToast.makeToast(context, "短信发送成功").show();
                    time.start();
                } else {
                    ReceiveGoldToast.makeToast(context, bean.errinf).show();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    // 绑定完成
    private void bindPhone() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        Observable observable = RetrofitUtils.getInstance().bindPhone(editPhone.getText().toString().trim(), editCode.getText().toString().trim());
        Subscriber<BaseBean> subscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                LogUtils.d("获取验证码异常" + e.toString());
            }

            @Override
            public void onNext(BaseBean bean) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (bean.errcode == 0) {
                    ReceiveGoldToast.makeToast(context, "绑定成功").show();
                    dismiss();
                } else {
                    ReceiveGoldToast.makeToast(context, bean.errinf).show();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public void dismiss() {
        time.cancel();
        super.dismiss();
    }
}
