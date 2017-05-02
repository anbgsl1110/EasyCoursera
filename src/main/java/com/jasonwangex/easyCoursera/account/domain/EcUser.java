package com.jasonwangex.easyCoursera.account.domain;

import com.jasonwangex.easyCoursera.common.domain.BaseDomain;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * Created by wangjz
 * on 17/2/23.
 */
@Entity
@DynamicInsert
@Table(name = "ec_user")
public class EcUser extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String openid;
    private String nickname;
    private String avatar;
    private int gender;
    private String userIp;
    private Date lastLogin;
    private Date activeTime;
    private String roleIds;

    public EcUser(){}

    /**
     * @param openid
     * @param nickname
     * @param avatar
     * @param gender
     * @param userIp
     * @param lastLogin
     */
    public EcUser(String openid, String nickname, String avatar, int gender,
                  String userIp, Date lastLogin) {
        this.openid = openid;
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
        this.userIp = userIp;
        this.lastLogin = lastLogin;
    }

    private Date createTime;
    private Date modifyTime;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getModifyTime() {
        return modifyTime;
    }

    @Override
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Set<Integer> getRoleIds(){
        String[] roleStrs = StringUtils.split(this.roleIds);
        Set<Integer> roleSet = new HashSet<>();
        for (String roleStr : roleStrs) {
            roleSet.add(NumberUtils.toInt(roleStr, 0));
        }
        return roleSet;
    }
}
