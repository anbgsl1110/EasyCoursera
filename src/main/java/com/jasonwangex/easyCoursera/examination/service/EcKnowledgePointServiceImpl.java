package com.jasonwangex.easyCoursera.examination.service;

import com.jasonwangex.easyCoursera.examination.dao.EcExaminationDao;
import com.jasonwangex.easyCoursera.examination.dao.EcKnowledgePointDao;
import com.jasonwangex.easyCoursera.examination.dao.EcKpExamDao;
import com.jasonwangex.easyCoursera.examination.domain.EcExamination;
import com.jasonwangex.easyCoursera.examination.domain.EcKnowledgePoint;
import com.jasonwangex.easyCoursera.examination.domain.EcKpExam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/10.
 */
@Service
public class EcKnowledgePointServiceImpl implements EcKnowledgePointService {
    @Resource
    private EcExaminationDao ecExaminationDao;
    @Resource
    private EcKnowledgePointDao ecKnowledgePointDao;
    @Resource
    private EcKpExamDao ecKpExamDao;


    @Override
    public EcKpExam bind(int examId, int kpId) {
        EcKnowledgePoint knowledgePoint = ecKnowledgePointDao.getById(kpId);
        if (knowledgePoint == null) return null;

        EcExamination examination = ecExaminationDao.getById(examId);
        if (examination == null) return null;

        EcKpExam ecKpExam = new EcKpExam();
        ecKpExam.setExamId(examId);
        ecKpExam.setKpId(kpId);

        ecKpExamDao.save(ecKpExam);
        return ecKpExam;
    }
}
