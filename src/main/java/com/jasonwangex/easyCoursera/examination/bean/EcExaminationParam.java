package com.jasonwangex.easyCoursera.examination.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/10.
 */
public class EcExaminationParam implements Serializable {
    @NotNull @Length(max = 5000)
    private String content;
    private int type;
    @Range(min = 1)
    private int kpId;

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

    public int getKpId() {
        return kpId;
    }

    public void setKpId(int kpId) {
        this.kpId = kpId;
    }
}
