package com.jasonwangex.easyCoursera.course.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@EcParam(value = "course", type = EcParamType.CREATE, role = UserRoleEnum.TEACHER)
public class CourseParam {
    @NotNull
    @Length(min = 1, max = 32)
    private String name;
    @NotNull
    @Length(max = 60000)
    private String content;

    @NotNull
    private Boolean needCheck;

    @NotNull
    @Max(1)
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }
}
