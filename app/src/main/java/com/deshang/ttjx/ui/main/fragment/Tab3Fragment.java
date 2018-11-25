package com.deshang.ttjx.ui.main.fragment;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleFragment;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.framework.utils.UserPathUtils;
import com.deshang.ttjx.ui.main.presenter.Tab3Presenter;
import com.deshang.ttjx.ui.main.view.Tab3View;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.mywidget.dialog.QRApprenticeDialog;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;
import com.deshang.ttjx.ui.tab3.activity.HowApprenticeActivity;
import com.deshang.ttjx.ui.tab3.activity.MyApprenticeActivity;
import com.deshang.ttjx.ui.tab3.bean.Tab3Bean;
import com.deshang.ttjx.ui.tab4.activity.ApprenticeActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 收徒Tab模块
 * Created by L on 2017/12/4.
 */
public class Tab3Fragment extends MvpSimpleFragment<Tab3View, Tab3Presenter> implements Tab3View {

    @BindView(R.id.reward_num)
    TextView reward_num;

    @Override
    public void setContentLayout(Bundle savedInstanceState) {
        setContentView(R.layout.f_tab3);
    }

    @Override
    public void initView(View v) {
        getPresenter().getApprenticeChange();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().getApprenticeChange();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private RelativeLayout imageView;

    @OnClick({R.id.one, R.id.go_apprentice, R.id.reward_num})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.go_apprentice: // 立即收徒
                UserPathUtils.commitUserPath(13);
                final ShareDialog dialog = new ShareDialog(getActivity(), R.style.dialog_style);
                dialog.setType(3);
                dialog.setClickListener(new ShareDialog.ClickListener() {
                    @Override
                    public void onClickListener(int type) {
                        getPresenter().apprenticeClickNum(type, 1);
                        switch (type) {
                            case 1:
                                // 朋友圈
                                ShareUtils.showShare(getActivity(), Constants.SHARE_APPRENTICE_TITLE, "", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/3/share_type/1", null, WechatMoments.NAME, 0);
                                break;
                            case 2:
                                // 微信
                                ShareUtils.showShare(getActivity(), Constants.SHARE_APPRENTICE_TITLE, "", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/3/share_type/2", null, Wechat.NAME, 0);
                                break;
                            case 3:
                                Uri smsToUri = Uri.parse("smsto:");
                                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                //短信内容
                                intent.putExtra("sms_body", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/3/share_type/3");
                                startActivity(intent);
                                break;
                            case 4:
                                QRApprenticeDialog dialog = new QRApprenticeDialog(getActivity(), 3);
                                dialog.setOnSureClickListener(new QRApprenticeDialog.OnSureClickListener() {
                                    @Override
                                    public void onSureClick(RelativeLayout view) {
                                        // 二维码弹窗确认
                                        // 判断是否已经赋予权限
                                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                            // 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                                                ReceiveGoldToast.makeToast(getActivity(), "请前往设置授予权限后，才可使用此功能").show();
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
                                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/3/share_type/5");
                                ReceiveGoldToast.makeToast(getActivity(), "复制成功").show();
                                break;
                            case 6:
                                // QQ
                                ShareUtils.shareSDK(getActivity(), Constants.SHARE_APPRENTICE_TITLE, SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/3/share_type/6", null, QQ.NAME, 0);
                                break;
                        }
                    }
                });
                dialog.show();
                break;

            case R.id.one: // 如何收徒
                UserPathUtils.commitUserPath(14);
                UIManager.turnToAct(getActivity(), HowApprenticeActivity.class);
                break;

            case R.id.reward_num:
                UserPathUtils.commitUserPath(15);
                UIManager.turnToAct(getActivity(), MyApprenticeActivity.class);
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
                    ReceiveGoldToast.makeToast(getActivity(), "请前往设置授予权限后，才可使用此功能").show();
                }
            }
        }
    }

    @Override
    public Tab3Presenter createPresenter() {
        return new Tab3Presenter();
    }

    @Override
    public void getApprenticeChange(Tab3Bean bean) {
        if (bean.errcode == 0) {
            reward_num.setText("我已经获得收徒奖励" + bean.change + "元");
        } else {
            showToast(bean.errinf);
        }
    }
}
