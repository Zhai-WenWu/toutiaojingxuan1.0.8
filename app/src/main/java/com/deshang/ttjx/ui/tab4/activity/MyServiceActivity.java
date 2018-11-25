package com.deshang.ttjx.ui.tab4.activity;

import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 我的客服页面
 * Created by L on 2018/6/8.
 */

public class MyServiceActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_my_service);
    }

    @Override
    public void initView() {
        titleBar.setTitle("联系客服");
        titleBar.setBack(true);
    }

    @OnClick({R.id.copy, R.id.open_we_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText("qipabaolukuang");
                ReceiveGoldToast.makeToast(MyServiceActivity.this, "复制成功").show();
                break;

            case R.id.open_we_chat:
                Platform plat = ShareSDK.getPlatform(Wechat.NAME);
                if (plat.isClientValid()) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } else {
                    ReceiveGoldToast.makeToast(this, "请安装微信客户端").show();
                    return;
                }
                break;
        }
    }
}
