package com.jasonwangex.easyCoursera.examination.api;

import com.jasonwangex.easyCoursera.auth.annonation.NeedRole;
import com.jasonwangex.easyCoursera.auth.bean.EcSession;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.bean.ECResponse;
import com.jasonwangex.easyCoursera.common.util.EcSessionUtil;
import com.jasonwangex.easyCoursera.common.web.BaseController;
import com.jasonwangex.easyCoursera.examination.bean.EcExaminationParam;
import com.jasonwangex.easyCoursera.examination.domain.EcExamination;
import com.jasonwangex.easyCoursera.examination.service.EcExaminationService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 题库的增删改查
 * Created by wangjz
 * on 17/5/9.
 */
@RestController
@RequestMapping("/user/api/examination")
@NeedRole(UserRoleEnum.TEACHER)
public class EcExaminationApi extends BaseController {
    @Resource
    private EcExaminationService ecExaminationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ECResponse create(@Valid @ModelAttribute EcExaminationParam param) {
        EcSession ecSession = EcSessionUtil.getSession();

        EcExamination ecExamination = ecExaminationService.create(ecSession, param);

        if (ecExamination == null) return ECResponse.fail("创建失败");

        return ECResponse.success().set("item", ecExamination);
    }

}
