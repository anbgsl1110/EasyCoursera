package com.jasonwangex.easyCoursera.answer.api;

import com.jasonwangex.easyCoursera.answer.dao.AnswerDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
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
@NeedRole(UserRoleEnum.TEACHER)
@RequestMapping("/teacher/api/answer")
public class TeacherAnswerApi {
    @Resource
    private AnswerDao answerDao;

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public ECResponse commitAnswer(@RequestParam(value = "reply", required = false) String reply,
                                   @RequestParam(value = "right", required = false, defaultValue = "true") boolean right,
                                   @RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        Answer answer = answerDao.getById(id);
        if (answer == null) return ECResponse.notExist();

        answer.setJudge(right);
        answer.setReply(reply);

        // todo message

        return ECResponse.items(answer);
    }

}
