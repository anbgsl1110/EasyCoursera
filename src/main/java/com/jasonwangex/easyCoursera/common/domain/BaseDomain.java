package com.jasonwangex.easyCoursera.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by wangjz
 * on 17/2/24.
 */
public class BaseDomain {
    private Integer id;

    private Date createTime;
    private Date modifyTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean nullToEmpty(){
        return true;
    }

    public void beforeUpdate() {

    }

    public void afterUpdate() {

    }
}
