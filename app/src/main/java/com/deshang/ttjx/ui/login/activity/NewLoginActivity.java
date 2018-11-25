package com.deshang.ttjx.ui.login.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.network.retrofit.RetrofitUtils;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.login.bean.CodeBean;
import com.deshang.ttjx.ui.login.bean.NewLoginBean;
import com.deshang.ttjx.ui.login.bean.ShuMengBean;
import com.deshang.ttjx.ui.login.presenter.LoginPresenter;
import com.deshang.ttjx.ui.login.view.LoginView;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;

import android.content.DialogInterface.OnClickListener;

import com.deshang.ttjx.ui.mywidget.dialog.WithdrawalDialog;
import com.deshang.ttjx.ui.tab1.bean.GeeTestBean;
import com.geetest.deepknow.DPAPI;
import com.geetest.deepknow.bean.DPJudgementBean;
import com.geetest.sensebot.SEAPI;
import com.geetest.sensebot.listener.BaseSEListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.shuzilm.core.Main;
import mvp.cn.util.ToastUtil;
import mvp.cn.util.VerifyCheck;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * 手机号登陆页面
 * Created by L on 2018/6/6.
 */

public class NewLoginActivity extends MvpSimpleActivity<LoginView, LoginPresenter> implements LoginView {

    // 极验id
    public static final String ID = "c4afb15be0aeef630e188f7de9bd047e";
    private String smUrl = "https://ddi.shuzilm.cn/q?protocol=2&pkg=com.deshang.ttjx&did=";
    //设备号
    private String deviceid;
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

    private int type;
    private String id, time;
    private CountDownTimer timer; // 获取验证码倒计时
    private int isSimulator = 0;

    private String token;

