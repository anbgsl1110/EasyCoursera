package com.jasonwangex.easyCoursera.answer.service;

import com.jasonwangex.easyCoursera.answer.domain.Answer;

/**
 * Created by wangjz
 * on 17/5/23.
 */
public interface AnswerService {

    Answer createOrGet(int examId, int userId);

    Answer reply(int answerId, String reply, boolean judge);
}
