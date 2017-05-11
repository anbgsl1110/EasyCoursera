package com.jasonwangex.easyCoursera.common.exception;

/**
 * 类注册别名冲突
 *
 * Created by wangjz
 * on 17/5/11.
 */
public class EcClassAliasClashException extends RuntimeException {

    public EcClassAliasClashException() {
        super();
    }

    public EcClassAliasClashException(String message) {
        super(message);
    }
}
