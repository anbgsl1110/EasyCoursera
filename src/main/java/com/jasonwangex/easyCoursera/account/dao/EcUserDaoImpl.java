package com.jasonwangex.easyCoursera.account.dao;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
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

    @Override
    public PageBean<EcUser> getByName(String name, int page, int size) {
        List<Criterion> criteria = new ArrayList<>();

        if (StringUtils.isNotBlank(name)) criteria.add(Restrictions.like("name", "%" + name +"%"));

        List<Order> orders = new ArrayList<>();
        orders.add(Order.desc("id"));

        return getPage(criteria, orders, page, size);
    }

}
