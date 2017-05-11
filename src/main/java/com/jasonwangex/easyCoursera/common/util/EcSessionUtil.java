package com.jasonwangex.easyCoursera.common.util;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by wangjz
 * on 17/4/22.
 */
@Component
public class EcSessionUtil {
    @Value("${ec.session.salt}")
    public static String EC_SESSION_SALT;

    public static final String EC_SESSION = "EcSession";
    private static final String EC_COOKIE_NAME = "EC_COOKIE_DATA_";
    private static final int EC_SESSION_LIFE = 3 * 3600;            // session 失效时间为 3 个小时

    public static void setSession(HttpServletRequest request, HttpServletResponse response, EcSession session) {
        String host = ServerUtil.getHost();
        if (session == null) return;

        try {
            String sessionStr = JsonUtil.toString(session);
            Cookie cookie = new Cookie(EC_COOKIE_NAME, Base64Util.encodeUrl(sessionStr));
            cookie.setPath("/");
            cookie.setDomain(host);
            cookie.setMaxAge(EC_SESSION_LIFE);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EcSession getSession() {
        HttpServletRequest request = ContextUtil.getHttpServletRequest();
        return getSession(request);
    }

    public static EcSession getSession(HttpServletRequest request) {
        if (request == null) return new EcSession();

        Object session = request.getAttribute(EC_SESSION);
        if (session != null) return (EcSession) session;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) return new EcSession();

        return Arrays.stream(cookies)
                .filter(cookie -> EC_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .map(Base64Util::decodeUrl)
                .map(value -> JsonUtil.toObject(EcSession.class, value))
                .filter(ecSession -> ecSession.getTimestamp() + EC_SESSION_LIFE * 1000L > System.currentTimeMillis())
                .orElse(new EcSession());
    }

    public static String getSign(String password) {
        String tempToken = SecurityUtil.SHA1(password);
        return SecurityUtil.SHA1(tempToken + EC_SESSION_SALT);
    }

    public static String getSign(EcSession ecSession) {
        String sessionStr = ecSession.getOpenId()
                + ecSession.getTimestamp()
                + ecSession.getUserId()
                + ecSession.getNonce()
                + Arrays.toString(ecSession.getRoleIds().toArray());

        return getSign(sessionStr);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null) return;

        Stream.of(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .forEach(cookie -> {
                    cookie = new Cookie(cookieName, null);
                    cookie.setDomain(ServerUtil.getHost());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                });
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        deleteCookie(request, response, EC_COOKIE_NAME);
    }

}
