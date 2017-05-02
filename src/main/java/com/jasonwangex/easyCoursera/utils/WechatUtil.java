package com.jasonwangex.easyCoursera.utils;

import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by wangjz
 * on 17/2/13.
 */
@Component
public class WechatUtil {
    private static String appSignKey;
    private static String appId;
    private static String host = "https://open.weixin.qq.com";
    private static String apiHost = "https://api.weixin.qq.com";

    private static Map<String, String> packageSign(Map<String, String> params) {

        params.put("appId", appId);
        params.put("appSignKey", appSignKey);
        params.put("nonce", RandomStringUtils.random(32, true, true));

        params.put("sign", sign(params));
        params.remove("appSignKey");

        return params;
    }

    public static String sign(Map<String, String> params) {
        TreeSet<String> treeSet = new TreeSet<>();

        params.forEach((key, value) -> treeSet.add(value));

        StringBuilder stringBuilder = new StringBuilder();

        treeSet.forEach(stringBuilder::append);
        return SecurityUtil.SHA1(stringBuilder.toString());
    }

    @SuppressWarnings("deprecation")
    public static void refreshAccessToken() {
        Map<String, Object> params = new HashMap<>();

        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", appSignKey);

        String responseStr = HttpUtil.get(apiHost + "/cgi-bin/token", params);

        Map response = JsonUtil.toObject(Map.class, responseStr);

        if (response != null && response.containsKey("access_token")) {
            WechatClient.setAccessToken((String) response.get("access_token"));
        }
    }

    public static String getOAuthUrl(String url, String state) {
        return host + "/connect/oauth2/authorize?appid=" + appId
                + "&redirect_uri=" + WebUtil.encodeUrl(url)
                + "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";
    }

    public static String getOAuthUrl(String url) {
        return getOAuthUrl(url, "null");
    }

    public String getHost() {
        return host;
    }

    public String getApiHost(){
        return apiHost;
    }


    @Value("${weixin.appSignKey}")
    public void setAppSignKey(String appSignKey) {
        WechatUtil.appSignKey = appSignKey;
    }
    @Value("${weixin.appId}")
    public void setAppId(String appId) {
        WechatUtil.appId = appId;
    }

    public static String getAppSignKey() {
        return appSignKey;
    }

    public static String getAppId() {
        return appId;
    }

}
