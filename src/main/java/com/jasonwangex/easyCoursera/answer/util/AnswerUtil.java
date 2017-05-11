package com.jasonwangex.easyCoursera.answer.util;

import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.common.util.EcConsts;
import com.jasonwangex.easyCoursera.examination.domain.Examination;

/**
 * Created by wangjz
 * on 17/5/12.
 */
public class AnswerUtil {

    public static boolean check(Examination examination, Answer answer) {

        if (examination.getType() == EcConsts.Exam.TYPE_ANSWER_UNIQUE) {
            boolean right = examination.getAnswer().equals(answer.getContent());
            if (right) answer.setJudge(true);
            return right;
        }

        return false;
    }
}
