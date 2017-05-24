package com.jasonwangex.easyCoursera.answer.service;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.examination.dao.ExaminationDao;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import com.jasonwangex.easyCoursera.wechat.bean.WechatClient;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.message.TextMessage;

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
    @Resource
    private EcUserDao ecUserDao;
    @Resource
    private ExaminationDao examinationDao;

    @Override
    public Answer createOrGet(int examId, int userId, int tagId) {
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

    @Override
    public Answer reply(int answerId, String reply, boolean judge) {
        Answer answer = answerDao.getById(answerId);
        if (answer == null) return null;

        answer.setJudge(judge);
        answer.setClosed(true);
        answer.setReply(reply);

        answerDao.save(answer);

        String exam = StringUtils.truncate(answer.getExamination().getContent(), 32);
        EcUser user = ecUserDao.getById(answer.getUserId());
        MessageAPI.messageCustomSend(WechatClient.getAccessToken(),
                new TextMessage(user.getOpenid(), "你的题目：\"" + exam + "\" 获得了老师审核。\n" +
                        "审核结果为" + (judge ? "通过" : "不通过") + "\n\n" +
                        "审核意见为：\n" + reply));

        return answer;
    }
}
