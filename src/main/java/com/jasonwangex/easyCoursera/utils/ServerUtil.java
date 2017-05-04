package com.jasonwangex.easyCoursera.utils;

/**
 * Created by wangjz
 * on 17/4/30.
 */
public class ServerUtil {

    private static final String HOST = "ec.jasonwangex.com";

    public static String getWebRoot() {
        return "http://" + HOST;
    }

    public static String getHost(){
        return HOST;
    }

    public static String getUrl(String uri) {
        return getWebRoot() + uri;
    }
}
