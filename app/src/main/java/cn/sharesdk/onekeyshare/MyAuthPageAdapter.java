package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by L on 2018/6/22.
 */

public class MyAuthPageAdapter extends AuthorizeAdapter {

    public void onCreate() {
        hideShareSDKLogo();
    }
}
