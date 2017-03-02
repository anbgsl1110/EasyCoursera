package com.jasonwangex.easyCoursera.common.dao;

import com.jasonwangex.easyCoursera.common.domain.BaseDomain;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wangjz
 * on 17/2/23.
 */
@Component
@Transactional
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T extends BaseDomain> extends HibernateDaoSupport implements BaseDao<T> {

    @Autowired
    public void setHibernateTemplate0(HibernateTemplate hibernateTemplate) {
        super.setHibernateTemplate(hibernateTemplate);
    }

    /**
     * 获取泛型的 class
     * 详情见 http://blog.csdn.net/gengv/article/details/5178055
     *
     * @return
     */
    protected Class<T> getThisClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(T obj) {
        if (obj == null) return;

        HibernateTemplate template = getHibernateTemplate();

        obj.beforeUpdate();

        if (obj.nullToEmpty()) {

        }

        if (obj.getId() == null) obj.setCreateTime(new Date());

        obj.setModifyTime(new Date());
        template.saveOrUpdate(obj);

        obj.afterUpdate();
    }

    @Override
    public T getById(int id) {
        if (id <= 0) return null;
        return getHibernateTemplate().get(getThisClass(), id);
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) return;
        getHibernateTemplate().delete(getById(id));
    }

    @Override
    public void delete(T obj) {
        if (obj == null) return;

        getHibernateTemplate().delete(obj);
    }

    @Override
    public void delete(List<Criterion> criteria) {
        List<T> list = getList(criteria);

        getHibernateTemplate().delete(list);
    }

    @Override
    public List<T> getList(List<Criterion> criteria) {
        return getList(criteria, null, 0, 0);
    }

    @Override
    public List<T> getList(List<Criterion> criteria, List<Order> orders) {
        return getList(criteria, orders, 0, 0);
    }

    @Override
    public List<T> getList(List<Criterion> criteria, int offset, int limit) {
        return getList(criteria, null, offset, limit);
    }

    @Override
    public List<T> getList(List<Criterion> criteria, List<Order> orders, int offset, int limit) {

        final Class<T> thisClass = getThisClass();
        return getHibernateTemplate().execute((session) -> {
            Criteria sessionCriteria = session.createCriteria(thisClass);

            if (criteria != null) criteria.forEach(sessionCriteria::add);
            if (orders != null) orders.forEach(sessionCriteria::addOrder);

            if (offset > 0) sessionCriteria.setMaxResults(limit);
            if (limit > 0) sessionCriteria.setFirstResult(offset);
            return sessionCriteria.list();
        });
    }

    @Override
    public T getOne(List<Criterion> criteria) {
        List<T> rtn = getList(criteria, null, 0, 1);
        if (CollectionUtils.isEmpty(rtn)) return null;
        return rtn.get(0);
    }

    @Override
    public T getOne(List<Criterion> criteria, List<Order> orders) {
        List<T> rtn = getList(criteria, orders, 0, 1);
        if (CollectionUtils.isEmpty(rtn)) return null;
        return rtn.get(0);
    }

    @Override
    public int update(String query, Object... objects) {
        if (StringUtils.isEmpty(query)) return 0;

        String sql = getSql(query, objects);
        return getHibernateTemplate().execute((session -> {
            SQLQuery sqlQuery = session.createSQLQuery(sql);

            List<Object> complexParams = (List<Object>) Stream.of(objects)
                    .flatMap(param -> {
                        if (param instanceof Collection) return ((Collection) param).stream();
                        else return Stream.of(param);
                    }).collect(Collectors.toList());

            int setCounter = 0;
            for (Object param : complexParams) {
                sqlQuery.setParameter(setCounter++, param);
            }

            return sqlQuery.executeUpdate();
        }));
    }

    @Override
    public int insert(String query, Object... objects) {
        return update(query, objects);
    }

    @Override
    public int delete(String query, Object... objects) {
        return update(query, objects);
    }

    @Override
    public List<Object[]> get(String query, Object... objects) {
        if (StringUtils.isEmpty(query)) return null;

        String sql = getSql(query, objects);
        return getHibernateTemplate().execute((session -> {
            SQLQuery sqlQuery = session.createSQLQuery(sql);

            List<Object> complexParams = (List<Object>) Stream.of(objects)
                    .flatMap(param -> {
                        if (param instanceof Collection) return ((Collection) param).stream();
                        else return Stream.of(param);
                    }).collect(Collectors.toList());

            int setCounter = 0;
            for (Object param : complexParams) {
                sqlQuery.setParameter(setCounter++, param);
            }

            return sqlQuery.list();
        }));
    }

    private String getSql(String sql, Object[] params){
        sql = sql + " ";
        int counter = 0;
        String[] departs = sql.split("\\?");

        StringBuilder stringBuilder = new StringBuilder();

        for (Object param : params) {
            String placeholder = "?";
            if (param instanceof Collection) {
                placeholder = ((Collection) param).stream().map(single -> " ?").collect(Collectors.joining(",")).toString();
            }
            stringBuilder.append(departs[counter++]).append(placeholder);
        }

        stringBuilder.append(departs[counter]);
        return stringBuilder.toString();
    }

}
