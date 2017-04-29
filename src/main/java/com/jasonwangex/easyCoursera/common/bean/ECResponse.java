package com.jasonwangex.easyCoursera.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/3/2.
 */
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
        return success("success");
    }

    public static ECResponse fail(String message) {
        ECResponse responseData = new ECResponse();
        responseData.error = 400;
        responseData.message = message;
        return responseData;
    }

}
