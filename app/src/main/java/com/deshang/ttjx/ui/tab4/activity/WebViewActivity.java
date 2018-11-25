package com.deshang.ttjx.ui.tab4.activity;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.manager.UIManager;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.LogUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.home.activity.BaseActivity;

import com.deshang.ttjx.R;
import com.deshang.ttjx.ui.main.activity.MainActivity;
import com.deshang.ttjx.ui.mywidget.dialog.ShareDialog;

import butterknife.BindView;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 网页加载
 * Created by L on 2017/12/6.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.my_progress)
    ProgressBar progressBar;

    private String loadUrl;

    private int type;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initView() {
        titleBar.setBack(true);
        titleBar.setTitle(getIntent().getStringExtra("Title"));

        type = getIntent().getIntExtra("Type", 0);

        loadUrl = getIntent().getStringExtra("loadUrl");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.addJavascriptInterface(this, "android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtils.d("当前加载进度：" + newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

        webView.loadUrl(loadUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void share() {
        ShareDialog dialog = new ShareDialog(this, R.style.dialog_style);
        dialog.setVisible(1);
        dialog.setClickListener(new ShareDialog.ClickListener() {
            @Override
            public void onClickListener(int type) {
                if (type == 1) {
                    ShareUtils.showShare(WebViewActivity.this, Constants.SHARE_APPRENTICE_TITLE, "", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/1", null, WechatMoments.NAME, 0);
                } else if (type == 2) {
                    ShareUtils.showShare(WebViewActivity.this, Constants.SHARE_APPRENTICE_TITLE, "", SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/2", null, Wechat.NAME, 0);
                } else if (type == 6) {
                    ShareUtils.shareSDK(WebViewActivity.this, Constants.SHARE_APPRENTICE_TITLE, SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId() + "/type/1/share_type/6", null, QQ.NAME, 0);
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type == 3) {
            UIManager.turnToAct(this, MainActivity.class);
        }
    }
}
