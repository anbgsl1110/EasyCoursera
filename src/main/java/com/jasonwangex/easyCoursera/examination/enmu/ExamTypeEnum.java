package com.jasonwangex.easyCoursera.examination.enmu;

/**
 * Created by wangjz
 * on 17/5/12.
 */
public enum  ExamTypeEnum {
    DEFAULT(0),
    CHOSE(1),
    COMPLEX_CHOSE(2),
    QUESTION(3);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    ExamTypeEnum(int value) {
        this.value = value;
    }
}
