package com.deshang.ttjx.framework.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.deshang.ttjx.framework.contant.Constants;

/**
 * Created by L on 2018/5/30.
 */

public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    private int refCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        refCount++;
        Constants.IS_BACKGROUND = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        refCount--;
        Constants.IS_BACKGROUND = refCount == 0;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}