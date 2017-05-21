package com.jasonwangex.easyCoursera.examination.bean;


import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.annotation.EcParamForeign;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;
import com.jasonwangex.easyCoursera.examination.domain.Tag;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wangjz
 * on 17/5/10.
 */
@EcParam(value = "tag", type = EcParamType.CREATE, role = UserRoleEnum.TEACHER)
public class TagParam implements Serializable {
    private static final long serialVersionUID = 6091574026489640339L;

    @EcParamForeign(target = Tag.class)
    private int root;
    @NotNull @Length(min = 1, max = 32)
    private String name;
    @NotNull @Length(min = 1, max = 50000)
    private String detail;

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
