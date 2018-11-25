package com.deshang.ttjx.ui.mywidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import butterknife.ButterKnife;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.widget.CustomerDialog;

/**
 * 懒加载的Fragment
 * Created by L on 2017/12/11 0011.
 */

public abstract class LazyLoadingFragment extends Fragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private int contentViewRes = -1;
    private View inflate;
    private CustomerDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflate == null) {
            setContentLayout(savedInstanceState);
            if (contentViewRes == -1) {
                return null;
            }
            inflate = inflater.inflate(contentViewRes, null);
            ButterKnife.bind(this, inflate);
            if (inflate != null)
                initView(inflate);
        } else {
        }
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    public void showToast(String content) {
        ReceiveGoldToast.makeToast(getActivity(), content).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            LogUtils.d("setUserVisibleHint : true");
            isUIVisible = true;
            lazyLoad();
        } else {
            LogUtils.d("setUserVisibleHint : false");
            isUIVisible = false;
        }
    }

    public boolean getIsUIVisible() {
        return isUIVisible;
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    protected abstract void loadData();

    protected abstract void setContentLayout(Bundle savedInstanceState);

    protected abstract void initView(View v);

    protected void setContentView(int resId) {
        this.contentViewRes = resId;
    }

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

    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
/*
    @Override
    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
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

    *//**
     * @return
     *//*
    public boolean isLogin() {
       *//* if (softApplication.isLogin()) {
            return true;
        }
        UIManager.turnToAct(getActivity(), LoginActivity.class);*//*
        return false;
    }*/
}
