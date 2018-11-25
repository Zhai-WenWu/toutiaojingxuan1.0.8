package com.deshang.ttjx.ui.broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.ui.tab4.activity.NewsDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushMessageReceiver extends BroadcastReceiver {
    //MessageDao messageDao;
    @Override
    public void onReceive(Context context, Intent intent) {
        //initDbHelp();
        try {
            Bundle bundle = intent.getExtras();

            LogUtils.d("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtils.d("[MyReceiver] 接收Registration Id : " + regId);

                // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
                String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);


            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

                LogUtils.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                // 普通消息走这个广播
                LogUtils.d("[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogUtils.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);


                
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                // 用户打开了推送的消息 打开自定义的Activity
                LogUtils.d("[MyReceiver] 用户点击打开了通知");

            }
        } catch (Exception e) {

        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtils.d("This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.d("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    //解析从服务器推送过来的消息
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.d("processCustomMessage : " + message + "  extras : " + extras);
        //解析服务器端推送过来的json
      /*  if(!ExampleUtil.isEmpty(message)){
            msgIntent.putExtra(BaseActivity.KEY_MESSAGE, message);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
*/


        /*if (MainActivity.isForeground) {
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {
                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }*/
    }
   /* private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.mBaseApplication, "recluse-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        messageDao = daoSession.getMessageDao();
    }*/

    public static void showNotifictionIcon(Context context,String title,String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, NewsDetailActivity.class);//将要跳转的界面
        //点击通知栏消息跳转页
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("状态栏显示的文字");
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setWhen(System.currentTimeMillis());//通知栏显示时间)
         builder.setAutoCancel(true);//设置点击通知栏消息后，通知消息自动消失
        builder.setContentIntent(pendingIntent);//关联点击通知栏跳转页面

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


}
