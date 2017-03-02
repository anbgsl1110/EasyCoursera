package com.jasonwangex.easyCoursera.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/3/2.
 */
public class ResponseJsonHolder {
    private Map<String, Object> data;
    private int error;
    private String message;

    public ResponseJsonHolder() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseJsonHolder set(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public static ResponseJsonHolder success(String message) {
        ResponseJsonHolder responseData = new ResponseJsonHolder();
        responseData.error = 0;
        responseData.message = message;
        return responseData;
    }

    public static ResponseJsonHolder success() {
        return success("success");
    }

    public static ResponseJsonHolder fail(String message) {
        ResponseJsonHolder responseData = new ResponseJsonHolder();
        responseData.error = 400;
        responseData.message = message;
        return responseData;
    }

}
