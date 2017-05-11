package com.jasonwangex.easyCoursera.common.bean;

import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by wangjz
 * on 17/5/11.
 */
public class ValidationResult {
    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public boolean isValid() {
        return CollectionUtils.isEmpty(errors);
    }
}
