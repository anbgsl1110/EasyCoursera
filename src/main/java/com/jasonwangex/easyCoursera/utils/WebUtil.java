package com.jasonwangex.easyCoursera.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wangjz
 * on 17/4/29.
 */
public class WebUtil {

    /**
     * 判断一个请求是否是微信浏览器
     *
     * @param request
     * @return
     */
    public static boolean isFromWechat(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return StringUtils.isNotEmpty(agent) && agent.toLowerCase().contains("micromessenger/");
    }

    /**
     * 发送 403
     * @param response
     */
    public static void error403(HttpServletResponse response) {
        try {
            response.sendError(403);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encodeUrl(String url) {
        if(url != null && url.length() > 0) {
            try {
                return URLEncoder.encode(url, "UTF-8");
            } catch (Exception ignore) {
            }
        }

        return url;
    }

    public static String decodeUrl(String url) {
        if(url != null && url.length() > 0) {
            try {
                return URLDecoder.decode(url, "UTF-8");
            } catch (Exception ignore) {
            }
        }

        return url;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
