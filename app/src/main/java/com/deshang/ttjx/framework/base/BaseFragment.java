package com.deshang.ttjx.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshang.ttjx.framework.application.SoftApplication;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.ui.login.activity.LoginActivity;

import butterknife.ButterKnife;
import mvp.cn.util.LogUtil;

/**
 * Created by hh on 2016/5/18.
 */
public abstract class BaseFragment extends Fragment {

    protected SoftApplication softApplication;

    private View inflate;
    private int contentViewRes = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        softApplication = SoftApplication.softApplication;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflate == null) {
            LogUtil.log(getClass().getName() + "初始化");
            setContentLayout(savedInstanceState);
            if (contentViewRes == -1) {
                LogUtil.log("未设置布局");
                return null;
            }
            inflate = inflater.inflate(contentViewRes, null);
            ButterKnife.bind(this, inflate);
            if (inflate != null)
                initView(inflate);
        } else {
            LogUtil.log(getClass().getName() + "再次加载,无需初始化");
        }

        return inflate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log(getClass().getName() + "[onDestroy]");
//        NetChangeManager.newInstance(softApplication).removeMinitor(this);
    }

    public abstract void setContentLayout(Bundle savedInstanceState);

    public abstract void initView(View v);

    public void setContentView(int resId) {
        this.contentViewRes = resId;
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
