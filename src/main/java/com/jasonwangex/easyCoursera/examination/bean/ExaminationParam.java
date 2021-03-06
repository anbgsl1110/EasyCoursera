package com.jasonwangex.easyCoursera.examination.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/10.
 */
@EcParam(value = "examination", type = EcParamType.CREATE, role = UserRoleEnum.TEACHER)
public class ExaminationParam implements Serializable {
    private static final long serialVersionUID = 6569296356523514125L;

    @NotNull
    @Length(max = 5000)
    private String content;
    private String answer;
    @Range(min = 1)
    private int type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
