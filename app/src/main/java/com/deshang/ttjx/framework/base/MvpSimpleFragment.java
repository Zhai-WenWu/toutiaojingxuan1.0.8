package com.deshang.ttjx.framework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.widget.CustomerDialog;
import com.deshang.ttjx.ui.login.activity.LoginActivity;

import butterknife.ButterKnife;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;

import mvp.cn.common.MvpView;
import mvp.cn.rx.MvpRxBaseFragment;
import mvp.cn.rx.MvpRxSimplePresenter;
import mvp.cn.util.LogUtil;

/**
 * Created by hh on 2016/5/18.
 */
public abstract class MvpSimpleFragment<V extends MvpView, P extends MvpRxSimplePresenter<V>>
        extends MvpRxBaseFragment<V, P> {

    protected SoftApplication softApplication;
    private CustomerDialog progressDialog;
    private View inflate;
    private int contentViewRes = -1;
    private boolean isReSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        softApplication = SoftApplication.softApplication;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!getReSetting()) {
            if (inflate == null) {
                LogUtil.log(getClass().getName() + "初始化");
                setContentLayout(savedInstanceState);
                if (contentViewRes == -1) {
                    LogUtil.log("未设置布局");
                    return null;
                }
                inflate = inflater.inflate(contentViewRes, null);
                ButterKnife.bind(this, inflate);
                if (inflate != null) {
                    LogUtil.log(getClass().getName() + "初始化1");
                    initView(inflate);
                }
            } else {
                LogUtil.log(getClass().getName() + "再次加载,无需初始化");
            }
        } else {
            LogUtil.log(getClass().getName() + "初始化");
            setContentLayout(savedInstanceState);
            if (contentViewRes == -1) {
                LogUtil.log("未设置布局");
                return null;
            }
            inflate = inflater.inflate(contentViewRes, null);
            ButterKnife.bind(this, inflate);
            if (inflate != null) {
                LogUtil.log(getClass().getName() + "初始化1");
                initView(inflate);
            }
        }
        return inflate;
    }

    public void setReSetting(boolean isReSet) {
        this.isReSet = isReSet;
    }

    public boolean getReSetting() {
        return isReSet;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log(getClass().getName() + "[onDestroy]");
//        NetChangeManager.newInstance(softApplication).removeMinitor(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log(getClass().getName() + "[onResume]");
//        NetChangeManager.newInstance(softApplication).removeMinitor(this);
    }

    public abstract void setContentLayout(Bundle savedInstanceState);

    public abstract void initView(View v);

    public void setContentView(int resId) {
        this.contentViewRes = resId;
    }

    @Override
    public void showToast(String info) {
        ReceiveGoldToast.makeToast(getActivity(), info, true).show();
    }

    @Override
    public void showToastLong(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(getActivity(), R.style.dialog_style);
        progressDialog.setMessage("加载中...");
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(getActivity(), R.style.dialog_style);
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * @return
     */
    public boolean isLogin() {
        if (softApplication.isLogin()) {
            return true;
        }
        UIManager.turnToAct(getActivity(), LoginActivity.class);
        return false;
    }
}
