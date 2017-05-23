package com.jasonwangex.easyCoursera.answer.service;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerDao answerDao;

    @Override
    public Answer createOrGet(int examId, int userId) {
        List<Criterion> criteria = new ArrayList<>();

        criteria.add(Restrictions.eq("examId", examId));
        criteria.add(Restrictions.eq("userId", userId));
        Answer answer = answerDao.getOne(criteria);

        if (answer != null) return answer;

        answer = new Answer();
        answer.setExamId(examId);
        answer.setUserId(userId);

        answerDao.save(answer);
        return answer;
    }
}
