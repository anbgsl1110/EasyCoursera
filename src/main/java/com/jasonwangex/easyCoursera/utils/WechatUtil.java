package com.jasonwangex.easyCoursera.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangjz
 * on 17/2/13.
 */
@Component
public class WechatUtil {
    @Value("${weixin.appSignKey}")
    private static String appSignKey;

    @Value("${weixin.appId}")
    private static String appId;

    private static Map<String, String> packageSign(Map<String, String> params){

        params.put("appId", appId);
        params.put("appSignKey", appSignKey);
        params.put("nonce", RandomStringUtils.random(32, true, true));

        params.put("sign", sign(params));
        params.remove("appSignKey");

        return params;
    }

    public static String sign(Map<String, String> params) {
        TreeMap<String, String> treeMap = new TreeMap<>();

        params.forEach(treeMap::put);

        StringBuilder signSb = new StringBuilder();
        treeMap.forEach((key, value) -> signSb.append(key).append("=").append(value).append("&"));
        if (signSb.length() > 0) signSb.deleteCharAt(signSb.length() - 1);

        return SecurityUtil.SHA1(signSb.toString());
    }
}
