package com.jasonwangex.easyCoursera.answer.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;

/**
 * Created by wangjz
 * on 17/5/24.
 */
@EcParam(role = UserRoleEnum.TEACHER, type = EcParamType.QUERY, value = "answer")
public class AnswerQueryParam {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
