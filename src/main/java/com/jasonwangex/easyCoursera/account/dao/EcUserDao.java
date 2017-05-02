package com.jasonwangex.easyCoursera.account.dao;


import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.dao.BaseDao;

/**
 * Created by wangjz
 * on 17/2/24.
 */
public interface EcUserDao extends BaseDao<EcUser> {

    EcUser getByOpenId(String openid);
}
