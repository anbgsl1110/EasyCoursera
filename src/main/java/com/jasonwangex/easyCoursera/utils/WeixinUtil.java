package com.jasonwangex.easyCoursera.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangjz
 * on 17/2/13.
 */
public class WeixinUtil {

    private String appSignKey;

    private String appId;

    private Map<String, String> packageSign(Map<String, String> params){
        TreeMap<String, String> treeMap = new TreeMap<>();
        params.forEach(treeMap::put);

        treeMap.put("appId", appId);
        treeMap.put("appSignKey", appSignKey);
        treeMap.put("nonce", RandomStringUtils.random(32, true, true));

        StringBuilder signSb = new StringBuilder();
        treeMap.forEach((key, value) -> signSb.append(key).append("=").append(value).append("&"));

        if (signSb.length() > 0) signSb.deleteCharAt(signSb.length() - 1);

        String sign = SecurityUtil.SHA1(signSb.toString());

        treeMap.put("sign", sign);
        treeMap.remove("appSignKey");

        return treeMap;
    }
}
