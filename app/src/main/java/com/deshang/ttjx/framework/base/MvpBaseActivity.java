package com.deshang.ttjx.framework.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;

import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.widget.CustomerDialog;
import com.deshang.ttjx.ui.login.activity.LoginActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.application.SoftApplication;

import mvp.cn.common.MvpView;
import mvp.cn.rx.MvpModel;
import mvp.cn.rx.MvpRxBaseActivity;
import mvp.cn.rx.MvpRxBasePresenter;
import mvp.cn.util.LogUtil;


public abstract class MvpBaseActivity<M extends MvpModel, V extends MvpView, P extends MvpRxBasePresenter<V>> extends MvpRxBaseActivity<V,P> {
    protected SoftApplication softApplication;
    public boolean isAllowFullScreen;// 是否允许全屏
    public boolean isAllowOneScreen = true;// 是否允许一體化
    public boolean hasMenu;// 是否有菜单显示
    private CustomerDialog progressDialog;
    protected Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();
        softApplication = (SoftApplication) getApplicationContext();
        softApplication.unDestroyActivityList.add(this);
//        NetChangeManager.newInstance(softApplication).addMinitor(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }
        if (isAllowOneScreen) {
            setTranslucentStatus(R.color.title_color);
        } else {
            setTranslucentStatus(R.color.transparent);
        }
        setContentLayout();
        ButterKnife.bind(this);
        initView();

    }

    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    public Activity getActivity() {
        return this;
    }
    static long timer;
    protected void filterPin() {
        interceptTime = 10000;
        timer = System.currentTimeMillis();
    }

    protected void resetFilterPin() {
        interceptTime = 1000;
    }

    static int interceptTime = 1000;

    /**
     * 得到屏幕宽度
     *
     * @return 宽度
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 得到屏幕高度
     *
     * @return 高度
     */
    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
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

    public static void setStatusBarTextColor(Activity context, int type) {
        Window window = context.getWindow();
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (type == 0) {
                extraFlagField.invoke(window, tranceFlag, tranceFlag);// 只需要状态栏透明
            } else if (type == 1) {
                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);// 状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);// 清除黑色字体
            }
        } catch (Exception e) {

        }
    }

    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToastLong(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示Toast
     * <p>
     * 显示的内容
     */
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     * <p>
     * 显示的内容
     */
    public void showToastLong(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * onClick方法的封装，在此方法中处理点击
     * <p>
     * 被点击的View对象
     */
    // abstract public void onClickEvent(View view);
    public String getResStrById(int resId) {
        return getResources().getString(resId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SoftApplication.unDestroyActivityList.remove(this);
//        NetChangeManager.newInstance(softApplication).removeMinitor(this);
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(MvpBaseActivity.this, R.style.dialog_style);
        progressDialog.setMessage("加载中...");
        try {
            progressDialog.show();
        } catch (BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(MvpBaseActivity.this, R.style.dialog_style);
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean isLoginAndAuthOk() {
//        UserInfo userInfo = softApplication.getUserInfo();
//        int authStatus = softApplication.getAuthStatus();
//        if (userInfo == null) {
//            UIManager.turnToAct(this, LoginActivity.class);
//            return false;
//        }
//
//        if (authStatus != 7) {
//            Bundle b = new Bundle();
//            b.putInt("authStatus", authStatus);
//            if (authStatus == 1) {
//                UIManager.turnToAct(this, AuthActivity.class, b);
//            } else if (authStatus == 2 || authStatus == 3 || authStatus == 4 || authStatus == 5 || authStatus == 6) {
//            }
//            return false;
//        }
        return true;
    }

    /**
     * 显示栈中的activity, 并干掉它上边的
     */
    public boolean finishActivityAndAboveIt(String activityName) {

        synchronized (SoftApplication.class) {
            if (activityName == null) {
                return false;
            }
            boolean isExist = false;
            for (Activity act : SoftApplication.unDestroyActivityList) {
                if (act.getClass().getName().equals(activityName)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                LogUtil.log("栈中没有这个Activiy:" + activityName);
                return false;
            }
            boolean isOk = false;
            while (!isOk) {
                String prepareFinishActName = SoftApplication.unDestroyActivityList.get(SoftApplication.unDestroyActivityList.size() - 1).getClass().getName();
                SoftApplication.unDestroyActivityList.remove(SoftApplication.unDestroyActivityList.size() - 1).finish();
                LogUtil.log("栈中activity数量:" + SoftApplication.unDestroyActivityList.size());
                if (activityName.equals(prepareFinishActName)) {
                    isOk = true;
                }
            }
            return true;
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
