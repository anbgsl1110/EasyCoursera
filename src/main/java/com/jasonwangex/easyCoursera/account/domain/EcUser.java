package com.jasonwangex.easyCoursera.account.domain;

import com.jasonwangex.easyCoursera.common.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangjz
 * on 17/2/23.
 */
@Entity
@DynamicInsert
@Table(name = "ec_user")
public class EcUser extends BaseEntity {
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

    @Column(updatable = false)
    private String roleIds;
    private boolean subscribe;
    private Date subscribeTime;

    @Column(updatable = false)
    private Date createTime;
    private Date modifyTime;

    @Transient
    private Set<Integer> roleSet = new HashSet<>();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

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

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
        refreshRoleIds();
    }

    public String getRoleIds() {
        return roleIds;
    }

    public Set<Integer> getRoleIdSet(){
        initRoleSet();
        return roleSet;
    }

    public void addRoleId(int roleId) {
        initRoleSet();
        roleSet.add(roleId);
        refreshRoleIds();
    }

    public void deleteRoleId(int roleId) {
        initRoleSet();
        roleSet.remove(roleId);
        refreshRoleIds();
    }

    private void refreshRoleIds(){
        roleIds = roleSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private void initRoleSet() {
        if (!CollectionUtils.isEmpty(roleSet)) return;

        if (StringUtils.isBlank(this.roleIds)) return;

        String[] roleStrs = this.roleIds.split(",");
        for (String roleStr : roleStrs) {
            roleSet.add(NumberUtils.toInt(roleStr, 0));
        }
    }

    public static void main(String[] args) {
        EcUser ecUser = new EcUser();
        ecUser.setRoleIds("1");
        ecUser.addRoleId(2);
        System.out.println(ecUser.getRoleIds());

    }
}
