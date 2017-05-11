package com.jasonwangex.easyCoursera.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangjz
 * on 17/4/22.
 */
@Component
public class ContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name, Class<T> tClass) {
        try {
            return applicationContext.getBean(name, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getBean(String name) {
        try {
            return applicationContext.getBean(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpServletRequest getHttpServletRequest() {
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return sra != null ? sra.getRequest() : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
