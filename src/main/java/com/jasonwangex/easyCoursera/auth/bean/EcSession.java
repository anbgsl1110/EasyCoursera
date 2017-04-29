package com.jasonwangex.easyCoursera.auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;

import java.util.List;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public class EcSession {
    private int userId;
    private List<Integer> roleIds;
    private long trace;
    private long timestamp;
    private String openId;

    public long getTrace() {
        return trace;
    }

    public void setTrace(long trace) {
        this.trace = trace;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @JsonIgnore
    public boolean isLogin() {
        return userId > 0;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @JsonIgnore
    public boolean hasRole(UserRoleEnum userRoleEnum) {
        return UserRoleEnum.hasRole(this.roleIds, userRoleEnum);
    }

}
