package com.jasonwangex.easyCoursera.common.service;

import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;

import java.lang.reflect.Field;

/**
 * Created by wangjz
 * on 17/5/11.
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
    private BaseDao baseDao;

    @Override
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T save(T obj) {
        if (obj == null) return null;
        baseDao.save(obj);
        return obj;
    }

    @Override
    public T create(Object param) {

        Class clazz = param.getClass();
        
        
        
        return null;
    }


}
