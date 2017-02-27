package com.jasonwangex.easyCoursera.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/2/22.
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static String toString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toParams(Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        params.forEach((key, value) -> {
            stringBuilder.append("&").append(key);
            String valueStr = null;
            try {
                valueStr = OBJECT_MAPPER.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (valueStr == null) return;
            stringBuilder.append("=")
                    .append(valueStr);
        });
        if (stringBuilder.length() > 0) stringBuilder.deleteCharAt(0);
        return stringBuilder.toString();
    }

    public static <T> T toObject(Class<T> tClass, String value){
        try {
            return OBJECT_MAPPER.readValue(value, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> pa = new HashMap<>();
        pa.put("111", "222");
        pa.put("133", "222");
        Map<String, Object> map = new HashMap<>();
        map.put("test", 1);
        map.put("test2", new Integer(2));
        map.put("test3", pa);
        System.out.println(toParams(map));
    }
}
