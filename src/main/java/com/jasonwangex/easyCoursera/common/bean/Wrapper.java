package com.jasonwangex.easyCoursera.common.bean;

/**
 * Created by wangjz
 * on 17/5/4.
 */
public class Wrapper<T> {
    T value;

    public T get(){
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
