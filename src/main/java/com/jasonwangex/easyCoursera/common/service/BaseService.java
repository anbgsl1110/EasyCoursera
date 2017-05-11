package com.jasonwangex.easyCoursera.common.service;


import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;

/**
 * Created by wangjz
 * on 17/5/11.
 */
public interface BaseService<T extends BaseEntity> {

    void setBaseDao(BaseDao baseDao);

    T save(T obj);

    T create(Object param);
}
