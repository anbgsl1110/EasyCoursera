package com.jasonwangex.easyCoursera.message.dao;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import com.jasonwangex.easyCoursera.message.domain.Message;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/5/22.
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {
    @Resource
    private EcUserDao ecUserDao;

    @Override
    public PageBean<Message> getMessagePage(int userId, Integer creator, int page, int size) {

        List<Criterion> criteria = new ArrayList<>();
        criteria.add(Restrictions.eq("userId", userId));
        if (creator != null) criteria.add(Restrictions.eq("creator", creator));

        PageBean<Message> pageBean = this.getPage(criteria, null, page, size);
        List<Integer> userIds = pageBean.getItems().stream().map(Message::getCreator).collect(Collectors.toList());

        List<EcUser> users = ecUserDao.getListById(userIds);
        Map<Integer,String> userIdName = users.stream().collect(Collectors.toMap(EcUser::getId, EcUser::getNickname));

        pageBean.getItems().forEach(message -> message.setCreatorName(userIdName.get(message.getCreator())));

        return pageBean;
    }
}
