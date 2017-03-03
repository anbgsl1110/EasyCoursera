package com.jasonwangex.easyCoursera.utils;

import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by wangjz
 * on 17/2/13.
 */
@Component
public class WechatUtil {
    private static String appSignKey;
    private static String appId;
    private static String webroot;

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

        Map response = HttpUtil.get(webroot + "/cgi-bin/token", params);

        if (response != null && response.containsKey("access_token")) {
            WechatClient.setAccessToken((String) response.get("access_token"));
        }
    }


    @Value("${weixin.appSignKey}")
    public void setAppSignKey(String appSignKey) {
        WechatUtil.appSignKey = appSignKey;
    }
    @Value("${weixin.appId}")
    public void setAppId(String appId) {
        WechatUtil.appId = appId;
    }
    @Value("${weixin.webroot}")
    public void setWebroot(String webroot) {
        WechatUtil.webroot = webroot;
    }
}
