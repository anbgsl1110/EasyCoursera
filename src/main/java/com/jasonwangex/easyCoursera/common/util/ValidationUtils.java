package com.jasonwangex.easyCoursera.common.util;

import com.jasonwangex.easyCoursera.common.bean.ValidationResult;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Created by wangjz
 * on 17/5/11.
 */
public class ValidationUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResult check(T obj) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);

        if (CollectionUtils.isEmpty(set)) return result;
        Map<String, String> errorMsg = new HashMap<>();

        for (ConstraintViolation<T> cv : set) {
            errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
        }
        result.setErrors(errorMsg);

        return result;
    }


}