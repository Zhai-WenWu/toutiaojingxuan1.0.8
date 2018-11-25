package com.deshang.ttjx.wxapi;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by 13364 on 2017/4/18.
 *
 * 包名转换
 *
 */

public class MyContextWrapper extends ContextWrapper {


    private String pckname;

    public MyContextWrapper(Context context, String pck) {
        super(context);
        this.pckname = pck;
    }

    @Override
    public String getPackageName() {
        return pckname;
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    public String getPckname() {
        return pckname;
    }

    public void setPckname(String packname) {
        this.pckname = packname;
    }

}
