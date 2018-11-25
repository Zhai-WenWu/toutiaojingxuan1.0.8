package com.deshang.ttjx.framework.utils;

import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab1.bean.ReceiveRedPacketBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 记录用户轨迹
 * Created by L on 2018/8/16.
 */

public class UserPathUtils {

    // 记录用户轨迹
    public static void commitUserPath(int type) {
        LogUtils.d("记录用户轨迹：" + "http://push.deshangkeji.com/api/push/add_push/app/1/userid/" + SharedPrefHelper.getInstance().getUserId() + "/type/" + type);
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url("http://push.deshangkeji.com/api/push/add_push/app/1/userid/" + SharedPrefHelper.getInstance().getUserId() + "/type/" + type)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("记录用户轨迹失败：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                }
            }
        });
    }

    // 记录用户轨迹
    public static void commitUserPathWithNews(int type, String id) {
        LogUtils.d("记录用户轨迹：" + "http://push.deshangkeji.com/api/push/add_push/app/1/userid/" + SharedPrefHelper.getInstance().getUserId() + "/type/" + type + "/aid/" + id);
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .get()
                .url("http://push.deshangkeji.com/api/push/add_push/app/1/userid/" + SharedPrefHelper.getInstance().getUserId() + "/type/" + type + "/aid/" + id)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("记录用户轨迹失败：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                }
            }
        });
    }

}