    private SEAPI seapi;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_new_login);
    }

    @Override
    public void initView() {
        titleBar.setTitle("登陆");
        titleBar.setBack(true);

        seapi = new SEAPI(this);
        DPAPI.getInstance(getApplicationContext()).ignoreDPView(finish, "NewLoginActivity");

        type = getIntent().getIntExtra("Type", 0);
        id = getIntent().getStringExtra("ID");
        time = getIntent().getStringExtra("Time");

        timer = new CountDownTimer(60000, 1000) {
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

        handler.sendEmptyMessageAtTime(1, 300);
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

        queryID(1);
    }

    private String queryID;

    private void queryID(final int type) {
        new Thread() {
            @Override
            public void run() {
                queryID = Main.getQueryID(NewLoginActivity.this, Constants.CHANNEL_TYPE, Constants.CHANNEL_TYPE);
                LogUtils.d("查询数盟ID：" + queryID);
                if (type == 1 && queryID != null) {
                    querySmData(smUrl + queryID);
                } else if (queryID != null) {
                    // 去登录
                    getPresenter().login(editPhone.getText().toString().trim(), editCode.getText().toString().trim(), type, id, time, queryID);
                }
            }
        }.start();
    }


    // 查询数盟数据
    private void querySmData(String url) {
        LogUtils.d("查询数盟链接：" + url);
        if (url == null || url.trim().length() == 0)
            return;

        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("查询数盟返回：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    LogUtils.d("查询数盟返回数据：" + str);
                    Gson gson = new Gson();
                    final ShuMengBean bean = gson.fromJson(str, ShuMengBean.class);
                    if (bean.getErr() == 0) {
                        if (bean.getDevice_type() == 1) {
                            isSimulator = 1;
                        }
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtils.d("what:" + msg.what);
            if (msg.what == 1) {
                ProjectUtils.showSoftInputFromWindow(NewLoginActivity.this, editPhone);
            } else {
                ProjectUtils.closeSoftInputFromWindow(NewLoginActivity.this, editPhone);
            }
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
                if (isSimulator == 1) {
                    ReceiveGoldToast.makeToast(NewLoginActivity.this, "您的登录渠道异常，请选择正规渠道").show();
                    break;
                }
                /*if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    showToast("手机号码格式不正确");
                    break;
                }*/
                if (editCode.getText().toString().trim().length() == 0) {
                    showToast("验证码格式不正确");
                    break;
                }
                seapi.onVerify(new DPJudgementBean(ID, 1, new HashMap<String, Object>()), new BaseSEListener() {
                    /**
                     * SDK内部show loading dialog
                     */
                    @Override
                    public void onShowDialog() {
                    }

                    @Override
                    public void onError(String errorCode, String error) {
                        showToast(errorCode + ":" + error);
                    }

                    /**
                     * 验证码Dialog关闭
                     * 1：webview的叉按钮关闭
                     * 2：点击屏幕外关闭
                     * 3：点击回退键关闭
                     *
                     * @param num
                     */
                    @Override
                    public void onCloseDialog(int num) {
                    }

                    /**
                     * show 验证码webview
                     */
                    @Override
                    public void onDialogReady() {
                    }

                    /**
                     * 验证成功
                     * @param challenge
                     */
                    @Override
                    public void onResult(String challenge) {
                        getPresenter().commitGeeTest(editPhone.getText().toString(), challenge);
                    }
                });
                getPresenter().login(editPhone.getText().toString().trim(), editCode.getText().toString().trim(), type, id, time, queryID);
                break;

            case R.id.get_code:
                if (!VerifyCheck.isMobilePhoneVerify(editPhone.getText().toString())) {
                    showToast("手机号码格式不正确");
                    break;
                }
                if (isSimulator == 1) {
//                    ReceiveGoldToast.makeToast(NewLoginActivity.this, "您的登录渠道异常，请选择正规渠道").show();
                    getPresenter().commitSimulatorPhone(editPhone.getText().toString());
                    break;
                }
                getPresenter().getCode(editPhone.getText().toString());
                break;
        }
    }

    @Override
    public void onDestroy() {
        handler.sendEmptyMessageAtTime(0, 300);
        super.onDestroy();
    }

    @Override
    public void sendMessageSucc(CodeBean bean) {
        if (bean.errcode == 0) {
            showToast("发送成功");
            timer.start();
        } else {
            showToast(bean.errinf);
        }
    }

    private NewLoginBean loginBean;

    @Override
    public void loginSucc(NewLoginBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;
            loginBean = bean;
            if (bean.getReturn().getNew_wx() == 0) {
                SharedPrefHelper.getInstance().setUserId(String.valueOf(loginBean.getReturn().getUserid()));
                token = loginBean.getReturn().getToken();
                AlertDialog.Builder b = new AlertDialog.Builder(NewLoginActivity.this);
                b.setMessage("请先绑定微信后登录");
                b.setNegativeButton("去绑定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getWeChatData();
                    }
                });
                b.setCancelable(false);
                b.create();//创建
                b.show();
            } else {
                Constants.IS_LOGIN = 2;
                Constants.TIME_FIVE = SharedPrefHelper.getInstance().getTime();
                setResult(RESULT_OK);
                SharedPrefHelper.getInstance().setUserId(String.valueOf(bean.getReturn().getUserid()));
                SharedPrefHelper.getInstance().setPhoneNumber(bean.getReturn().getMobile());
                SharedPrefHelper.getInstance().setToken(bean.getReturn().getToken());
                SharedPrefHelper.getInstance().setNewUser(bean.getReturn().getNew_type() == 1);
                Constants.turn_to_other_tab = 3;
                finish();
            }
            commitAndroid();
        } else {
            showToast(bean.getErrinf());
        }
    }

    // 获取微信信息

    private void getWeChatData() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (plat.isClientValid()) {
            // 判断是否存在客户端
            showProgressDialog();
        } else {
            ReceiveGoldToast.makeToast(this, "请安装微信客户端").show();
            return;
        }
        if (plat.isAuthValid()) {
            // 已授权  移除授权信息
            plat.removeAccount(true);
//            dismissProgressDialog();
//            PlatformDb db = plat.getDb();
//            commitAuthorization(db.get("unionid"), db.get("openid"), db.get("icon"), db.get("nickname"));
        }
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                dismissProgressDialog();
                PlatformDb db = platform.getDb();
                //LogUtils.d("unionid:" + db.get("unionid") + "  openid:" + db.get("openid") + " icon" + db.get("icon") + " nickname:" + db.get("nickname"));
                commitAuthorization(db.get("unionid"), db.get("openid"), db.get("icon"), db.get("nickname"));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                dismissProgressDialog();
                LogUtils.d("onError" + throwable.toString());
                ReceiveGoldToast.makeToast(NewLoginActivity.this, "拉取授权失败，请重试").show();
                // 清除授权缓存数据
                platform.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                dismissProgressDialog();
                ReceiveGoldToast.makeToast(NewLoginActivity.this, "取消授权").show();
                // 清除授权缓存数据
                platform.removeAccount(true);
            }
        });

        plat.showUser(null);
    }

    /**
     * 提交授权信息
     */
    public void commitAuthorization(String uuid, String open_id, final String img, final String name) {
        Observable request = RetrofitUtils.getInstance().commitAuthorization(uuid, open_id, img, name, token);
        Subscriber<BaseBean> upDataSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                showToast("提交授权信息异常：" + e.toString());
            }

            @Override
            public void onNext(final BaseBean bean) {
                if (bean.errcode == 0) {
                    Constants.IS_LOGIN = 2;
                    Constants.TIME_FIVE = SharedPrefHelper.getInstance().getTime();
                    setResult(RESULT_OK);
                    if (loginBean == null) {
                        return;
                    }
                    SharedPrefHelper.getInstance().setUserId(String.valueOf(loginBean.getReturn().getUserid()));
                    SharedPrefHelper.getInstance().setPhoneNumber(loginBean.getReturn().getMobile());
                    SharedPrefHelper.getInstance().setToken(loginBean.getReturn().getToken());
                    SharedPrefHelper.getInstance().setNewUser(loginBean.getReturn().getNew_type() == 1);
                    SharedPrefHelper.getInstance().setUserName(name);
                    SharedPrefHelper.getInstance().setAvatar(img);
                    Constants.turn_to_other_tab = 3;
                    finish();
                } else {
                    showToast(bean.errinf);
                }
            }
        };

        request.subscribe(upDataSubscriber);
    }

    @Override
    public void commitSimulator(BaseBean bean) {
    }

    @Override
    public void commitSimulatorPhone(BaseBean bean) {

    }

    @Override
    public void commitAuthorization(BaseBean bean) {

    }

    @Override
    public void commitGeeTest(GeeTestBean bean) {
        if (bean != null && bean.getRisk_code() != null && bean.getRisk_code().size() > 0) {
            if (bean.getRisk_level() == 3 || bean.getRisk_level() == 7 || bean.getRisk_level() == 9){
                getPresenter().commitGeeTestLevel(editPhone.getText().toString(), bean.getRisk_code().get(0));
            }
        }
    }

    @Override
    public void commitGeeTestLevel(BaseBean bean) {

    }

    /**
     * 提交登录系统型号 Android/iOS
     */
    public void commitAndroid() {
        Observable request = RetrofitUtils.getInstance().commitAndroid();
        Subscriber<BaseBean> subscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean bean) {

            }
        };
        request.subscribe(subscriber);
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
}
