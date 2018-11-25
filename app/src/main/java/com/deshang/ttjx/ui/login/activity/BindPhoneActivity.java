package com.deshang.ttjx.ui.login.activity;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.presenter.BindPhonePresenter;
import com.deshang.ttjx.ui.login.view.BindPhoneView;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cn.util.VerifyCheck;

/**
 * 绑定手机号页面
 * Created by L on 2018/6/6.
 */

public class BindPhoneActivity extends MvpSimpleActivity<BindPhoneView, BindPhonePresenter> implements BindPhoneView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.clear_phone)
    ImageView clearPhone;
    @BindView(R.id.clear_code)
    ImageView clearCode;
    @BindView(R.id.finish)
    TextView finish;

    private CountDownTimer time; // 获取验证码倒计时

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_bind_phone);
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(300));
            getWindow().setExitTransition(new Slide().setDuration(300));
        }
        titleBar.setTitle("绑定手机号");
        titleBar.setBack(true);

        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCode.setClickable(false);//防止重复点击
                getCode.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                getCode.setText("重新发送");
                getCode.setClickable(true);
            }
        };
        handler.sendEmptyMessageDelayed(0, 300);
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

        editPhone.setText(SharedPrefHelper.getInstance().getPhoneNumber());
        editPhone.setSelection(SharedPrefHelper.getInstance().getPhoneNumber().length());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProjectUtils.showSoftInputFromWindow(BindPhoneActivity.this, editPhone);
        }
    };

    @OnClick({R.id.clear_phone, R.id.clear_code, R.id.finish, R.id.get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_phone:
                editPhone.setText("");
                break;

            case R.id.clear_code:
                editCode.setText("");
                break;

            case R.id.finish:
                if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    showToast("手机号码格式不正确");
                    break;
                }
                if (editCode.getText().toString().trim().length() == 0) {
                    showToast("验证码格式不正确");
                    break;
                }
                getPresenter().bindPhone(editPhone.getText().toString().trim(), editCode.getText().toString().trim());
                /*UIManager.turnToAct(this, MainActivity.class);
                finish();*/
                break;

            case R.id.get_code:
                if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    showToast("手机号码格式不正确");
                    break;
                }
                getPresenter().getCode(editPhone.getText().toString());
                break;
        }
    }

    @Override
    public void onDestroy() {
        ProjectUtils.closeSoftInputFromWindow(BindPhoneActivity.this, editPhone);
        super.onDestroy();
    }

    @Override
    public void sendMessageSucc(CodeBean bean) {
        if (bean.errcode == 0) {
            showToast("发送成功");
//            SharedPrefHelper.getInstance().setPhoneNumber(editPhone.getText().toString());
            time.start();
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public void loginSucc(BaseBean bean) {
        if (bean.errcode == 0) {
            showToast("绑定成功");
            SharedPrefHelper.getInstance().setPhoneNumber(editPhone.getText().toString().trim());
            finish();
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public BindPhonePresenter createPresenter() {
        return new BindPhonePresenter();
    }
}
