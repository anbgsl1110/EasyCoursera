package com.jasonwangex.easyCoursera.auth.annonation;

import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangjz
 * on 17/4/22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface NeedRole {

    UserRoleEnum[] value() default UserRoleEnum.ANON;
}
