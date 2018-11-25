package com.deshang.ttjx.framework.network;

/**
*  AppConstants.java
*  创建人 hh
*  创建日期 2017/6/5 16:49
*/
public interface AppConstants {

    //---------------OkHttp配置-----------------------
    String RESPONSE_CACHE = "netCache";

    long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024;

    long HTTP_CONNECT_TIMEOUT = 1000 * 20;

    long HTTP_READ_TIMEOUT = HTTP_CONNECT_TIMEOUT;

    //-------------项目配置------------------------
    String PLATFORM_VALUE = "android";

    /**
     * 项目名字做为资源访问路径
     */
    String PROJECT_NAME = "socialComment";

    String IMAGE_SERVER_URL = "http://10.12.5.21:8080/socialWeb";

    String SCRET_KEY = "9cb5ee00b6a87c24a2fabf87c6dba2e1";

    String DEFAULT_BIRTHDAY = "1940-01-01";
}
