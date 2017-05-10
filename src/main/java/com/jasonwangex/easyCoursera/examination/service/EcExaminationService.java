package com.jasonwangex.easyCoursera.examination.service;

import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.examination.bean.EcExaminationParam;
import com.jasonwangex.easyCoursera.examination.domain.EcExamination;

/**
 * Created by wangjz
 * on 17/5/10.
 */
public interface EcExaminationService {

    EcExamination create(EcSession ecSession, EcExaminationParam param);
}
