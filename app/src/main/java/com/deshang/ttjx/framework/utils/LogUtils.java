package com.deshang.ttjx.framework.utils;

import android.util.Log;

public class LogUtils {

    private static boolean debug = false;   //设置调试模式
    private static String TAG = "callback";   //设置调试模式

    public static void i(String content) {

        if (debug) {
            Log.e(TAG, content);
        }

    }

    public static void i(String tag, String content) {

        if (debug) {
            Log.e(tag, content);
        }

    }

    public static void d(String content) {

        if (debug) {
            Log.e(TAG, content);
        }

    }

    public static void d(String tag, String content) {

        if (debug) {
            Log.e(tag, content);
        }

    }


    public static void setDebug(boolean bln) {
        debug = bln;
    }

}
