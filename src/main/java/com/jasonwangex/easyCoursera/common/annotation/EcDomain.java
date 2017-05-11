package com.jasonwangex.easyCoursera.common.annotation;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangjz
 * on 17/5/11.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Entity
@DynamicInsert
public @interface EcDomain {

    String value();
}
