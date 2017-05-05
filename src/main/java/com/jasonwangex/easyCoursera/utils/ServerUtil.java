package com.jasonwangex.easyCoursera.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

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

    public static String getLoginUri(HttpServletRequest request) {
        String redirect = "/index";
        if (StringUtils.isNotBlank(request.getRequestURI())) {
            redirect = request.getRequestURI();
        }
        request.setAttribute("redirect", WebUtil.encodeUrl(redirect));

        return "/login";
    }
}
