package com.jasonwangex.easyCoursera.common.enmu;

/**
 * Created by wangjz
 * on 17/5/11.
 */
public enum EcParamType {
    CREATE("create"),       // 创建资源
    MODIFY("modify"),       // 修改资源
    QUERY("query");         // 获取资源

    private String value;

    EcParamType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
