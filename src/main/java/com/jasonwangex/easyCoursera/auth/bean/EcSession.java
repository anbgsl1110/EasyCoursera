package com.jasonwangex.easyCoursera.auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jasonwangex.easyCoursera.auth.enmus.UserRoleEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public class EcSession {
    private int userId;
    private Set<Integer> roleIds;
    private long trace;
    private long timestamp;
    private String openId;
    private String sign;
    private String nonce;

    public EcSession() {
        // 所有人都有匿名身份
        roleIds = new HashSet<>();
        roleIds.add(0);
    }

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

    public Set<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @JsonIgnore
    public boolean hasRole(UserRoleEnum userRoleEnum) {
        return UserRoleEnum.hasRole(this.roleIds, userRoleEnum);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
