package com.jasonwangex.easyCoursera.cache.service;

import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/3.
 */
public interface EcCacheService {

    void setOrRefreshCache(String key, Serializable value, int ttl);

    <T extends Serializable> T getCache(String key);

    void deleteCache(String key);
}
