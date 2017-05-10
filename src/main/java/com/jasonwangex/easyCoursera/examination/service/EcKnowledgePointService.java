package com.jasonwangex.easyCoursera.examination.service;

import com.jasonwangex.easyCoursera.examination.domain.EcKpExam;

/**
 * Created by wangjz
 * on 17/5/10.
 */
public interface EcKnowledgePointService {

    EcKpExam bind(int examId, int kpId);
}
