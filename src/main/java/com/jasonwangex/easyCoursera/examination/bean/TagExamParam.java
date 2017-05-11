package com.jasonwangex.easyCoursera.examination.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.annotation.EcParamForeign;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import com.jasonwangex.easyCoursera.examination.domain.Examination;
import com.jasonwangex.easyCoursera.examination.domain.Tag;

import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/12.
 */
@EcParam(value = "tagexam", type = EcParamType.CREATE, role = UserRoleEnum.TEACHER)
public class TagExamParam implements Serializable {
    @EcParamForeign(target = Examination.class)
    private int examId;
    @EcParamForeign(target = Tag.class)
    private int tagId;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

}
