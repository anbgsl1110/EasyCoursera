package com.jasonwangex.easyCoursera.course.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by wangjz
 * on 17/5/21.
 */
@EcParam(value = "course", type = EcParamType.MODIFY, role = UserRoleEnum.TEACHER)
public class CourseModifyParam {
    @NotNull
    @Range(min = 1)
    private int id;
    @Length(min = 1, max = 32)
    private String name;
    @Length(max = 60000)
    private String content;

    @Range(max = 1)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
}
