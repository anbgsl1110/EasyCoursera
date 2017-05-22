package com.jasonwangex.easyCoursera.qrcode.bean;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.annotation.EcParam;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;

import javax.validation.constraints.NotNull;

/**
 * Created by wangjz
 * on 17/5/22.
 */
@EcParam(role = UserRoleEnum.USER, type = EcParamType.QUERY, value = "qrcode")
public class QrcodeQueryParam {
    @NotNull
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
