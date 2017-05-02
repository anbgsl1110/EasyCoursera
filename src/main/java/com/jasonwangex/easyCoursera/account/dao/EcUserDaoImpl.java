package com.jasonwangex.easyCoursera.account.dao;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangjz
 * on 17/2/23.
 */
@Repository
public class EcUserDaoImpl extends BaseDaoImpl<EcUser> implements EcUserDao {
    @Override
    public EcUser getByOpenId(String openid) {
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("openid", openid));
        return this.getOne(criteria);
    }
}
