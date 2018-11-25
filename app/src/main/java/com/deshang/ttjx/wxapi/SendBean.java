package com.deshang.ttjx.wxapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 13364 on 2017/4/18.
 * 分享参数相关
 */

public class SendBean {
    private Map<String, String> key;

    public SendBean(){
        key = new HashMap<>();
        setKey();
    }

    public Map<String, String> getKey(){
        return key;
    }

    private void setKey(){
        key.put("com.tencent.mobileqq", "wxf0a80d0ac2e82aa7");
        key.put("com.tencent.mtt", "wx64f9cf5b17af074d");
        key.put("com.sina.weibo", "wx299208e619de7026");
        key.put("com.UCMobile", "wx020a535dccd46c11");
        key.put("com.ss.android.article.news", "wx50d801314d9eb858");
        key.put("com.vivo.browser", "wx3ba80a4a60c4329d");
        key.put("com.tencent.news", "wx073f4a4daff0abe8");
        key.put("com.tencent.reading", "wxe90c9765ad00e2cd");
        key.put("com.baidu.searchbox", "wx27a43222a6bf2931");
        key.put("com.android.browser", "wxd7d1c11a5a7fa0df");
    }

}
