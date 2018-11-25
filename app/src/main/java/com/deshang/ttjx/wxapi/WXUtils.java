package com.deshang.ttjx.wxapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by yshow_mdj on 2016/4/13.
 */
public class WXUtils {
    public static final String APPID = "wxd41e2186c9aae228";
    public static final String APPSECRET = "4b5f26610861e94d232fa67dc9759cd2";
    private IWXAPI iwxapi = null;
    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 1) { // 微信sdk分享
                Bitmap bitmap = (Bitmap) message.obj;
                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                    LogUtils.d("分享进来了 bitmap is null");
                }
                Bundle bundle = message.getData();
                WXWebpageObject webpageObject = new WXWebpageObject();
                webpageObject.webpageUrl = bundle.getString("share_url");
                WXMediaMessage msg = new WXMediaMessage(webpageObject);
                msg.title = bundle.getString("title");
//                msg.description = bundle.getString("title");
                if (bundle.getInt("Type") == 1) {
                    // 新闻分享
//                    msg.description = SharedPrefHelper.getInstance().getSharehead();
                    msg.description = bundle.getString("content");
                } else if (bundle.getInt("Type") == 2) {
                    // 视频分享
                    msg.description = ".";
                } else {
                    // 收徒分享
                    msg.description = SharedPrefHelper.getInstance().getSubhead();
                }
                msg.thumbData = ProjectUtils.bmpToByteArray(bitmap, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = bundle.getBoolean("isTimeline") ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
                LogUtils.d("分享进来了 最后一步Handler");
            } else if (message.what == 2) {  // 系统自带分享到朋友圈
                Bundle bundle = message.getData();
                File file = new File(ProjectUtils.share_img_path);
                if (file.exists()) {

                    Uri uri = Uri.fromFile(file);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        uri = Uri.fromFile(file);
                    } else {
                        //修复微信在7.0崩溃的问题
                        try {
                            uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), file.getAbsolutePath(), "share.png", null));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent();
                    ComponentName comp = new ComponentName(bundle.getString("packageName"), bundle.getString("className"));
                    intent.setComponent(comp);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.putExtra("Kdescription", bundle.getString("title") + bundle.getString("url"));
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    public WXUtils(Context context, String myPck, String myApp) {
        this.mContext = context;
        iwxapi = WXAPIFactory.createWXAPI(new MyContextWrapper(context, myPck), myApp, true);
        iwxapi.registerApp(myApp);
    }

    public WXUtils(Context context) {
        mContext = context;
    }


    public IWXAPI getIwxapi() {
        return iwxapi;
    }

    public void setIwxapi(IWXAPI iwxapi) {
        this.iwxapi = iwxapi;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void share(final boolean isTimeline, final String share_url, final String title, final String share_img) {
        if (null == share_url || null == title || null == share_img) {
            Toast.makeText(mContext, "分享初始化参数失败", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("share_url", share_url);
                bundle.putBoolean("isTimeline", isTimeline);
                bundle.putString("title", title);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.setData(bundle);

                Bitmap bitmap = ProjectUtils.returnBitMap(share_img);
                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_share);
                }

                msg.obj = createBitmapThumbnail(bitmap);
                msg.sendToTarget();
            }
        }).start();

    }

    public void share(ShareParam param, boolean isTimeline, String title) {
        if (param == null || param.isEmpty()) {
            Toast.makeText(mContext, "分享参数初始化失败", Toast.LENGTH_LONG).show();
            return;
        }
        // 如果分享内容为空，将分享内容默认设置为title
        if (param.getDescription() == null || "".equals(param.getDescription())) {
            param.setDescription(title);
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = param.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = param.getDescription();
        Bitmap thumb = createBitmapThumbnail(getShareBitmap(mContext, param.getImageUrl()));
        msg.thumbData = ProjectUtils.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    public void share(final boolean isTimeline, final String share_url, final String title, final String content, final String share_img, final int type) {
        LogUtils.d("分享进来了 开始设置分享参数" + share_img);
        if (null == share_url || null == title) {
            ReceiveGoldToast.makeToast(mContext, "分享初始化参数失败").show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("share_url", share_url);
                bundle.putBoolean("isTimeline", isTimeline);
                bundle.putString("title", title);
                bundle.putInt("Type", type);
                bundle.putString("content", content);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.setData(bundle);
                Bitmap bitmap;
                if (share_img != null && share_img.length() > 0) {
                    bitmap = ProjectUtils.returnBitMap(share_img);
                } else {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                }
                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                }
                msg.obj = createBitmapThumbnail(bitmap);
                msg.sendToTarget();
                LogUtils.d("分享进来了 最后一步");
            }
        }).start();

    }

    private Bitmap getShareBitmap(Context context, String imgurl) {
        String path = null;
        if (imgurl != null && !"".equals(imgurl)) {
            path = DownloadWebImgTask.isExist(context, imgurl);
        }
        if (path == null || "".equals(imgurl)) {
            return BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        } else {
            return BitmapFactory.decodeFile(path);
        }
    }

    // 压缩微信分享图片

    public Bitmap createBitmapThumbnail(Bitmap bitMap) {
        if (bitMap == null) {
            LogUtils.d("分享进来了 bitMap is null");
        } else {
            LogUtils.d("分享进来了 bitMap 不是 null");
        }
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 99;
        int newHeight = 99;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        return newBitMap;
    }

    public void shouquan() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        iwxapi.sendReq(req);
    }

    // 分享图片到微信或朋友圈
    public void shareImageToFriendOrCircle(final boolean isCircle, final Bitmap bitmap) {
        if (bitmap == null) {
            LogUtils.d("bitmap = null");
        }
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
            /*}
        }).start();*/
    }

    /**
     * 分享多张图片和文字至朋友圈
     *
     * @param title
     * @param url       地址
     * @param share_img 图片地址
     */
    /*public void shareImgToWXCircle(final String title, final String url, final String share_img) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ProjectUtils.returnBitMap(share_img);

                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.default_share);
                }

                if (ProjectUtils.savePicture(bitmap)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putString("title", title);
                    bundle.putString("packageName", mContext.getResources().getString(R.string.wx_pck));
                    bundle.putString("className", mContext.getResources().getString(R.string.wxcirclce));
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.setData(bundle);
                    msg.sendToTarget();
                }
            }
        }).start();
    }*/

    /**
     * 原生分享到微信好友
     *
     * @param title
     * @param url
     */
    public void shareToWXFriend(String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        ComponentName comp = new ComponentName(mContext.getResources().getString(R.string.wx_pck), mContext.getResources().getString(R.string.wxfriend));
        intent.setComponent(comp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_TEXT, title + url);
        mContext.startActivity(intent);
    }

}
