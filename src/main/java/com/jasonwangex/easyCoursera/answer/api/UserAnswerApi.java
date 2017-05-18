package com.jasonwangex.easyCoursera.answer.api;

import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.answer.util.AnswerUtil;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.examination.dao.ExaminationDao;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@RestController
@NeedRole(UserRoleEnum.USER)
@RequestMapping("/user/api/answer")
public class UserAnswerApi {
    @Resource
    private AnswerDao answerDao;
    @Resource
    private ExaminationDao examinationDao;

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public ECResponse commitAnswer(@RequestParam(value = "content", required = false) String content,
                                   @RequestParam(value = "examId", required = false, defaultValue = "0") int examId) {
        EcSession session = EcSessionUtil.getSession();

        if (StringUtils.isBlank(content) || content.length() > 5000) return ECResponse.fail("回答不嫩为空或超过 5000 字");
        Examination exam = examinationDao.getByField("examId", examId);
        if (exam == null) return ECResponse.notExist();

        Answer answer = answerDao.getOrRefresh(session.getUserId(), examId, content);

        if (answer == null) return ECResponse.fail("题目不存在或超出答题限制");

        if (exam.getType() == EcConsts.Exam.TYPE_ANSWER_UNIQUE) {
            if (AnswerUtil.check(exam, answer)) answerDao.save(answer);
            // todo message
        }

        return ECResponse.items(answer);
    }
}
