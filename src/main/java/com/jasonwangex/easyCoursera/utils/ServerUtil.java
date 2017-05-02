package com.jasonwangex.easyCoursera.utils;

/**
 * Created by wangjz
 * on 17/4/30.
 */
public class ServerUtil {

    public static final boolean LOCAL = System.getProperty("env.location", "prd").equals("local");
    public static final boolean PRD = System.getProperty("env.location", "prd").equals("prd");
    private static final String HOST_PRD = "http://ec.jasonwangex.com";

    public static String getWebRoot() {
        if (PRD) return HOST_PRD;

        if (LOCAL) return "http://localhost:8081";

        return null;
    }

    public static String getUrl(String uri) {
        return getWebRoot() + uri;
    }
}
