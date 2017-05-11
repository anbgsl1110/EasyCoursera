package com.jasonwangex.easyCoursera.common.util;

import com.jasonwangex.easyCoursera.common.dao.BaseDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/11.
 */
public class SpringUtil {
    private static Map<Class, BaseDao> DAO_MAP = new HashMap<>();

    @Deprecated
    public static void add(Class entityClass, BaseDao baseDao) {
        DAO_MAP.put(entityClass, baseDao);
    }

    public static BaseDao getDao(Class entityClass) {
        return DAO_MAP.get(entityClass);
    }
}
