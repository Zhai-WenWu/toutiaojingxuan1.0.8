package com.deshang.ttjx.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.widget.CustomerDialog;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.wxapi.SendBean;
import com.deshang.ttjx.wxapi.SendUtil;
import com.deshang.ttjx.wxapi.WXUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by L on 2018/6/20.
 */

public class ShareUtils {

//    private static CustomerDialog dialog;

    private static WXUtils wxUtils;

    // 指定平台分享
    public static void shareSDK(final Context context, String title, String url, String imgUrl, String platform, int type) {
        ReceiveGoldToast.makeToast(context, "分享加载中...").show();
        final OnekeyShare oks = new OnekeyShare();
        // 指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        if (type == 1) {
            oks.setText(SharedPrefHelper.getInstance().getSharehead());
        } else if (type == 2) {
            oks.setText("观看视频有好礼~");
        } else {
            oks.setText(SharedPrefHelper.getInstance().getSubhead());
        }
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        if (imgUrl != null && imgUrl.trim().length() > 0) {
            oks.setImageUrl(imgUrl);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            oks.setImageData(bmp);// 确保SDcard下面存在此张图片
        }
        oks.setTitleUrl(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
//                ReceiveGoldToast.makeToast(context, "分享失败").show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
//                ReceiveGoldToast.makeToast(context, "分享已取消").show();
            }
        });
        // 启动分享
        oks.show(context);
    }

    // 指定平台分享
    public static void showShare(final Context context, String title, String content, String url, String imgUrl, String platform, int type) {
        LogUtils.d("分享进来了");
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (!plat.isClientValid()) {
            // 判断是否存在客户端
            ReceiveGoldToast.makeToast(context, "请安装微信客户端").show();
            return;
        }
        LogUtils.d("分享进来了 有客户端");
        ReceiveGoldToast.makeToast(context, "分享加载中...").show();
        wxUtils = new WXUtils(context, SendUtil.getToWho(context), new SendBean().getKey().get(SendUtil.getToWho(context)));
        if (Wechat.NAME.equals(platform)) {
            LogUtils.d("分享进来了 微信分享");
            wxUtils.share(false, url, title, content, imgUrl, type);
        } else {
            LogUtils.d("分享进来了 朋友圈分享");
            wxUtils.share(true, url, title, content, imgUrl, type);
        }
    }

    // 指定平台分享动态生成的图片
    public static void shareImage(final Context context, View view, String platform) {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (!plat.isClientValid()) {
            // 判断是否存在客户端
            ReceiveGoldToast.makeToast(context, "请安装微信客户端").show();
            return;
        }
        ReceiveGoldToast.makeToast(context, "分享加载中...").show();
        wxUtils = new WXUtils(context, SendUtil.getToWho(context), new SendBean().getKey().get(SendUtil.getToWho(context)));
        if (Wechat.NAME.equals(platform)) {
            wxUtils.shareImageToFriendOrCircle(false, loadBitmapFromView(view));
        } else {
            wxUtils.shareImageToFriendOrCircle(true, loadBitmapFromView(view));
        }
    }

    //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    public static void layoutView(View v, int width, int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public static void viewSaveToImage(Context context, View view, String child) {
        /**
         * View组件显示的内容可以通过cache机制保存为bitmap
         * 我们要获取它的cache先要通过setDrawingCacheEnable方法把cache开启，
         * 然后再调用getDrawingCache方法就可 以获得view的cache图片了
         * 。buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，
         * 若果 cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         * 若果要更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         */
        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);
        //保存在本地 产品还没决定要不要保存在本地
        view.destroyDrawingCache();
        sharePic(context, cachebmp, child);
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        /**
         *  如果不设置canvas画布为白色，则生成透明
         */
        v.draw(c);
        return bmp;
    }

    //保存在本地并一键分享
    private static void sharePic(Context context, Bitmap cachebmp, String child) {
        final File qrImage = new File(Environment.getExternalStorageDirectory(), child + ".jpg");
        if (qrImage.exists()) {
            qrImage.delete();
        }
        try {
            qrImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(qrImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (cachebmp == null) {
            return;
        }
        cachebmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaStore.Images.Media.insertImage(context.getContentResolver(), cachebmp, child, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(qrImage);
        intent.setData(uri);
        context.sendBroadcast(intent);
        ReceiveGoldToast.makeToast(context, "图片保存成功").show();
//        return qrImage.getPath();
    }
}