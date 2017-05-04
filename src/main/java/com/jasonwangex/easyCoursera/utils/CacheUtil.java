package com.jasonwangex.easyCoursera.utils;

import com.jasonwangex.easyCoursera.cache.service.EcCacheService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/3.
 */
@Component
public class CacheUtil {
    private static EcCacheService cacheService;

    public static void setCache(String key, Serializable value, int ttl) {
        cacheService.setOrRefreshCache(key, value, ttl);
    }

    public static void setCache(String key, Serializable value) {
        setCache(key, value, 0);
    }

    public static <T> T getCache(String key) {
        return cacheService.getCache(key);
    }

    public static <T> T getCache(String key, boolean delete) {
        T rtn = cacheService.getCache(key);
        if (!delete) return rtn;

        if (rtn != null) cacheService.deleteCache(key);

        return rtn;
    }

    @Resource
    public void setCacheService(EcCacheService cacheService) {
        CacheUtil.cacheService = cacheService;
    }
}
