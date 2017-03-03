package com.jasonwangex.easyCoursera.wechat.bean;

import org.springframework.stereotype.Component;


/**
 * Created by wangjz
 * on 17/3/3.
 */
@Component
public class WechatClient {
    private static String accessToken;

    @Deprecated
    public static void setAccessToken(String accessToken) {
        WechatClient.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return accessToken;
    }

}
