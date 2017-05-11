package com.jasonwangex.easyCoursera.common.dao;

import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wangjz
 * on 17/2/23.
 */
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T extends BaseEntity> extends HibernateDaoSupport implements BaseDao<T> {
    private Class thisClass;

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
    public Class<T> getThisClass() {
        if (thisClass == null) {
            thisClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return thisClass;
    }

    /**
     * 获取泛型的 tableName
     *
     * @return
     */
    protected String getThisTable() {
        Class t = getThisClass();
        Table table = (Table) t.getAnnotation(Table.class);
        return table.name();
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
    public List<T> getListById(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return Collections.emptyList();

        List<Criterion> criteria = new ArrayList<>(1);
        criteria.add(Restrictions.in("id", ids));
        return getList(criteria);
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
        if (CollectionUtils.isEmpty(orders)) {
            orders = new ArrayList<>(1);
            orders.add(Order.desc("id"));
        }

        final Class<T> thisClass = getThisClass();

        List<Order> finalOrders = orders;
        return getHibernateTemplate().execute((session) -> {
            Criteria sessionCriteria = session.createCriteria(thisClass);

            if (criteria != null) criteria.forEach(sessionCriteria::add);
            finalOrders.forEach(sessionCriteria::addOrder);

            if (limit > 0) sessionCriteria.setMaxResults(limit);
            if (offset > 0) sessionCriteria.setFirstResult(offset);
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
    public int updateField(String field, String value, int id) {
        String sql = "UPDATE " + getThisTable() + " SET " + field + "=? WHERE id=?";
        return update(sql, value, id);
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
    public List<T> get(String query, Object... objects) {
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

            return sqlQuery.addEntity(getThisClass()).list();
        }));
    }

    @Override
    public int count(List<Criterion> criteria) {
        final Class<T> thisClass = getThisClass();
        Number number = getHibernateTemplate().execute((session) -> (Number) session.createCriteria(thisClass).setProjection(Projections.rowCount()));

        return number.intValue();
    }

    @Override
    public PageBean<T> getPage(List<Criterion> criteria, List<Order> orders, int page, int size) {
        int total = count(criteria);
        PageBean<T> pageBean = new PageBean<>(page, size, total);
        List<T> items = getList(criteria, orders, pageBean.getOffset(), size);
        pageBean.setItems(items);
        return pageBean;
    }

    @Override
    public T getOne(String query, Object... objects) {
        List<T> objects1 = get(query, objects);
        if (CollectionUtils.isEmpty(objects1)) return null;
        return objects1.get(0);
    }

    private String getSql(String sql, Object[] params) {
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
