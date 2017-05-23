package com.jasonwangex.easyCoursera.callback.service;

import com.jasonwangex.easyCoursera.utils.CacheUtil;
import org.springframework.stereotype.Component;

/**
 * Created by wangjz
 * on 17/5/24.
 */
@Component
public class CallbackContextHelper {

    public void put(Integer userId, String context) {
        CacheUtil.setCache("CALLBACK_CONTEXT_MAP_" + userId, context, 0);
    }

    public String get(Integer userId) {
        return CacheUtil.getCache("CALLBACK_CONTEXT_MAP_" + userId);
    }

    public void remove(Integer userId) {
        CacheUtil.clear("CALLBACK_CONTEXT_MAP_" + userId);
    }
}
