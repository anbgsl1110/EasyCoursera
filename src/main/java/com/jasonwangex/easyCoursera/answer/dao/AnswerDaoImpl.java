package com.jasonwangex.easyCoursera.answer.dao;

import com.jasonwangex.easyCoursera.common.bean.PageBean;
import com.jasonwangex.easyCoursera.common.bean.Wrapper;
import com.jasonwangex.easyCoursera.common.dao.BaseDaoImpl;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.utils.LockUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjz
 * on 17/5/9.
 */
@Repository
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao {
    @Override
    public Answer getOrRefresh(int userId, int examId, String content) {
        List<Criterion> criteria = new ArrayList<>();

        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("examId", examId));

        Wrapper<Answer> wrapper = new Wrapper<>();
        LockUtil.lock("EC_ANSWER_" + userId + "_" + examId, () -> {
            Answer answer = getOne(criteria);
            if (answer == null) return;

            if (answer.isJudge()) return;

            if (answer.getAnswerCount() >= EcConsts.Exam.MAX_ANSWER_COUNT) return;
            answer.setContent(content);
            answer.setAnswerCount(answer.getAnswerCount() + 1);
            save(answer);
            wrapper.set(answer);
        });

        return wrapper.get();
    }

    @Override
    public PageBean<Answer> getPage(int teacherId, int page, int size) {

        Number total = (Number) getHibernateTemplate().execute((session) -> {
            Criteria sessionCriteria = session.createCriteria(Answer.class, "answer");
            sessionCriteria.createAlias("answer.examination", "examination");
            sessionCriteria.add(Restrictions.eq("closed", false));
            sessionCriteria.add(Restrictions.eq("examination.type", EcConsts.Exam.TYPE_ANSWER_NEED_CHECK));

            return sessionCriteria.setProjection(Projections.rowCount()).uniqueResult();
        });

        PageBean<Answer> pageBean = new PageBean<>(page, size, total.intValue());

        List<Answer> items = getHibernateTemplate().execute((session) -> {
            Criteria sessionCriteria = session.createCriteria(Answer.class, "answer");
            sessionCriteria.createAlias("answer.examination", "examination");
            sessionCriteria.add(Restrictions.eq("closed", false));
            sessionCriteria.add(Restrictions.eq("examination.type", EcConsts.Exam.TYPE_ANSWER_NEED_CHECK));

            sessionCriteria.setMaxResults(pageBean.getSize());
            sessionCriteria.setFirstResult(pageBean.getOffset());
            return sessionCriteria.list();
        });

        pageBean.setItems(items);
        return pageBean;
    }

}
