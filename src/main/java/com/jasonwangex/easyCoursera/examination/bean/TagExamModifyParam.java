package com.jasonwangex.easyCoursera.examination.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@EcParam(value = "tagexam", type = EcParamType.MODIFY, role = UserRoleEnum.TEACHER)
public class TagExamModifyParam {
}
