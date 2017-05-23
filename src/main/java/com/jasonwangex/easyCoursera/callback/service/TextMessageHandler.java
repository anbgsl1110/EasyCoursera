package com.jasonwangex.easyCoursera.callback.service;

import com.jasonwangex.easyCoursera.account.dao.EcUserDao;
import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.answer.service.AnswerService;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.examination.dao.ExaminationDao;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/23.
 */
@Component
public class TextMessageHandler {
    @Resource
    private AnswerService answerService;
    @Resource
    private ExaminationDao examinationDao;
    @Resource
    private AnswerDao answerDao;
    @Resource
    private EcUserDao ecUserDao;
    @Resource
    private CallbackContextHelper contextHelper;

    public String handleForModifyName(int userId, String content) {
        ecUserDao.updateField("nickname", content, userId);

        return "姓名【" + content + "】绑定成功，如果需要再次修改用户名，请联系系统管理员";
    }

    public String handleForAnswer(int userId, int examId, String content) {
        Examination examination = examinationDao.getById(examId);
        if (examination == null) {
            return "题目不存在或已被删除，请联系老师";
        }

        Answer answer = answerService.createOrGet(examId, userId);
        if (!answer.isClosed() && answer.getAnswerCount() <= 2) {
            answer.setContent(content);
            answer.setAnswerCount(answer.getAnswerCount() + 1);
            answerDao.save(answer);

            if (examination.getType() == EcConsts.Exam.TYPE_ANSWER_UNIQUE) {
                // 客观题
                if (content.equalsIgnoreCase(examination.getAnswer())) return closeAnswer(answer, true);
                else {
                    if (answer.getAnswerCount() >= 3) closeAnswer(answer, false);
                    contextHelper.put(userId, "ANSWER_" + examId);
                    return "回答错误，你还有" + (3 - answer.getAnswerCount()) + "次回答机会";
                }
            } else {
                // 主观题
                if (answer.getAnswerCount() >= 3) return "回答错误，问题修改次数已耗尽";

                contextHelper.put(userId, "ANSWER_" + examId);
                return "回答已提交，等待教师审核。\n你还有" + (3 - answer.getAnswerCount()) + "次回答机会";
            }
        }

        return "问题已关闭";
    }

    private String closeAnswer(Answer answer, boolean right) {
        answer.setJudge(right);
        answer.setClosed(true);
        answerDao.save(answer);
        if (right) return "回答正确";

        return "回答错误，问题回答次数已耗尽，问题已关闭";
    }
}
