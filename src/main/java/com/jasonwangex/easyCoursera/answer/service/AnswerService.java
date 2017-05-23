package com.jasonwangex.easyCoursera.answer.service;

import com.jasonwangex.easyCoursera.account.domain.EcUser;
import com.jasonwangex.easyCoursera.answer.domain.Answer;
import com.jasonwangex.easyCoursera.examination.domain.Examination;

/**
 * Created by wangjz
 * on 17/5/23.
 */
public interface AnswerService {

    Answer createOrGet(int examId, int userId);
}
