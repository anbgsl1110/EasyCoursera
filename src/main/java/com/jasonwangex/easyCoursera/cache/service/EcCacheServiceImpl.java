package com.jasonwangex.easyCoursera.cache.service;

import com.jasonwangex.easyCoursera.cache.dao.EcCacheDao;
import com.jasonwangex.easyCoursera.cache.domain.EcCache;
import com.jasonwangex.easyCoursera.utils.LockUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * todo 换成缓存
 * Created by wangjz
 * on 17/5/3.
 */
@Service
public class EcCacheServiceImpl implements EcCacheService {
    @Resource
    private EcCacheDao ecCacheDao;

    @Override
    public void setOrRefreshCache(String key, Serializable valueObj, int ttl) {
        List<Criterion> criteria = new ArrayList<>(1);
        criteria.add(Restrictions.eq("keyStr", key));

        String value = new String(SerializationUtils.serialize(valueObj), Charset.forName("ISO-8859-1"));
        LockUtil.lock(key, () -> {
            EcCache ecCache = ecCacheDao.getOne(criteria);
            if (ecCache == null) {
                ecCache = new EcCache();
                ecCache.setKeyStr(key);
                ecCache.setTtl(ttl);
            }
            ecCache.setValue(value);
            ecCacheDao.save(ecCache);
        });
    }

    @Override
    public <T extends Serializable> T getCache(String key) {
        try {
            List<Criterion> criteria = new ArrayList<>(1);
            criteria.add(Restrictions.eq("keyStr", key));
            EcCache ecCache = ecCacheDao.getOne(criteria);
            if (ecCache == null || ecCache.isExpire()) return null;

            return (T) SerializationUtils.deserialize(ecCache.getValue().getBytes(Charset.forName("ISO-8859-1")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteCache(String key) {
        if (StringUtils.isBlank(key)) return;

        ecCacheDao.delete("DELETE FROM ec_cache where key_str=?", key);
    }


}
