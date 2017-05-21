package com.jasonwangex.easyCoursera.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/3/2.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ECResponse {
    private Map<String, Object> data;
    private int error;
    private String message;

    public ECResponse() {
        data = new HashMap<>();
        error = 0;
        message = "";
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @JsonInclude(content = JsonInclude.Include.NON_EMPTY)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ECResponse set(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public static ECResponse success(String message) {
        ECResponse responseData = new ECResponse();
        responseData.error = 0;
        responseData.message = message;
        return responseData;
    }

    public static ECResponse success() {
        return success(null);
    }

    public static ECResponse items(Collection items) {
        ECResponse ecResponse = success();
        return ecResponse.set("items", items);
    }

    public static ECResponse items(Object item) {
        ECResponse ecResponse = success();
        return ecResponse.set("items", Collections.singletonList(item));
    }

    public static ECResponse pagebean(PageBean pageBean) {
        List result = pageBean.getItems();
        return success()
                .set("total", pageBean.getTotal())
                .set("page", pageBean.getPage())
                .set("size", pageBean.getSize())
                .set("items", result);
    }

    public static ECResponse fail(String message) {
        return fail(4000, message);
    }

    public static ECResponse fail(int error, String message) {
        ECResponse responseData = new ECResponse();
        responseData.error = error;
        responseData.message = message;
        return responseData;
    }

    public static ECResponse notExist() {
        return fail(4100, "操作失败，资源不存在");
    }

    public static ECResponse packageError(BindingResult result) {
        ECResponse response = new ECResponse();
        response.error = 4200;
        response.message = result.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(";\n"));
        return response;
    }

    public static ECResponse forbbiden() {
        return fail(4400, "暂无权限");
    }

    public static ECResponse packageError(ValidationResult result) {
        ECResponse response = new ECResponse();
        response.error = 4200;
        StringBuilder stringBuilder = new StringBuilder();
        result.getErrors().forEach((key, value) -> {
            stringBuilder.append(key)
                    .append(":")
                    .append(value)
                    .append(";");
        });

        response.message = stringBuilder.toString();
        return response;
    }
}
