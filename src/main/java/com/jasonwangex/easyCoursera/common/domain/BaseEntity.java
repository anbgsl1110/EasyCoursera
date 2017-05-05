package com.jasonwangex.easyCoursera.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangjz
 * on 17/2/24.
 */
public abstract class BaseEntity implements Serializable{

    public abstract Date getCreateTime();

    public abstract void setCreateTime(Date createTime);

    public abstract Date getModifyTime();

    public abstract void setModifyTime(Date modifyTime);

    public abstract Integer getId();

    public abstract void setId(Integer id);

    @JsonIgnore
    public boolean nullToEmpty(){
        return true;
    }

    public void beforeUpdate() {
    }

    public void afterUpdate() {
    }
}
