package com.jasonwangex.easyCoursera.examination.dao;

import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wangjz
 * on 17/5/9.
 */
@Repository
public class ExaminationDaoImpl extends BaseDaoImpl<Examination> implements ExaminationDao {

    @Override
    public PageBean<Examination> getPage(Integer type, Integer creator, int page, int size) {
        List<Criterion> criteria = new ArrayList<>();

        if (type != null) criteria.add(Restrictions.eq("type", type));
        if (creator != null) criteria.add(Restrictions.eq("creator", creator));

        return getPage(criteria, null, page, size);
    }



    @Override
    public PageBean getPage(Object obj, int page, int size) throws Throwable {
        List<Criterion> criteria = new ArrayList<>();
        Class clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object val = field.get(obj);
            if (val != null) {
                String sqlField = field.getName();
                criteria.add(Restrictions.eq(sqlField, val));
            }
        }
        return getPage(criteria, null, page, size);
    }
}
