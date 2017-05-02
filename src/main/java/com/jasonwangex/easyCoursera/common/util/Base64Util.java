package com.jasonwangex.easyCoursera.common.util;

import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public class Base64Util {

    public static String decodeUrl(String str) {
        try {
            byte[] bytes = Base64Utils.decodeUrlSafe(str.getBytes("UTF-8"));
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String encodeUrl(String url) {
        try {
            return Base64Utils.encodeToUrlSafeString(url.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
