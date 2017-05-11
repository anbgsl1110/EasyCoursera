package com.jasonwangex.easyCoursera.utils;

import com.jasonwangex.easyCoursera.common.bean.Wrapper;

/**
 * Created by wangjz
 * on 17/5/12.
 */
public class IdUtil {

    public static int getInt(String lock) {
        Wrapper<Integer> wrapper = new Wrapper<>();
        LockUtil.lock("EC_LOCK_ID_MAKE_" + lock, () -> {
            Integer value = CacheUtil.getCache("EC_ID_MAKE_" + lock);
            if (value == null) value = 1;
            CacheUtil.setCache("EC_ID_MAKE_" + lock, value + 1);
            wrapper.set(value);
        });
        return wrapper.get();
    }

}
