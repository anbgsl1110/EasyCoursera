package com.jasonwangex.easyCoursera.examination.dao;

import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.examination.domain.Examination;

/**
 * Created by wangjz
 * on 17/5/9.
 */
public interface ExaminationDao extends BaseDao<Examination> {

    PageBean<Examination> getPage(Integer type, Integer creator, int page, int size);

    PageBean getPage(Object obj, int page, int size)  throws Throwable;
}
