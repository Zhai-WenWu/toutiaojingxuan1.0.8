package com.deshang.ttjx.ui.home.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.SystemBarTintManager;
import com.deshang.ttjx.framework.widget.CustomerDialog;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;

import butterknife.ButterKnife;

/**
 * 没有网络请求的基类
 * Created by L on 2017/12/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public boolean isAllowFullScreen;// 是否允许全屏
    public boolean isAllowOneScreen = true;// 是否允许一體化

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 锁定竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }
        if (isAllowOneScreen) {
            setTranslucentStatus(R.color.gray_light);
        } else {
            setTranslucentStatus(R.color.transparent);
        }
        setContentLayout();
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 是否全屏和显示标题，true为全屏和无标题，false为无标题，请在setContentView()方法前调用
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorResId);// 状态栏无背景
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        ReceiveGoldToast.makeToast(this, info).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToastLong(String info) {
        ReceiveGoldToast.makeToast(this, info).show();
    }

    public CustomerDialog dialog;

    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new CustomerDialog(this, R.style.dialog_style);
        }
        dialog.show();
    }

    public void closeProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
