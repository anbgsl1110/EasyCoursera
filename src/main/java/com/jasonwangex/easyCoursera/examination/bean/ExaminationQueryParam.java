package com.jasonwangex.easyCoursera.examination.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * Created by wangjz
 * on 17/5/11.
 */
@EcParam(value = "examination", type = EcParamType.QUERY, role = UserRoleEnum.USER)
public class ExaminationQueryParam {
    @Min(1)
    private Integer id;

    @Min(1)
    private Integer creator;

    @Range(min = 1)
    private int page = 1;

    @Range(min = 1, max = 100)
    private int size = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
