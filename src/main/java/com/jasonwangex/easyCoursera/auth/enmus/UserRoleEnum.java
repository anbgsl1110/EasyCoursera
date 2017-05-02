package com.jasonwangex.easyCoursera.auth.enmus;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by wangjz
 * on 17/4/22.
 */
public enum UserRoleEnum {
    ANON(0),
    USER(1),
    TEACHER(2),
    ADMIN(3);

    private int roleId;

    UserRoleEnum(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public static boolean hasRole(Set<Integer> roleIds, UserRoleEnum userRoleEnum) {
        if (roleIds == null || userRoleEnum == null) return false;

        UserRoleEnum thisEnum = Arrays.stream(UserRoleEnum.values())
                .filter(roleEnum -> roleIds.contains(userRoleEnum.getRoleId()))
                .findFirst()
                .orElse(null);

        return thisEnum != null;
    }
}
