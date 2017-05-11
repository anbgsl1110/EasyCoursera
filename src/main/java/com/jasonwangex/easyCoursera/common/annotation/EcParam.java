package com.jasonwangex.easyCoursera.common.annotation;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;
import com.jasonwangex.easyCoursera.common.enmu.EcParamType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类别名注解
 * Created by wangjz
 * on 17/5/11.
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EcParam {

    String value();

    EcParamType type();

    UserRoleEnum role();
}
