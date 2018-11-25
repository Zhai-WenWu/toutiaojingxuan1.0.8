package com.deshang.ttjx.ui.tab4.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deshang.ttjx.R;
import com.deshang.ttjx.framework.base.MvpSimpleActivity;
import com.deshang.ttjx.framework.contant.Constants;
import com.deshang.ttjx.framework.spfs.SharedPrefHelper;
import com.deshang.ttjx.framework.utils.ProjectUtils;
import com.deshang.ttjx.framework.utils.ShareUtils;
import com.deshang.ttjx.framework.widget.TitleBar;
import com.deshang.ttjx.ui.main.bean.BaseBean;
import com.deshang.ttjx.ui.mywidget.ReceiveGoldToast;
import com.deshang.ttjx.ui.tab4.persenter.ShowIncomePresenter;
import com.deshang.ttjx.ui.tab4.view.ShowIncomeView;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 晒收入
 * Created by L on 2018/7/3.
 */

public class ShowIncomeActivity extends MvpSimpleActivity<ShowIncomeView, ShowIncomePresenter> implements ShowIncomeView {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.qr_image)
    ImageView qrImage;
    @BindView(R.id.share_we_chat)
    ImageView shareWeChat;
    @BindView(R.id.share_circle)
    ImageView shareCircle;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.rl_image)
    RelativeLayout rlImage;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_show_income);
    }

    @Override
    public void initView() {
        titleBar.setBack(true);
        titleBar.setTitle("晒收入");
        Bitmap bitmap = EncodingUtils.Create2DCode(SharedPrefHelper.getInstance().getApprenticeUrl() + Constants.SHARE_APPRENTICE + SharedPrefHelper.getInstance().getUserId(),
                ProjectUtils.dp2px(this, 120), ProjectUtils.dp2px(this, 120), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        qrImage.setImageBitmap(bitmap);
    }

    @OnClick({R.id.share_we_chat, R.id.share_circle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_we_chat:
                getPresenter().getShowIncome(0);
                break;

            case R.id.share_circle:
                getPresenter().getShowIncome(1);
                break;
        }
    }

    @Override
    public void getShowIncome(BaseBean bean, int type) {
        if (bean.errcode == 0) {
            if (type == 1) {
                ShareUtils.shareImage(this, rlImage, WechatMoments.NAME);
            } else {
                ShareUtils.shareImage(this, rlImage, Wechat.NAME);
            }
        } else {
            showToast(bean.errinf);
        }
    }

    @Override
    public ShowIncomePresenter createPresenter() {
        return new ShowIncomePresenter();
    }
}
