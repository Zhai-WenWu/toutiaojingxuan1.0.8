package com.deshang.ttjx.ui.tab4.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.imageload.GlideLoading;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.framework.widget.CircleImageView;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.mywidget.scroll_text.AutoScrollTextView;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.dialog.QRApprenticeDialog;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;
import com.deshang.ttjx.ui.mywidget.scroll_text.TextBannerView;
import com.deshang.ttjx.ui.tab4.bean.ApprenticeScrollMessageBean;
import com.deshang.ttjx.ui.tab4.persenter.ApprenticePresenter;
import com.deshang.ttjx.ui.tab4.view.ApprenticeView;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by L on 2018/7/6.
 */

public class ApprenticeActivity extends MvpSimpleActivity<ApprenticeView, ApprenticePresenter> implements ApprenticeView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.auto_scroll_tv)
    TextBannerView auto_scroll_tv;
    @BindView(R.id.scroll_view)
    ScrollView scroll_view;
    @BindView(R.id.scroll_view1)
    ScrollView scroll_view1;
    @BindView(R.id.image_qr)
    ImageView imageQr;
    @BindView(R.id.image_qr1)
    ImageView image_qr1;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.header)
    CircleImageView header;

    private ArrayList<String> autoStringList;
//    private View qRView, view1;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_apprentice);
    }

    @Override
    public void initView() {
        UserPathUtils.commitUserPath(31);
        titleBar.setBack(true);
        titleBar.setTitle("收徒任务");
        autoStringList = new ArrayList<>();
        getPresenter().getScrollText();
//        qRView = LayoutInflater.from(ApprenticeActivity.this).inflate(R.layout.apprentice_qr_view, null);
//        ((ImageView) qRView.findViewById(R.id.image_qr))
        GlideLoading.getInstance().loadImageHeader(this, SharedPrefHelper.getInstance().getAvatar(), header);
        text.setText(SharedPrefHelper.getInstance().getUserName() + "在头条精选上");
        imageQr.setImageBitmap(
                EncodingUtils.Create2DCode(
                        SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/2",
                        ProjectUtils.dp2px(getActivity(), 120),
                        ProjectUtils.dp2px(getActivity(), 120), BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher)));

        image_qr1.setImageBitmap(
                EncodingUtils.Create2DCode(
                        SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/2",
                        ProjectUtils.dp2px(getActivity(), 130),
                        ProjectUtils.dp2px(getActivity(), 130), BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_launcher)));
//        view1 = LayoutInflater.from(ApprenticeActivity.this).inflate(R.layout.apprentice_qr1_view, null);
    }

    private RelativeLayout imageView;

    @OnClick({R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                final ShareDialog dialog = new ShareDialog(this, R.style.dialog_style);
                dialog.setClickListener(new ShareDialog.ClickListener() {
                    @Override
                    public void onClickListener(int type) {
//                        getPresenter().apprenticeClickNum(type, 2);
                        switch (type) {
                            case 1:
                                //if ((int) (Math.random() * 2) == 0) {
                                ShareUtils.shareImage(ApprenticeActivity.this, scroll_view, WechatMoments.NAME);
                                /*} else {
                                    ShareUtils.shareImage(ApprenticeActivity.this, scroll_view1, WechatMoments.NAME);
                                }*/
//                                ShareUtils.showShare(ApprenticeActivity.this, Constants.SHARE_APPRENTICE_TITLE, SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/1", null, WechatMoments.NAME, 0);
                                break;
                            case 2:
                                //  if ((int) (Math.random() * 2) == 0) {
                                ShareUtils.shareImage(ApprenticeActivity.this, scroll_view, Wechat.NAME);
                               /* } else {
                                    ShareUtils.shareImage(ApprenticeActivity.this, scroll_view1, Wechat.NAME);
                                }*/
//                                ShareUtils.showShare(ApprenticeActivity.this, Constants.SHARE_APPRENTICE_TITLE, SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/2", null, Wechat.NAME, 0);
                                break;
                            case 3:
                                Uri smsToUri = Uri.parse("smsto:");
                                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                //短信内容
                                intent.putExtra("sms_body", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/3");
                                startActivity(intent);
                                break;
                            case 4:
                                QRApprenticeDialog dialog = new QRApprenticeDialog(getActivity(), 1);
                                dialog.setOnSureClickListener(new QRApprenticeDialog.OnSureClickListener() {
                                    @Override
                                    public void onSureClick(RelativeLayout view) {
                                        // 二维码弹窗确认
                                        // 判断是否已经赋予权限
                                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                            // 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                                                ReceiveGoldToast.makeToast(ApprenticeActivity.this, "授予权限才可使用此功能").show();
                                            } else {
                                                imageView = view;
                                                // 申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                            }
                                        } else {
                                            // 保存二维码图片
                                            ShareUtils.viewSaveToImage(getActivity(), view, "apprentice_image");
                                        }
                                    }
                                });
                                dialog.show();
                                break;
                            case 5:
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/5");
                                ReceiveGoldToast.makeToast(ApprenticeActivity.this, "复制成功").show();
                                break;

                            case 6:
                                ShareUtils.shareSDK(ApprenticeActivity.this, Constants.SHARE_APPRENTICE_TITLE, SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/6", null, QQ.NAME, 0);
                                break;
                        }
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 同意
                    ShareUtils.viewSaveToImage(getActivity(), imageView, "apprentice_image");
                } else {
                    // 拒绝
                    ReceiveGoldToast.makeToast(ApprenticeActivity.this, "授予权限才可使用此功能").show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        auto_scroll_tv.startViewAnimator();
    }

    @Override
    public void onPause() {
        super.onPause();
        auto_scroll_tv.stopViewAnimator();
    }

    @Override
    public ApprenticePresenter createPresenter() {
        return new ApprenticePresenter();
    }

    @Override
    public void getApprenticeScrollData(ApprenticeScrollMessageBean bean) {
        if (bean.getErrcode() == 0) {
            if (bean.getReturn() == null)
                return;

            for (int i = 0; i < bean.getReturn().size(); i++) {
                autoStringList.add(bean.getReturn().get(i).getName());
            }
            auto_scroll_tv.setDatas(autoStringList);
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            auto_scroll_tv.setDatasWithDrawableIcon(autoStringList, drawable, 18, Gravity.LEFT);
            auto_scroll_tv.startViewAnimator();
        }
    }
}
