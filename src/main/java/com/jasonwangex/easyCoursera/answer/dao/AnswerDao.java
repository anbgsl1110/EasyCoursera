package com.jasonwangex.easyCoursera.answer.dao;

import com.jasonwangex.easyCoursera.common.dao.BaseDao;
import com.jasonwangex.easyCoursera.answer.domain.Answer;

import java.util.List;

/**
 * Created by wangjz
 * on 17/5/9.
 */
public interface AnswerDao extends BaseDao<Answer> {

    Answer getOrRefresh(int userId, int examId, String content);

}
