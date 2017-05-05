package com.jasonwangex.easyCoursera.common.dao;

import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

/**
 * 基础 dao 类接口
 * Created by wangjz
 * on 17/2/24.
 */
public interface BaseDao<T extends BaseEntity> {

    void setHibernateTemplate0(HibernateTemplate hibernateTemplate);

    void save(T obj);

    T getById(int id);

    void deleteById(int id);

    void delete(T obj);

    void delete(List<Criterion> criteria);

    List<T> getList(List<Criterion> criteria);

    List<T> getList(List<Criterion> criteria, List<Order> orders);

    List<T> getList(List<Criterion> criteria, int offset, int limit);

    List<T> getList(List<Criterion> criteria, List<Order> orders, int offset, int limit);

    T getOne(List<Criterion> criteria);

    T getOne(List<Criterion> criteria, List<Order> orders);

    T getOne(String query, Object... objects);

    int update(String query, Object... objects);

    int updateField(String field, String value, int id);

    int insert(String query, Object... objects);

    int delete(String query, Object... objects);

    List get(String query, Object... objects);
}
