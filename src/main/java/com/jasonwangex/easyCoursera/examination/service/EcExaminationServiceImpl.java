package com.jasonwangex.easyCoursera.examination.service;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.examination.bean.EcExaminationParam;
import com.jasonwangex.easyCoursera.examination.dao.EcExaminationDao;
import com.jasonwangex.easyCoursera.examination.domain.EcExamination;
import com.jasonwangex.easyCoursera.examination.domain.EcKpExam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangjz
 * on 17/5/10.
 */
@Service
public class EcExaminationServiceImpl implements EcExaminationService {
    @Resource
    private EcExaminationDao ecExaminationDao;

    @Resource
    private EcKnowledgePointService ecKnowledgePointService;

    @Override
    public EcExamination create(EcSession ecSession, EcExaminationParam param) {
        if (ecSession == null || param == null) return null;

        EcExamination ecExamination = new EcExamination();
        ecExamination.setContent(param.getContent());
        ecExamination.setType(param.getType());
        ecExamination.setCreator(ecSession.getUserId());
        ecExamination.setModifier(ecSession.getUserId());
        ecExaminationDao.save(ecExamination);

        ecKnowledgePointService.bind(ecExamination.getId(), param.getKpId());

        return ecExamination;
    }
}
