package com.jasonwangex.easyCoursera.message.dao;

import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.message.domain.Message;

/**
 * Created by wangjz
 * on 17/5/22.
 */
public interface MessageDao extends BaseDao<Message> {

    PageBean<Message> getMessagePage(int userId, Integer creator, int page, int size);
}
